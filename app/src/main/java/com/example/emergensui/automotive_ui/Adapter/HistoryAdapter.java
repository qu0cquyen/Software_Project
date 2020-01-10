package com.example.emergensui.automotive_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Visit_Doctor;
import com.example.emergensui.automotive_ui.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private Context context;
    private String docID;
    private String patientID;
    private ArrayList<String> lstVisit;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    public HistoryAdapter(Context context, ArrayList<String> lstDate, String docID, String patientID)
    {
        this.context = context;
        this.lstVisit = lstDate;
        this.docID = docID;
        this.patientID = patientID;

    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_recyclerview, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.HistoryHolder holder, int position) {
        final String date = lstVisit.get(position);
        String path = "Visit_Doctor/" + docID + "/" + patientID + "/" + date;

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference(path);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Visit_Doctor vd = dataSnapshot.getValue(Visit_Doctor.class);
                vd.setDate(date);
                holder.setDetails(vd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return lstVisit.size();
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

