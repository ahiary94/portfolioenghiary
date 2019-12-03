package com.example.abeer.mysecretportfolio;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class TestingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button;
    private EditText titile;
    private EditText message;
    private TestingApp testingApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        toolbar = findViewById(R.id.general_toolbar);
        setSupportActionBar(toolbar);
        titile = findViewById(R.id.editText1);
        message = findViewById(R.id.editText2);
        testingApp = new TestingApp(this);
//        managerCompat = NotificationManagerCompat.from(this);
        button = findViewById(R.id.notification_button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String tittle = titile.getText().toString();
                String msg = message.getText().toString();

                Notification.Builder mBuilder = testingApp.getChannelNotification(tittle, msg);
                testingApp.getManeger().notify(new Random().nextInt(), mBuilder.build());

            }
        });
    }

//    public void sendOnChannel() {
//        String tittle = titile.getText().toString();
//        String msg = message.getText().toString();
//
//        Notification notification = new NotificationCompat.Builder(this, TestingApp.CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_save_black_24dp)
//                .setContentTitle(tittle)
//                .setContentText(msg)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//
//        managerCompat.notify(1, notification);
//    }

}

