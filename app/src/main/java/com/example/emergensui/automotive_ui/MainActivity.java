package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnParamedic;

    private CoordinatorLayout mCLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllView();

        //Hide keyboard
        findViewById(R.id.layout_activity).setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        //Login Click Listener
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent1 = new Intent(MainActivity.this, profile2_screen.class);
                //Pass user name to linked intent
                intent1.putExtra("user_name", txtUserName.getText().toString());
                startActivity(intent1);
            }
        });

        //Paramedic Click Listener
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
