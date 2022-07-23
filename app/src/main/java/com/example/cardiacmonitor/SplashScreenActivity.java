package com.example.cardiacmonitor;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * this class implements the splash screen
 */
public class SplashScreenActivity extends AppCompatActivity {
    /**
     * this is used for generating a delay and switch the screen to home screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));//switch to home screen from splash screen activity
                finish();
            }
        },3000);//generating a 3 seconds delay before switching to home screen
    }
}