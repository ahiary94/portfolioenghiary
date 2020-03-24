package com.example.abeer.mysecretportfolio;

import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button;
    private EditText titile;
    private EditText message;
    private TestingApp testingApp;

    private ImageView putImage;
    private Button takeImage;
    private AddNoteDatabase database;

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

        ///////////////////////////////////////////////////////////////////////////
        database = new AddNoteDatabase(this);
        takeImage = findViewById(R.id.test_take_image);
        putImage = findViewById(R.id.test_put_image);

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                putImage.setImageBitmap(database.getImage());
                database.clearQuote();
                pushQuoteIntoDB(R.drawable.q1);
                pushQuoteIntoDB(R.drawable.q2);
                pushQuoteIntoDB(R.drawable.q3);
                pushQuoteIntoDB(R.drawable.q4);
                pushQuoteIntoDB(R.drawable.q5);
                pushQuoteIntoDB(R.drawable.q6);
                pushQuoteIntoDB(R.drawable.q7);
                pushQuoteIntoDB(R.drawable.q8);
                pushQuoteIntoDB(R.drawable.q9);
                pushQuoteIntoDB(R.drawable.q10);

            }
        });


    }

    void pushQuoteIntoDB(Integer drawable){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable);
        database.addImageQuote(getBytes(bitmap));
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
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

