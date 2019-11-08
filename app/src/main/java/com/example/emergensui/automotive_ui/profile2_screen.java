package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class profile2_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2_screen);

        //Get the user name info from login screen
        Bundle bundle = getIntent().getExtras();
        String user_name = bundle.getString("user_name");

        EditText name = (EditText)findViewById(R.id.editText);
        name.setText(user_name);

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
                /*Intent intent = new Intent(profile2_screen.this, patient_list.class);
                startActivity(intent);*/

            }
        });



    }
}
