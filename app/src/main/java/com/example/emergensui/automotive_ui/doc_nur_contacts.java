package com.example.emergensui.automotive_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.emergensui.automotive_ui.Adapter.DepartmentContactAdapter;
import com.example.emergensui.automotive_ui.Class.Specialization;
import com.example.emergensui.automotive_ui.Controller.SwipeController;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class doc_nur_contacts extends AppCompatActivity {
    private String path;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private ArrayList<Specialization> lstSpec;

    private RecyclerView contactView;
    DepartmentContactAdapter departmentAdapter;
    SwipeController sc;


    private void Init()
    {
        contactView = findViewById(R.id.contact_recyclerview);
        path = "Specialization/";

        mDatabase = FirebaseDatabase.getInstance();

        DepartmentRetrieve();

    }

    private void DepartmentRetrieve()
    {
        mReference = mDatabase.getReference(path);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstSpec = new ArrayList<>();
                for(DataSnapshot dsh : dataSnapshot.getChildren())
                {
                    Specialization s = dsh.getValue(Specialization.class);
                    lstSpec.add(s);
                }
                departmentAdapter = new DepartmentContactAdapter(doc_nur_contacts.this, lstSpec);
                contactView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                contactView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

                contactView.setAdapter(departmentAdapter);

                //RecyclerView SwipeFunction
                ItemTouchHelper itm = new ItemTouchHelper(new SwipeController(getApplicationContext(), departmentAdapter));
                itm.attachToRecyclerView(contactView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_nur_contacts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        Init();
    }



}
