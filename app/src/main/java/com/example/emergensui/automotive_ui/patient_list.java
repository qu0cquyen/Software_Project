package com.example.emergensui.automotive_ui;

import android.os.Bundle;
import android.view.View;

import com.example.emergensui.automotive_ui.Adapter.PatientAdapter;
import com.example.emergensui.automotive_ui.Adapter.PatientChildAdapter;
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

    //Class
    Patient p;
    int count = 0;
    ArrayList<Patient> patientArrayList;


    private void findAllViews()
    {
        mRecyclerView = (RecyclerView)findViewById(R.id.patient_recyclerview);

    }

    private void init()
    {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientArrayList = new ArrayList<>();
        adapter = new PatientAdapter(this, patientArrayList);
        //childAdapter = new PatientChildAdapter(patientArrayList, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        PatientList_Initiation();
    }


    private void PatientList_Initiation()
    {
        Bundle extras = getIntent().getExtras();
        docID = extras.getString("Doc_ID");
        System.out.println(docID);
        path += docID;

        //Get instances for database
        mDatabase = FirebaseDatabase.getInstance();

        //Links the database with the specified path
        mReference = mDatabase.getReference(path);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsh : dataSnapshot.getChildren())
                {
                    String patientPath = "Patient/" + dsh.getKey();
                    System.out.println(patientPath);
                    System.out.println(dsh.getKey());


                    //Access to the inner child
                    mReference = mDatabase.getReference(patientPath);

                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            p = dataSnapshot.getValue(Patient.class);
                            p.setPos(count);
                            count++;
                            patientArrayList.add(p);



                            adapter.notifyDataSetChanged();

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
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        findAllViews();
        init();

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









        //Bind to Adapter
        //Initialize List of Patients
        //PatientList_Initiation();

        //System.out.println("Start printing here: ");
        /*for(Patient p : lstPatient)
        {
            System.out.println(p.getPatient_Name());
            System.out.println(p.getPatient_Type());
        }*/

        /*//Fill out the data into adapter
        PatientNameAdapater pa = new PatientNameAdapater(lstPatient);*/
        //System.out.println(pa.getItemCount());

        //Attach the adapter to recycler view
        /*mRecyclerView.setAdapter(pa);

        //Set layout manager to position items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));*/
    }

}
