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

    public HospitalAdapter(Context context, List<Hospital> lstHos)
    {
        this.context = context;
        this.lstHospital = lstHos;
    }

    @NonNull
    @Override
    public HospitalAdapter.HospitalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_sliding_items, parent, false);
        return new HospitalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.HospitalHolder holder, int position) {
        Hospital h = lstHospital.get(position);

        holder.setDetails(h);
        System.out.println(h.getAddress() + " - " + h.getName());

    }

    @Override
    public int getItemCount() {
        return lstHospital.size();
    }

    public interface ClickListener
    {
        void onItemClick(int position);
    }

    class HospitalHolder extends RecyclerView.ViewHolder
    {
        private TextView txtName;
        private TextView txtAddress;
        private TextView txtPhone;

        public HospitalHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_hos_name);
            txtAddress = itemView.findViewById(R.id.txt_hos_addr);
            txtPhone = itemView.findViewById(R.id.txt_hos_phone);
        }

        void setDetails(Hospital h)
        {
           txtName.setText(h.getName());
           txtAddress.setText(h.getAddress());
           txtPhone.setText(h.getPhone());
        }
    }
}
