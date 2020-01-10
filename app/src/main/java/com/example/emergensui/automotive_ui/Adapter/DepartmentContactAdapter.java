package com.example.emergensui.automotive_ui.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Class.Specialization;
import com.example.emergensui.automotive_ui.R;
import com.example.emergensui.automotive_ui.doc_nur_contacts;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DepartmentContactAdapter extends RecyclerView.Adapter<DepartmentContactAdapter.DepartmentContactHolder> {

    private Context context;
    private ArrayList<Specialization> lstSpec;

    public DepartmentContactAdapter(Context context, ArrayList<Specialization> lst)
    {
        this.context = context;
        this.lstSpec = lst;
    }

    @NonNull
    @Override
    public DepartmentContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts_recyclerview, parent,false);
        return new DepartmentContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentContactHolder holder, int position) {
        Specialization s = lstSpec.get(position);

        holder.setDetails(s);
    }

    @Override
    public int getItemCount() {
        return lstSpec.size();
    }

    public void callAction(int position)
    {
        Specialization sCall = lstSpec.get(position);
        System.out.println(sCall.getDepNum());
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sCall.getDepNum()));

        //Asking for direct Call Permission -- For Android >= 6.0
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions((doc_nur_contacts)context,
                                                    new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;

                }
                context.startActivity(intent);
            }
            else
            {
                context.startActivity(intent);
            }
        }
        catch(Exception ex)
        {
            Log.e("Log", ex.toString());
        }
        //Refresh adapter
        notifyDataSetChanged();
    }


    class DepartmentContactHolder extends RecyclerView.ViewHolder
    {

        TextView txtDepartment;

        DepartmentContactHolder(View itemView)
        {
            super(itemView);
            txtDepartment = itemView.findViewById(R.id.txtDepartment);
        }

        void setDetails(Specialization s)
        {
            txtDepartment.setText(s.getDescription() + " - " + s.getDepNum());
        }
    }
}

