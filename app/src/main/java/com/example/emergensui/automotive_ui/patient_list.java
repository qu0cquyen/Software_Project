package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.emergensui.automotive_ui.Adapter.PatientAdapter;
import com.example.emergensui.automotive_ui.Adapter.PatientChildAdapter;
import com.example.emergensui.automotive_ui.Class.Medical_Info;
import com.example.emergensui.automotive_ui.Class.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class patient_list extends AppCompatActivity {



    //Database
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    //Path
    private String path = "Visit_Doctor/";

    //Local Variables

    RecyclerView mRecyclerView;
    RecyclerView mChildRecyclerView;
    PatientAdapter adapter;
    PatientChildAdapter childAdapter;

    private String docID;
    private String patientID;

    //Class
    Patient p;
    Medical_Info medicalInfo;
    int count = 0;
    int counter = 0;
    int countChild = -1;
    int childCounter;
    ArrayList<Patient> patientArrayList;// = new ArrayList<>();
    ArrayList<Medical_Info> lstMedicalInfo;
    ArrayList<String> patientIDlst;
    ArrayList<String> lstMedicalDate = new ArrayList<>();


    ArrayList<String> lstPath;



    private void findAllViews()
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.patient_recyclerview);

    }

    private void init()
    {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientArrayList = new ArrayList<>();
        lstMedicalInfo = new ArrayList<>();
        PatientList_Initiation();
        //adapter = new PatientAdapter(this, patientArrayList, docID, patientID);
        //childAdapter = new PatientChildAdapter(patientArrayList, this);
        //mRecyclerView.setAdapter(adapter);
        adapter = new PatientAdapter(getApplicationContext(), patientArrayList, lstMedicalInfo, docID);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //PatientList_Initiation();
        System.out.println("We r done with database");


    }


    private void PatientList_Initiation()
    {
        Bundle extras = getIntent().getExtras();
        docID = extras.getString("Doc_ID");
        System.out.println(docID);
        path += docID;

        patientIDlst = new ArrayList<>();

        //Get instances for database
        mDatabase = FirebaseDatabase.getInstance();

        //Links the database with the specified path
        mReference = mDatabase.getReference(path);


       /* mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dhs : dataSnapshot.getChildren())
                {
                    patientIDlst.add(dhs.getKey());
                    childCounter = Integer.parseInt(String.valueOf(dataSnapshot.getChildrenCount()));
                    String patientPath = "Patient/" + dhs.getKey();

                    mReference = mDatabase.getReference(patientPath);

                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if(patientArrayList.size() == 0 || (count+1) <= childCounter) {
                               p = dataSnapshot.getValue(Patient.class);
                               p.setPos(count);
                               count++;
                               patientArrayList.add(p);
                           }
                           else
                           {
                               *//*for(Patient pa : patientArrayList)
                               {
                                  Patient ps = dataSnapshot.getValue(Patient.class);
                                  if(patientArrayList.contains(ps))
                                  {
                                      patientArrayList.set(pa.getPos(), ps);
                                  }
                               }*//*
                               //adapter.notifyDataSetChanged();
                               adapter.notifyItemRangeChanged(0, childCounter);
                           }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientArrayList.clear();
                patientIDlst = new ArrayList<>();
                childCounter = Integer.parseInt(String.valueOf(dataSnapshot.getChildrenCount()));
                System.out.println(childCounter);


                for(DataSnapshot dsh : dataSnapshot.getChildren())
                {
                    String patientPath = "Patient/" + dsh.getKey();
                    String medicalPath = "Medical_Info/" + dsh.getKey();
                    patientIDlst.add(dsh.getKey());

                    //Needs to reassign the list in order to update ddata for a patient in the app
                    //Access to the inner child
                    mReference = mDatabase.getReference(patientPath);

                    /*mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null) {
                                Patient p = dataSnapshot.getValue(Patient.class);
                                p.setPos(count);
                                count++;
                                patientArrayList.add(p);
                            }
                            adapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/

                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(patientArrayList.size() == 0 || (count+1) <= childCounter) {
                                p = dataSnapshot.getValue(Patient.class);

                                p.setPos(count);
                                p.setPatient_ID(patientIDlst.get(count));
                                //System.out.println(p.getPatient_ID());
                                count++;
                                patientArrayList.add(p);
                            }
                            else
                            {
                                adapter.notifyDataSetChanged();
                            }
                            //adapter.notifyDataSetChanged();
                           /* adapter.notifyItemChanged();


                            adapter = new PatientAdapter(getApplicationContext(), patientArrayList);
                            mRecyclerView.setAdapter(adapter);*/


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    mReference = mDatabase.getReference(medicalPath);


                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            lstMedicalDate.add(dataSnapshot.getKey());
                            for(DataSnapshot ds : dataSnapshot.getChildren())
                            {
                                medicalInfo = ds.getValue(Medical_Info.class);
                                medicalInfo.setDate(lstMedicalDate.get(counter));
                                counter++;
                                lstMedicalInfo.add(medicalInfo);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast t = Toast.makeText(getApplicationContext(), "Function hasn't fully been able to function yet!!", Toast.LENGTH_LONG);
                            t.show();

                        }
                    });



                }
                adapter = new PatientAdapter(getApplicationContext(), patientArrayList, lstMedicalInfo, docID);
                mRecyclerView.setAdapter(adapter);
                if(countChild > childCounter) {patientArrayList.clear();}




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast t = Toast.makeText(getApplicationContext(), "Function hasn't fully been able to function yet!!", Toast.LENGTH_LONG);
                t.show();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        findAllViews();
        init();
        System.out.println("Im done with feeding database");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(patient_list.this, profile2_screen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
