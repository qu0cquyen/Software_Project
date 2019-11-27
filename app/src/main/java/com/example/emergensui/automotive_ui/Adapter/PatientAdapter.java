package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private RecyclerView.RecycledViewPool recycledViewPool;

    private boolean visible = false;
    private int pos;

    public PatientAdapter(Context context, ArrayList<Patient> lstp)
    {
        this.context = context;
        this.lstPatient = lstp;

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
        holder.setDetails(p);

        //int poss = p.getPos();

        List<Patient> lstpp = new ArrayList<>();
        lstpp.add(p);

        PatientChildAdapter pca = new PatientChildAdapter(lstpp, context);

        holder.childRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.childRecycler.setAdapter(pca);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(visible){visible = false;}
                else {visible = true;}
                notifyItemChanged(p.getPos());
            }

        });


    }

    @Override
    public int getItemCount() {
        return lstPatient.size();
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

            childRecycler.setVisibility(visible? View.VISIBLE : View.GONE);



        }
    }
}
