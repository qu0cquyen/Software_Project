package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Hospital;
import com.example.emergensui.automotive_ui.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalHolder> {
    private Context context;
    private List<Hospital> lstHospital;
    private static ClickListener clickListener;

    public HospitalAdapter(Context context, List<Hospital> lstHos, ClickListener recyclerClickListener)
    {
        this.context = context;
        this.lstHospital = lstHos;
        this.clickListener = recyclerClickListener;
    }

    @NonNull
    @Override
    public HospitalAdapter.HospitalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_sliding_items, parent, false);
        return new HospitalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.HospitalHolder holder, final int position) {
        Hospital h = lstHospital.get(position);

        holder.setDetails(h);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstHospital.size();
    }

    public interface ClickListener
    {
        void onItemClick(int position);
    }

    static class HospitalHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView txtName;
        private TextView txtAddress;
        private TextView txtPhone;
        RecyclerView recyclerView;

        public HospitalHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_hos_name);
            txtAddress = itemView.findViewById(R.id.txt_hos_addr);
            txtPhone = itemView.findViewById(R.id.txt_hos_phone);
            /*recyclerView = itemView.findViewById(R.id.hospitalRecyclerView);
            recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(getLayoutPosition());
                }
            });*/
        }

        void setDetails(Hospital h)
        {
           txtName.setText(h.getName());
           txtAddress.setText(h.getAddress());
           txtPhone.setText(h.getPhone());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
