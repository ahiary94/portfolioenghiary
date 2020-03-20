package com.example.abeer.mysecretportfolio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abeer.mysecretportfolio.models.AddNoteModel;
import com.example.abeer.mysecretportfolio.home.HomePageFragment;
import com.example.abeer.mysecretportfolio.plugins.CalenderActivity;
import com.example.abeer.mysecretportfolio.plugins.PluginsGridActivity;
import com.example.abeer.mysecretportfolio.plugins.positivequotes.PositiveQuotesActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static android.media.MediaRecorder.AudioSource.MIC;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    private ProgressDialog pd;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private HomePageFragment homePageFragment;
    private AddNoteDatabase database;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName = "";
    private int randomNo;
    private Button recordVoice, playRecord, pauseRecord, deleteRecord, saveRecord, closeDialog, okSecretPassword;
    private EditText secretPassword;
    private TextView passwordDialogTitle;
    private boolean isStartRecording = false;
    //    private static final int THREAD_ID = 10000;
    private static final int REQUEST_PERMISSION_CODE = 10000;
    private Dialog voiceDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TrafficStats.setThreadStatsTag(THREAD_ID);
        toolbar = findViewById(R.id.general_toolbar);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigation_view);
        database = new AddNoteDatabase(this);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        getHomePage();

        pd = new ProgressDialog(this);
        pd.setMessage("Please waiting...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this
                , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}
                , REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();

                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkDevicePermission() {
        int writeExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordAudioResult = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        return writeExternalStorage == PackageManager.PERMISSION_GRANTED
                && recordAudioResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addnote, menu);
        return true;
    }

    void getHomePage() {
        manager = getSupportFragmentManager();
        homePageFragment = new HomePageFragment();
        transaction = manager.beginTransaction();
        transaction.add(R.id.main_relativeLayout_container, homePageFragment);
        transaction.commit();
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.add_note_btn) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.add_note_voice_message) {

            voiceDialog = new Dialog(this);
            voiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            voiceDialog.setContentView(R.layout.voice_message_dialog);
            voiceDialog.setCanceledOnTouchOutside(false);
            recordVoice = voiceDialog.findViewById(R.id.voiceMessage_record);
            playRecord = voiceDialog.findViewById(R.id.voiceMessage_play);
            pauseRecord = voiceDialog.findViewById(R.id.voiceMessage_pause);
            saveRecord = voiceDialog.findViewById(R.id.voiceMessage_save);
            deleteRecord = voiceDialog.findViewById(R.id.voiceMessage_delete);
            closeDialog = voiceDialog.findViewById(R.id.voiceMessage_close);

            recordVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkDevicePermission()) {

                        if (!isStartRecording) {
                            recordVoice.setBackgroundResource(R.drawable.record_start);
                            isStartRecording = true;
                            playRecord.setEnabled(false);
                            pauseRecord.setEnabled(false);
                            saveRecord.setEnabled(false);
                            deleteRecord.setEnabled(false);
//                            Date date = new Date();
                            fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "record.3gp";
                            Log.e("fileName", fileName);
                            setUpMediaRecorder();
                            try {
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(MainActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
//                            randomNo = new Random().nextInt(1000);
//                            fileName += "/noterecord" + randomNo + ".3gp";
//                            mediaRecorder = new MediaRecorder();
//                            try {
//                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  //ok so I say audio source is the microphone, is it windows/linux microphone on the emulator?
//                            }catch (Exception e){
//                                Log.e("Exception", e.getMessage().toString());
//                            }
//                            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//                            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                            mediaRecorder.setOutputFile("/sdcard/Music/"+System.currentTimeMillis()+".amr");
//
//
//                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//
//                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},
//                                        0);
//                                Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT).show();
//                            } else {
//                                mediaRecorder.start();
//                            }
                        } else {
                            isStartRecording = false;
                            playRecord.setEnabled(true);
                            pauseRecord.setEnabled(true);
                            saveRecord.setEnabled(true);
                            deleteRecord.setEnabled(true);
                            recordVoice.setBackgroundResource(R.drawable.record);
                            mediaRecorder.stop();
                            Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        requestPermission();
                    }
                }
            });
