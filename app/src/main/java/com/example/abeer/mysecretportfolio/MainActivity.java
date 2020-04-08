package com.example.abeer.mysecretportfolio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abeer.mysecretportfolio.models.AddNoteModel;
import com.example.abeer.mysecretportfolio.models.HomeModel;
import com.example.abeer.mysecretportfolio.plugins.CalenderActivity;
import com.example.abeer.mysecretportfolio.plugins.PluginsGridActivity;
import com.example.abeer.mysecretportfolio.plugins.PositiveQuotesActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.media.MediaRecorder.AudioSource.MIC;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
    private Snackbar snackbar;
    private RelativeLayout coordinator;
    private ProgressDialog pd;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    //    private FragmentManager manager;
//    private FragmentTransaction transaction;
    //    private HomePageFragment homePageFragment;
    private AddNoteDatabase database;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName = "";
    private int randomNo, noteFlag = 1;
    private Button deleteRecord, closeDialog, okSecretPassword;
    private EditText secretPassword, recordTitle;
    private TextView passwordDialogTitle;
    private boolean isStartRecording = false;
    //    private static final int THREAD_ID = 10000;
    private static final int REQUEST_PERMISSION_CODE = 10000;
    private Dialog voiceDialog;
    private int length = 0;
    private ImageView recordVoice, pauseRecord, playRecord, saveRecord;
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;
    private List<HomeModel> list = new ArrayList<>();
    private SeekBar seekBar;
    private Runnable runnable;
    private Handler handler;
    public static final String ACTIVITY_SOURCE = "SOURCE";
//    private Calendar calendar;
    private Date date;
    private SimpleDateFormat dateFormat;
    private String noteTime = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TrafficStats.setThreadStatsTag(THREAD_ID);
//        calendar = Calendar.getInstance();
        date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        noteTime = dateFormat.format(date);
//        Log.e("date", noteTime);

        activity = this;
        database = new AddNoteDatabase(this);
        coordinator = findViewById(R.id.main_coordinator);
        toolbar = findViewById(R.id.general_toolbar);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigation_view);
        recyclerView = findViewById(R.id.home_recyclerView);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getRecyclerItems();
                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
                adapter = new HomeRecyclerAdapter(list, MainActivity.this, database);
                recyclerView.setAdapter(adapter);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

//        getHomePage();
        getRecyclerItems();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new HomeRecyclerAdapter(list, this, database);
        recyclerView.setAdapter(adapter);

        pd = new ProgressDialog(this);
        pd.setMessage("Please waiting...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    public void getRecyclerItems() {
        list.clear();
        list = database.selectAllContent();
//        Log.e("size from db", "" + list.size());
    }

    public void goToAddNotePageForEditting(HomeModel model) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        intent.putExtras(bundle);
        intent.putExtra(ACTIVITY_SOURCE, 1);
        AddNoteModel.bit = 1;
//        Log.e("id editing", "" + model.getId());
//        Log.e("pluginid editing", "" + model.getPluginId());
//        Log.e("favorite editing", "" + model.getFavorite());
        startActivity(intent);

    }

    public class threadClassPart extends Thread {
        public void run() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Cursor cursor = database.selectTheLastRow();
                    cursor.moveToFirst();
                    int noteID = cursor.getInt(0);
                    Log.e("returned id", "" + noteID);
                    cursor.close();
                    database.addPluginsContent(noteID, 0, 0, 0);
                    notifyAdapter();

                }
            });
        }
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
            closeDialog = voiceDialog.findViewById(R.id.voiceMessage_close);
            recordTitle = voiceDialog.findViewById(R.id.voiceMessage_title);
            seekBar = voiceDialog.findViewById(R.id.voiceMessage_seekbar);
            handler = new Handler();
            mediaPlayer = new MediaPlayer();

            playRecord.setEnabled(false);
            playRecord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray2f)));
            pauseRecord.setEnabled(false);
            pauseRecord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray2f)));
            saveRecord.setEnabled(false);
            saveRecord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray2f)));

            recordVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkDevicePermission()) {

                        if (!isStartRecording) {
                            recordVoice.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red_dark)));
                            isStartRecording = true;

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
                        } else {
                            isStartRecording = false;
                            playRecord.setEnabled(true);
                            playRecord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            pauseRecord.setEnabled(true);
                            pauseRecord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            saveRecord.setEnabled(true);
                            saveRecord.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            recordVoice.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                            mediaRecorder.stop();
                            Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        requestPermission();
                    }
                }
            });

            playRecord.setOnClickListener(new VoiceDialogActions());
            pauseRecord.setOnClickListener(new VoiceDialogActions());
            closeDialog.setOnClickListener(new VoiceDialogActions());
            saveRecord.setOnClickListener(new VoiceDialogActions());

            voiceDialog.show();
        }
        return true;//super.onOptionsItemSelected(item)
    }

    class VoiceDialogActions implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.voiceMessage_play: {
                    if (mediaPlayer != null && length > 0) {
                        mediaPlayer.seekTo(length);
                        seekBar.setMax(mediaPlayer.getDuration());
                        mediaPlayer.start();
                        changeSeekbar();
                    } else {
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(fileName);
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                        Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();
                    }

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            seekBar.setMax(mediaPlayer.getDuration());
                            mediaPlayer.start();
                            changeSeekbar();
                        }
                    });
