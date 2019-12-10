package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergensui.automotive_ui.Class.UData;
import com.example.emergensui.automotive_ui.Class.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class profile2_screen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataRef;

    EditText txtName;
    TextView txtType;
    TextView txtSpec;
    ImageView langFlag;
    boolean english = true;

    //Class
    UData user;
    Doctor ud;

    private void findAllViews()
    {
        langFlag = findViewById(R.id.flagView);
        txtName = findViewById(R.id.editText);
        txtType = findViewById(R.id.txtTypeValue);
        txtSpec = findViewById(R.id.txtSpecValue);
    }

    public void getUserDatabase()
    {
        //Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        //Get user information
        String path = "UData/" + mAuth.getUid();

        mDataRef = mDatabase.getReference(path);

        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UData.class);

                System.out.println(user.getProfession_id());
                System.out.println(user.getType());


                if(user.getType().equals("Doctor"))
                {
                    String docPath = user.getType() + "/" + user.getProfession_id();
                    mDataRef = mDatabase.getReference(docPath);

                    mDataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ud = dataSnapshot.getValue(Doctor.class);

                            txtName.setText(ud.getName());
                            txtSpec.setText(ud.getSpec_ID());
                            txtType.setText(user.getType());
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
        setContentView(R.layout.activity_profile2_screen);

        //Initialize views
        findAllViews();

        //Get user database and show on UI
        getUserDatabase();

        Button btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        Button btnPatientList = (Button)findViewById(R.id.lstPatient);
        btnPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile2_screen.this, patient_list.class);
                intent.putExtra("Doc_ID", user.getProfession_id());
                startActivity(intent);

            }
        });

        langFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(english == true){
                    Locale locale = new Locale("fr","CA");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    english = false;

                    langFlag.setImageResource(R.drawable.flagfr);
                    return;
                }
                if(english == false) {
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    english = true;

                    langFlag.setImageResource(R.drawable.flagen);
                    return;
                }

            }
        });



    }
}
