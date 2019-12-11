package com.example.emergensui.automotive_ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MainActivity extends AppCompatActivity {
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnParamedic;
    FirebaseAuth mAuth;

    SharedPreferences sharedPref;

    private CoordinatorLayout mCLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //processPref();
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

        //Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        //Login Click Listener
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                appLogin();
                savePref();

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

        loadPref();

    }

   /* private void processPref() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String user_name = settings.getString("user_name_pref", "1");
        String password = settings.getString("password_pref", "1");
    }*/

    private void loadPref() {
        String userName = sharedPref.getString("user_name", "");
        String password = sharedPref.getString("password", "");

        txtUserName.setText(userName);
        txtPassword.setText(password);
    }

    private void savePref() {
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString("user_name", txtUserName.getText().toString());
        edit.putString("password", txtPassword.getText().toString());
        edit.commit();

    }

    private void appLogin()
    {
        String user_name = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        //Check user_name and password
        if(user_name.length() == 0 || password.length() == 0)
        {
            Toast t = Toast.makeText(getApplicationContext(), "User name or Password is missing.", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        mAuth.signInWithEmailAndPassword(user_name, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent1 = new Intent(MainActivity.this, profile2_screen.class);
                    startActivity(intent1);
                }
                else
                {
                    Toast t = Toast.makeText(getApplicationContext(), "User name and Password are incorrect", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    protected void findAllView()
    {
        txtUserName = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnParamedic = findViewById(R.id.btnParamedic);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
    }
}