//
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                mediaPlayer.seekTo(progress);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            length = 0;
                            mediaPlayer.seekTo(length);
                            changeSeekbar();
                        }
                    });
                }
                break;
                case R.id.voiceMessage_pause:
                    if (mediaPlayer != null) {
                        length = mediaPlayer.getCurrentPosition();
                        mediaPlayer.pause();
//                        mediaPlayer.release();
                    }
//                    setUpMediaRecorder();
                    Toast.makeText(MainActivity.this, "Paused", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.voiceMessage_save:
                    String title = "";
                    title = recordTitle.getText().toString();
                    if (!TextUtils.isEmpty(fileName)) {
                        database.addContent(title, fileName, "2131165356", noteFlag, noteTime);
                        new threadClassPart().start();
                        Toast.makeText(MainActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                        voiceDialog.dismiss();
//                        notifyAdapter();

                    } else
                        Toast.makeText(MainActivity.this, "File is empty!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.voiceMessage_close:
                    if (isStartRecording)
                        mediaRecorder.stop();
                    fileName = "";
                    mediaPlayer = null;
                    isStartRecording = false;
                    recordVoice.setBackgroundResource(R.drawable.record);
                    voiceDialog.dismiss();
                    break;
            }

        }
    }

    private void changeSeekbar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            handler.postDelayed(runnable, 500);
        }
    }

    public void changeAdapterSeekbar(final SeekBar seekBar, final MediaPlayer mediaPlayer, Runnable runnable, final Handler handler) {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            final Runnable finalRunnable = runnable;
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeAdapterSeekbar(seekBar, mediaPlayer, finalRunnable, handler);
                }
            };
            handler.postDelayed(runnable, 500);
        }
    }

    void showSnackbar(String message, int drawable) {

        snackbar = Snackbar.make(coordinator, Html.fromHtml("<font color=\"#5951EE\">" + message + "</font>"), Snackbar.LENGTH_SHORT);
        View snackbarLayout = snackbar.getView();
        TextView textView = snackbarLayout.findViewById(R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
        textView.setCompoundDrawablePadding(15);
//        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
        snackbar.show();

    }

    public void notifyAdapter() {
        Log.e("notifyAdapter", "true");
        getRecyclerItems();
        adapter = new HomeRecyclerAdapter(list, this, database);
        recyclerView.setAdapter(adapter);
    }

    public void deleteTheNote(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Want Delete it?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.clearNote(id);
                showSnackbar("The note is deleted", R.drawable.ic_check);
                notifyAdapter();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        builder.show();

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
                drawerLayout.closeDrawer(Gravity.LEFT);
                Intent calenderIntent = new Intent(MainActivity.this, CalenderActivity.class);
                startActivity(calenderIntent);
                break;
            case R.id.plugins_delete:
                if (recyclerView.getChildCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Are You Want Delete All Notes?");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database.clearDatabase();
                            showSnackbar("Deleted Successfully", R.drawable.ic_check);
                            notifyAdapter();
                        }
                    });
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else
                    Toast.makeText(activity, "The list is empty!", Toast.LENGTH_SHORT).show();

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
                builder1.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();

        }
        return true;
    }

}