//            recordVoice.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    if (checkDevicePermission()) {
//                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//
//                            recordVoice.setBackgroundResource(R.drawable.record_start);
//                            fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "record.3gp";
//                            setUpMediaRecorder();
//                            try {
//                                mediaRecorder.prepare();
//                                mediaRecorder.start();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            Toast.makeText(MainActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
////                            randomNo = new Random().nextInt(1000);
////                            fileName += "/noterecord" + randomNo + ".3gp";
////                            mediaRecorder = new MediaRecorder();
////                            try {
////                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  //ok so I say audio source is the microphone, is it windows/linux microphone on the emulator?
////                            }catch (Exception e){
////                                Log.e("Exception", e.getMessage().toString());
////                            }
////                            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
////                            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
////                            mediaRecorder.setOutputFile("/sdcard/Music/"+System.currentTimeMillis()+".amr");
////
////
////                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
////
////                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},
////                                        0);
////                                Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT).show();
////                            } else {
////                                mediaRecorder.start();
////                            }
//
//                        } else {
//
////                            recordVoice.setBackgroundResource(R.drawable.record);
////                            fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
////                            randomNo = new Random().nextInt(1000);
////                            fileName += "/noterecord" + randomNo + ".3gp";
////                            mediaRecorder.setOutputFile(fileName);
//                            mediaRecorder.stop();
//                        }
//                    } else {
//                        requestPermission();
//                    }
//                    return false;
//                }
//            });

            playRecord.setOnClickListener(new VoiceDialogActions());
            pauseRecord.setOnClickListener(new VoiceDialogActions());
            closeDialog.setOnClickListener(new VoiceDialogActions());

            voiceDialog.show();
        }
        return true;//super.onOptionsItemSelected(item)
    }

    class VoiceDialogActions implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.voiceMessage_play:
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(fileName);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.voiceMessage_pause:
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    setUpMediaRecorder();
                    Toast.makeText(MainActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.voiceMessage_save:

                    break;
                case R.id.voiceMessage_delete:
                    break;
                case R.id.voiceMessage_close:
                    if (isStartRecording)
                        mediaRecorder.stop();
                    isStartRecording = false;
                    recordVoice.setBackgroundResource(R.drawable.record);
                    voiceDialog.dismiss();
                    break;
            }

        }
    }

    private void setUpMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(fileName);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.e("cal", "calender");
        switch (item.getItemId()) {
            case R.id.plugins_calender:
                drawerLayout.closeDrawer(Gravity.START);
                Intent calenderIntent = new Intent(MainActivity.this, CalenderActivity.class);
                startActivity(calenderIntent);
                break;
            case R.id.plugins_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are You Want to Delete All Notes?");
                builder.setIcon(R.drawable.ic_delete_black_24dp);
                builder.setPositiveButton("Ok", this);
                builder.setNeutralButton("Cancel", this);
                builder.show();
                break;
            case R.id.plugins_secret:
                final String password = database.getSecretDialogPassword();
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.secret_password_dialog);

                passwordDialogTitle = dialog.findViewById(R.id.passwordDialog_title);
                secretPassword = dialog.findViewById(R.id.passwordDialog_password);
                okSecretPassword = dialog.findViewById(R.id.passwordDialog_ok);
                okSecretPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (password.equals("")) { // for the first time
                            passwordDialogTitle.setText("Add your password");
                            if (!TextUtils.isEmpty(secretPassword.getText().toString())) {
                                database.addSecretPassword(secretPassword.getText().toString());
                                Toast.makeText(MainActivity.this, "Password Added Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent0 = new Intent(MainActivity.this, PluginsGridActivity.class);
                                intent0.putExtra("flag", 11); // secret
                                startActivity(intent0);
                            } else
                                Toast.makeText(MainActivity.this, "Please add password first!", Toast.LENGTH_SHORT).show();
                        } else {
                            passwordDialogTitle.setText("Password");
                            if (!TextUtils.isEmpty(secretPassword.getText().toString())) {
                                if (secretPassword.getText().toString().equals(password)) {
                                    Toast.makeText(MainActivity.this, "Password Added Successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    Intent intent0 = new Intent(MainActivity.this, PluginsGridActivity.class);
                                    intent0.putExtra("flag", 11); // secret
                                    startActivity(intent0);
                                } else {
                                    Toast.makeText(MainActivity.this, "Password isn't correct!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Please fill your password first!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.plugins_star_list:
                Intent intent1 = new Intent(MainActivity.this, PluginsGridActivity.class);
                intent1.putExtra("flag", 12); // favourite
                startActivity(intent1);
//                Intent intent1 = new Intent(MainActivity.this, FavouriteActivity.class);
//                startActivity(intent1);
                break;
            case R.id.plugins_positive:
                Intent intent = new Intent(MainActivity.this, PositiveQuotesActivity.class);
                startActivity(intent);
                break;
            case R.id.plugins_exit:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you want to exit?");
                builder1.setIcon(R.drawable.ic_exit_to_app_black_24dp);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
                builder1.setNeutralButton("Cancel", this);
                builder1.show();

        }
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE: {
                Toast.makeText(this, "The note is deleted", Toast.LENGTH_SHORT).show();
                database.clearDatabase();
                AddNoteModel.bit = 0;
//                finish();
                startActivity(getIntent());
                break;
            }
            case DialogInterface.BUTTON_NEUTRAL: {
                dialog.cancel();
            }
        }
    }
//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();

//        if (item.getItemId() == R.id.plugins_background) //1
//        {
//
//            //openDialog(false);
//        }
///////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_delete)//2
//        {
//            // deleteFrag DF=new deleteFrag();
//            //transaction.replace(R.id.tex_container,DF);
//            //transaction.commit();
//            AlertDialog.Builder alertB = new AlertDialog.Builder(this)
//                    .setIcon(R.drawable.trash)
//                    .setTitle("My Portofolio ")
//                    .setMessage("Are you want delete ?");
//
//            alertB.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    portfolioDB pdb = new portfolioDB(MainActivity.this);
//                    SQLiteDatabase db = pdb.getWritableDatabase();
//                    //   db.execSQL("update userNotes set note=?",new String[]{""});
//
//                }
//            });
//
//            alertB.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//
//                    dialog.cancel();
//
//                }
//            });
//
//            alertB.setCancelable(true);
//            AlertDialog dialog = alertB.create();
//            dialog.show();
//        }
//////////////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_exit)//3
//        {
//            AlertDialog.Builder alertB = new AlertDialog.Builder(this)
//                    .setIcon(R.drawable.exit)
//                    .setTitle("My Portfolio")
//                    .setMessage("Are you want to exit ?");
//
//            alertB.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    users_account.usersAccount = "";
//                    finish();
//                }
//            });
//
//            alertB.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//
//                    dialog.cancel();
//
//                }
//            });
//
//            alertB.setCancelable(false);
//            AlertDialog alertDialog = alertB.create();
//            alertDialog.show();
//
//        }
//////////////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_save)// 4
//        {
//            String url1 = "https://mynovelsbox.000webhostapp.com/connect/myPortfolioSave.php";
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("id", String.valueOf(users_account.uId));
//                    map.put("notey", et.getText().toString());
//                    return super.getParams();
//                }
//            };
//            requestQueue.add(request);
//            portfolioDB saveDB = new portfolioDB(this);
//            SQLiteDatabase db = saveDB.getWritableDatabase();
//             db.execSQL("insert into userNotes values (?)",new String[]{et.getText().toString()});

//}
//////////////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_edit)//5
//
//        {
//            Intent obj=getIntent();
//            String url="https://mynovelsbox.000webhostapp.com/connect/myPortfolioEdit.php?notey="+et.getText().toString()+
//                    "&usery="+users_account.usersAccount+"&passy="+obj.getStringExtra("password") ;
//
//            RequestQueue rq = Volley.newRequestQueue(this);
//            StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, "Failed...", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            rq.add(sr);
//        }

//    }


}
