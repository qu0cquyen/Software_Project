package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.emergensui.automotive_ui.Adapter.PatientAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class patient_list extends AppCompatActivity implements SearchView.OnQueryTextListener {


    //Database
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    //Path
    private String path = "Visit_Doctor/";

    //Local Variables
    RecyclerView mRecyclerView;
    SearchView mSearchView;
    PatientAdapter adapter;

    private String docID;

    ArrayList<String> lstPatientID;


    private void findAllViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.patient_recyclerview);
        mSearchView = findViewById(R.id.patient_search);

    }

    private void init() {
        PatientList_Initiation();
    }


    private void PatientList_Initiation() {
        Bundle extras = getIntent().getExtras();
        docID = extras.getString("Doc_ID");
        path += docID;


        //Get instances for database
        mDatabase = FirebaseDatabase.getInstance();

        //Links the database with the specified path
        mReference = mDatabase.getReference(path);

        //Gets all the patients that a Doctor is responsible for
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstPatientID = new ArrayList<>();
                for (DataSnapshot dsh : dataSnapshot.getChildren()) {
                    lstPatientID.add(dsh.getKey());

                }
                adapter = new PatientAdapter(getApplicationContext(), lstPatientID, docID);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
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
                Intent intent = new Intent(patient_list.this, doc_nur_contacts.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Activate the Search function
        mSearchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    //Filter/Search Function
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //Listen to user's input
    public boolean onQueryTextChange(String newText)
    {
        String text = newText;
        System.out.println("Search is activated");
        adapter.stringFilter(text);
        return false;
    }


}
