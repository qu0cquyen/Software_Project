package com.example.emergensui.automotive_ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash_screen extends AppCompatActivity {

    private final int SPLASH_DELAY = 2000; //Delays for 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(splash_screen.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
