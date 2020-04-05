package com.example.abeer.mysecretportfolio;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashAct extends AppCompatActivity implements Runnable{

    private  static  int splashTimeOut = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(this,splashTimeOut);

    }

    @Override
    public void run() {
//        Intent intent = new Intent(SplashAct.this,passwordAct.class);
//        startActivity(intent);
//        finish();
    }
}
