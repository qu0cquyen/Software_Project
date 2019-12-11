package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergensui.automotive_ui.Adapter.HistoryAdapter;
import com.example.emergensui.automotive_ui.Class.Patient;
import com.example.emergensui.automotive_ui.Class.Visit_Doctor;
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
    String date;
    int counter = 0;
    ArrayList<Visit_Doctor> lstVisitDoc;
    ArrayList<String> lstDate;
    Visit_Doctor vd;
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

    private void displayInfo()
    {
        //Getting patient information from previous intent
        Bundle b = getIntent().getExtras();
        p = (Patient)b.getSerializable("Patient");
        docID = b.getString("Doc_ID");


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
        System.out.println(p.getPatient_ID() + "This is from here");

        //Initialize lstDate
        lstDate = new ArrayList<>();
        //lstVisitDoc = new ArrayList<>();
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Retrieve Doc's note
        retrieve(docID, p.getPatient_ID());
    }

    //Retrieve data from database
    private void retrieve(String doc_id, String patient_ID)
    {
        //Get Doctor's Note
        final String path = "Visit_Doctor/" + doc_id + "/" + patient_ID;
        System.out.println(path);

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference(path);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstVisitDoc = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String newPath = path + "/" + ds.getKey();
                    lstDate.add(ds.getKey());

                    mReference = mDatabase.getReference(newPath);

                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            vd = dataSnapshot.getValue(Visit_Doctor.class);
                            vd.setDate(lstDate.get(counter));
                            counter++;
                            lstVisitDoc.add(vd);



                            hisAdapter = new HistoryAdapter(getApplicationContext(), lstVisitDoc);
                            //System.out.println(lstDocNote);
                            rvHistory.setAdapter(hisAdapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast t = Toast.makeText(getApplicationContext(), "Function hasn't fully been able to function yet!!", Toast.LENGTH_LONG);
                            t.show();
                        }
                    });
                }

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
        setContentView(R.layout.activity_patient_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //Initialize components
        findAllView();
        displayInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(patient_information.this, patient_list.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle b = new Bundle();
                b.putSerializable("Patient", p);
                intent.putExtras(b);
                intent.putExtra("Doc_ID", docID);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
