package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Medical_Info;
import com.example.emergensui.automotive_ui.R;
import com.example.emergensui.automotive_ui.patient_information;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PatientChildAdapter extends RecyclerView.Adapter<PatientChildAdapter.PatientChildHolder>
{

    private Context context;
    List<String> pIDlst;
    String docID;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;


    public PatientChildAdapter(Context context, List<String> lstPatientID, String docID)
    {
        this.context = context;
        this.pIDlst = lstPatientID;
        this.docID = docID;
    }

    @NonNull
    @Override
    public PatientChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_recycler_view, parent, false);
        return new PatientChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientChildHolder holder, int position) {
        final String pID = pIDlst.get(position);
        String medicalPath = "Medical_Info/" + pID;

        System.out.println(pID);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference(medicalPath);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Medical_Info mi = dataSnapshot.getValue(Medical_Info.class);
                holder.setDetails(mi);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, patient_information.class);
                intent.putExtra("PatientID", pID);
                intent.putExtra("DoctorID", docID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (pIDlst == null)? 0 : pIDlst.size();
    }

    class PatientChildHolder extends RecyclerView.ViewHolder
    {

        private TextView txtPatientRoom;
        private TextView txtPatientType;
        private TextView txtPatientDoB;
        private CardView cardView;

        PatientChildHolder(@NonNull View itemView)
        {
            super(itemView);
            txtPatientRoom = itemView.findViewById(R.id.lblPatientBloodPressure);
            txtPatientType = itemView.findViewById(R.id.lblPatientHeartRate);
            txtPatientDoB = itemView.findViewById(R.id.lblPatientOxygen);
            cardView = itemView.findViewById(R.id.childCard);
        }

        void setDetails(Medical_Info med)
        {
            txtPatientRoom.setText(String.valueOf(med.getBlood_pressure()));
            txtPatientType.setText(String.valueOf(med.getHeart_rate()));
            txtPatientDoB.setText(String.valueOf(med.getOxygen()));
            cardView.setRadius(100);
            //cardView.setCardElevation(2.1f);
            //cardView.setMaxCardElevation(3f);
            cardView.setContentPadding(30,30,30,0);
            cardView.setUseCompatPadding(true);
            cardView.setPreventCornerOverlap(true);
            //cardView.setCardBackgroundColor(Color.parseColor("#add8e6"));
        }
    }
}
