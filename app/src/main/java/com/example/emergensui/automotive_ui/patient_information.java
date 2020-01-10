package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emergensui.automotive_ui.Adapter.HistoryAdapter;
import com.example.emergensui.automotive_ui.Class.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class patient_information extends AppCompatActivity {

    TextView txtName;
    TextView txtAge;
    TextView txtSex;
    TextView txtEmergenContact;
    TextView txtPhoneNumber;
    RecyclerView rvHistory;

    ImageView eImageView;

    String docID;
    String patientID;
    Patient p;

    HistoryAdapter hisAdapter;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    private void findAllView()
    {
        txtName = findViewById(R.id.lblPatientInfo_Name);
        txtAge = findViewById(R.id.lblPatientInfo_Age);
        txtSex = findViewById(R.id.lblPatientInfo_Sex);
        txtEmergenContact = findViewById(R.id.lblPatientInfo_EmergencyContact);
        txtPhoneNumber = findViewById(R.id.lblPatientInfo_EmergencyNumber);
        rvHistory = findViewById(R.id.PatientInfo_RecyclerView);
        eImageView = findViewById(R.id.patient_img);
    }

    private void init()
    {
        //Gets DocID and PatientID
        Bundle b = getIntent().getExtras();
        docID = b.getString("DoctorID");
        patientID = b.getString("PatientID");

        //Initialize database
        mDatabase = FirebaseDatabase.getInstance();
    }

    private void retrievePatient()
    {
        String patientPath = "Patient/" + patientID;

        //Access to database with specified path
        mReference = mDatabase.getReference(patientPath);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p = dataSnapshot.getValue(Patient.class);
                displayPatientInfo(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayPatientInfo(Patient p)
    {
        //Set profile's image based on Sex
        if(p.getPatient_Sex().equals("Male"))
        {
            eImageView.setImageResource(R.drawable.male_patient_icon);
        }
        else
        {
            eImageView.setImageResource(R.drawable.female_patient_icon);
        }
        txtName.setText(p.getPatient_Name());
        txtSex.setText(p.getPatient_Sex());
        txtPhoneNumber.setText(p.getPhone_Number());
        String patient_dob = p.getDoB();
        String[] dateSplit = patient_dob.split("/");
        String year = dateSplit[2];
        //Get current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        int age = currentYear - Integer.parseInt(year);
        txtAge.setText(String.valueOf(age));
    }

    private void retrieveDocNote()
    {
        String docNotePath = "Visit_Doctor/" + docID + "/" + patientID;

        mReference = mDatabase.getReference(docNotePath);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> lstDate = new ArrayList<>();
                for(DataSnapshot dsh : dataSnapshot.getChildren())
                {
                    lstDate.add(dsh.getKey());
                }
                hisAdapter = new HistoryAdapter(getApplicationContext(), lstDate, docID, patientID);
                rvHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvHistory.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                rvHistory.setAdapter(hisAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_patient_info);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_information.this, doc_nur_contacts.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize components
        findAllView();
        init();
        retrievePatient();
        retrieveDocNote();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(patient_information.this, patient_list.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Doc_ID", docID);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
