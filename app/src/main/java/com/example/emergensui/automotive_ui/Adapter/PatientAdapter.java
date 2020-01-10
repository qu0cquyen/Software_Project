package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Patient;
import com.example.emergensui.automotive_ui.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder>
{

    private Context context;
    private String docID;

    private Patient p;

    private List<String> lstPatientID;
    private List<Patient> lstPatient;

    private int toggle = 0;
    private int flag = 0;

    FirebaseDatabase mDatabase;
    DatabaseReference mReferences;

    public PatientAdapter(Context context, ArrayList<String> lst, String docID)
    {
        this.context = context;
        this.lstPatientID = lst;
        this.docID = docID;
        this.lstPatient = new ArrayList<>();
    }


    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_recycleview, parent, false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientHolder holder, final int position) {
        final String patientID = lstPatientID.get(position);

        mDatabase = FirebaseDatabase.getInstance();
        mReferences = mDatabase.getReference("Patient/" + patientID);

        mReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient p = dataSnapshot.getValue(Patient.class);
                p.setPatient_ID(patientID);
                holder.setDetails(p.getPatient_Name());
                if(flag == 0) //Gets all the patient when the app runs for the 1st time
                {
                    lstPatient.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println(lstPatient.size());

        ArrayList<String> lstPsID = new ArrayList<>();
        lstPsID.add(patientID);

        PatientChildAdapter pca = new PatientChildAdapter(context, lstPsID, docID);

        holder.childRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.childRecycler.setAdapter(pca);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.setChildVisible();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstPatientID.size();
    }

    //String filter
    public void stringFilter(String searchString)
    {
        flag = 1; //Prevents add the patients again since they existed in the array
        ArrayList<Patient> lst = new ArrayList<>();
        lst.addAll(lstPatient);
        lstPatientID.clear();
        if(searchString.length() == 0)
        {
            for(Patient p : lst)
            {
                lstPatientID.add(p.getPatient_ID());
            }
        }
        else
        {
            for(Patient p : lst)
            {
                if(p.getPatient_Name().toLowerCase(Locale.getDefault()).contains(searchString.toLowerCase(Locale.getDefault())))
                {
                    lstPatientID.add(p.getPatient_ID());
                }
            }
        }
        notifyDataSetChanged();
    }

    class PatientHolder extends RecyclerView.ViewHolder
    {

        private TextView txtPatientName;
        private RecyclerView childRecycler;


        PatientHolder(@NonNull View itemView)
        {
            super(itemView);
            txtPatientName = itemView.findViewById(R.id.lbPatientName);
            childRecycler = itemView.findViewById(R.id.sub_items);

        }

        void setDetails(String patientName)
        {
            txtPatientName.setText(patientName);

            if(toggle == 1) {childRecycler.setVisibility(View.VISIBLE);}
            else{childRecycler.setVisibility(View.GONE);}
        }

        void setChildVisible()
        {
            if(childRecycler.getVisibility() == View.VISIBLE)
            {
                childRecycler.setVisibility(View.GONE);
                return;
            }
            if(childRecycler.getVisibility() == View.GONE)
            {
                childRecycler.setVisibility(View.VISIBLE);
                return;
            }
        }
    }
}
