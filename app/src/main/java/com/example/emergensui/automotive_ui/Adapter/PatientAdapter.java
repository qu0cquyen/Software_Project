package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Medical_Info;
import com.example.emergensui.automotive_ui.Class.Patient;
import com.example.emergensui.automotive_ui.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder>
{

    private Context context;
    private List<Patient> lstPatient;
    private List<Medical_Info> lstMedical;
    private String docID;

    private RecyclerView.RecycledViewPool recycledViewPool;

    private int toggle = 0;

    public PatientAdapter(Context context, ArrayList<Patient> lstp, ArrayList<Medical_Info> lstMed, String docID)
    {
        this.context = context;
        this.lstPatient = lstp;
        this.lstMedical = lstMed;
        this.docID = docID;

        recycledViewPool = new RecyclerView.RecycledViewPool();
    }


    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_recycleview, parent, false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        final Patient p = lstPatient.get(position);
        Medical_Info med = lstMedical.get(position);
        holder.setDetails(p);

        //int poss = p.getPos();

        List<Patient> lstpp = new ArrayList<>();
        lstpp.add(p);

        List<Medical_Info> lsm = new ArrayList<>();
        lsm.add(med);

        PatientChildAdapter pca = new PatientChildAdapter(lstpp, lsm, context, docID);

        holder.childRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.childRecycler.setAdapter(pca);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (toggle)
                {
                    case 1:
                        toggle = 0;
                        notifyItemChanged(p.getPos());
                        break;

                    case 0:
                        toggle = 1;
                        notifyItemChanged(p.getPos());
                        break;
                }

            }

        });


    }

    @Override
    public int getItemCount() {
        return (lstPatient == null)? 0 : lstPatient.size();
    }

    class PatientHolder extends RecyclerView.ViewHolder
    {

        private TextView txtPatientName;
        /*private TextView txtPatientRoom;
        private TextView txtPatientType;
        private TextView txtPatientDoB;*/
        private View subItems;
        private RecyclerView childRecycler;


        PatientHolder(@NonNull View itemView)
        {
            super(itemView);
            txtPatientName = itemView.findViewById(R.id.lbPatientName);
            /*txtPatientRoom = itemView.findViewById(R.id.lblPatientRoom);
            txtPatientType = itemView.findViewById(R.id.lblPatientTypeValue);
            txtPatientDoB = itemView.findViewById(R.id.lblPatientDoB_Value);*/
            //subItems = itemView.findViewById(R.id.sub_items);
            childRecycler = itemView.findViewById(R.id.sub_items);

            //childRecycler.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        }

        void setDetails(Patient p)
        {
            txtPatientName.setText(p.getPatient_Name());
            /*txtPatientRoom.setText(p.getRoom_Number());
            txtPatientType.setText(p.getPatient_Type());
            txtPatientDoB.setText(p.getDoB());*/

            if(toggle == 1) {childRecycler.setVisibility(View.VISIBLE);}
            else{childRecycler.setVisibility(View.GONE);}
            //childRecycler.setVisibility(toggle? View.VISIBLE : View.GONE);



        }
    }
}
