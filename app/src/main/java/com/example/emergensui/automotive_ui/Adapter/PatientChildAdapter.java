package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Medical_Info;
import com.example.emergensui.automotive_ui.Class.Patient;
import com.example.emergensui.automotive_ui.R;
import com.example.emergensui.automotive_ui.patient_information;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PatientChildAdapter extends RecyclerView.Adapter<PatientChildAdapter.PatientChildHolder>
{

    private Context context;
    List<Patient> lstPatient;
    List<Medical_Info> lstMedical;
    Patient p;
    String docID;

    public PatientChildAdapter(List<Patient> lsp, List<Medical_Info> lstMed, Context context, String docID)
    {
        this.context = context;
        this.lstPatient = lsp;
        this.lstMedical = lstMed;
        this.docID = docID;

    }

    public PatientChildAdapter(Patient patient, Context context)
    {
        this.context = context;
        this.p = patient;

    }


    @NonNull
    @Override
    public PatientChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_recycler_view, parent, false);
        return new PatientChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientChildHolder holder, int position) {
        final Patient pa = lstPatient.get(position);
        Medical_Info med = lstMedical.get(position);
        //System.out.println(pa.getPatient_ID());
        holder.setDetails(med);
        //count++;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, patient_information.class);
                Bundle b = new Bundle();
                b.putSerializable("Patient", pa);
                System.out.println(pa.getPatient_ID());
                intent.putExtras(b);
                intent.putExtra("Doc_ID", docID);
                context.startActivity(intent);
                notifyItemChanged(pa.getPos());
            }
        });





    }

    @Override
    public int getItemCount() {
        return (lstPatient == null)? 0 : lstPatient.size();
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
            txtPatientRoom.setTextColor(Color.WHITE);
            txtPatientType.setTextColor(Color.WHITE);
            txtPatientDoB.setTextColor(Color.WHITE);
            cardView.setRadius(100);
            cardView.setCardElevation(2.1f);
            cardView.setMaxCardElevation(3f);
            cardView.setContentPadding(30,30,30,0);
            cardView.setUseCompatPadding(true);
            cardView.setPreventCornerOverlap(true);
            cardView.setCardBackgroundColor(Color.parseColor("#add8e6"));








        }
    }
}
