package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Visit_Doctor;
import com.example.emergensui.automotive_ui.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private Context context;
    private String date;
    private ArrayList<Visit_Doctor> lstVisit;


    public HistoryAdapter(Context context, ArrayList<Visit_Doctor> lstVisitDoc)
    {
        this.context = context;
        this.lstVisit = lstVisitDoc;
        System.out.println("???????????????????????????");
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_recyclerview, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, int position) {
        Visit_Doctor vd = lstVisit.get(position);
        System.out.println(vd.getDoc_note());
        holder.setDetails(vd);


    }

    @Override
    public int getItemCount() {
        return (lstVisit == null)? 0 : lstVisit.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder
    {
        private TextView txtDate;
        private TextView txtDocNote;


        HistoryHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDocNote = itemView.findViewById(R.id.txtDocNote);

        }

        void setDetails(Visit_Doctor vd)
        {
            txtDocNote.setText(vd.getDoc_note());
            txtDate.setText(vd.getDate());
        }
    }
}

