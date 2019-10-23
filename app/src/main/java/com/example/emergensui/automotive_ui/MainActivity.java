package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnParamedic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllView();

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent1 = new Intent(MainActivity.this, profile2_screen.class);
                startActivity(intent1);
            }
        });
        btnParamedic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent2 = new Intent(MainActivity.this, paramedic_screen.class);
                startActivity(intent2);
            }
        });

    }



    protected void findAllView()
    {
        txtUserName = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnParamedic = findViewById(R.id.btnParamedic);
    }
}
