package com.example.abeer.mysecretportfolio.addnote;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;

public class AddNoteActivity extends AppCompatActivity implements AddNoteView, DialogInterface.OnClickListener, View.OnClickListener {
    private EditText writeNote;
    private Toolbar toolbar;
    private TextView pinkTextView;
    private TextView yellowTextView;
    private TextView blueTextView;
    private TextView whiteTextView;
    private TextView greenTextView;
    private TextView purpleTextView;
    private TextView defaultTextView;
    private TextView greenWallpaperTextView;
    private TextView brownTextView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.general_toolbar);
        setSupportActionBar(toolbar);

        writeNote = findViewById(R.id.add_note_editText);
        relativeLayout = findViewById(R.id.color_list_relativeLayout);

//        pinkTextView = findViewById(R.id.color_list_pink);
//        yellowTextView = findViewById(R.id.color_list_yellow);
//        blueTextView = findViewById(R.id.color_list_blue);
//        whiteTextView = findViewById(R.id.color_list_white);
//        greenTextView = findViewById(R.id.color_list_green);
//        purpleTextView = findViewById(R.id.color_list_purple);
//        defaultTextView = findViewById(R.id.color_list_default);
//        greenWallpaperTextView = findViewById(R.id.color_list_greenWallpaper);
//        brownTextView = findViewById(R.id.color_list_brown);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        greenTextView.setOnClickListener(this);
//        brownTextView.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.noteoptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_option_background: {
                getBackgroundColor();
                break;
            }
            case R.id.note_option_delete: {
                break;
            }
            case R.id.note_option_save: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getBackgroundColor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        RelativeLayout layout = new RelativeLayout(this);
        pinkTextView = new TextView(this);
        yellowTextView = new TextView(this);
        blueTextView = new TextView(this);
        brownTextView = new TextView(this);
        whiteTextView = new TextView(this);
        purpleTextView = new TextView(this);
        defaultTextView = new TextView(this);
        greenTextView = new TextView(this);
        greenWallpaperTextView = new TextView(this);

        pinkTextView.setHeight(150);
        pinkTextView.setWidth(150);
        pinkTextView.setBackgroundResource(R.color.pink);
        yellowTextView.setBackgroundResource(R.color.yellow);
        blueTextView.setBackgroundResource(R.color.blue);
        brownTextView.setBackgroundResource(R.drawable.brown_wallpaper);
        whiteTextView.setBackgroundResource(R.color.white);
        purpleTextView.setBackgroundResource(R.color.purple);
        defaultTextView.setBackgroundResource(R.drawable.rosewallpaper);
        greenTextView.setBackgroundResource(R.color.green);
        greenWallpaperTextView.setBackgroundResource(R.drawable.green_wallpaper);

        RelativeLayout.LayoutParams paramsYellow = new RelativeLayout.LayoutParams(150,150);
        paramsYellow.leftMargin = 150;

        RelativeLayout.LayoutParams paramsBlue = new RelativeLayout.LayoutParams(150,150);
        paramsBlue.leftMargin = 300;

        RelativeLayout.LayoutParams paramsPurple = new RelativeLayout.LayoutParams(150,150);
        paramsPurple.topMargin = 150;


        RelativeLayout.LayoutParams paramsWhite = new RelativeLayout.LayoutParams(150,150);
        paramsWhite.leftMargin = 150;
        paramsWhite.topMargin = 150;

        RelativeLayout.LayoutParams paramsGreen = new RelativeLayout.LayoutParams(150,150);
        paramsGreen.leftMargin = 300;
        paramsGreen.topMargin = 150;

        RelativeLayout.LayoutParams paramsGreenWallpaper = new RelativeLayout.LayoutParams(150,150);
        paramsGreenWallpaper.topMargin = 300;

        RelativeLayout.LayoutParams paramsBrownWallpaper = new RelativeLayout.LayoutParams(150,150);
        paramsBrownWallpaper.leftMargin = 150;
        paramsBrownWallpaper.topMargin = 300;

        RelativeLayout.LayoutParams paramsDefaultWallpaper = new RelativeLayout.LayoutParams(150,150);
        paramsDefaultWallpaper.leftMargin = 300;
        paramsDefaultWallpaper.topMargin = 300;

        layout.addView(pinkTextView);
        layout.addView(yellowTextView,paramsYellow);
        layout.addView(blueTextView,paramsBlue);
        layout.addView(purpleTextView,paramsPurple);
        layout.addView(whiteTextView,paramsWhite);
        layout.addView(greenTextView,paramsGreen);
        layout.addView(greenWallpaperTextView,paramsGreenWallpaper);
        layout.addView(brownTextView,paramsBrownWallpaper);
        layout.addView(defaultTextView,paramsDefaultWallpaper);

//        LayoutInflater factory = LayoutInflater.from(this);
//        View view = factory.inflate(R.layout.activity_colors_list, null);
//        builder.setView(view);
        builder.setView(layout);
//        builder.setPositiveButton("Ok", this);

//        AlertDialog dialog = builder.create();
//        dialog.getWindow().setLayout(561, 700);
        builder.show();

    }

    @Override
    public void deleteTheNote() {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.e("which", "" + which);
        pinkTextView.setOnClickListener(this);
        blueTextView.setOnClickListener(this);
        purpleTextView.setOnClickListener(this);
        whiteTextView.setOnClickListener(this);
        yellowTextView.setOnClickListener(this);
        greenTextView.setOnClickListener(this);
        defaultTextView.setOnClickListener(this);
        greenWallpaperTextView.setOnClickListener(this);
        brownTextView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_list_green:
                Log.e("green", "green");
                writeNote.setBackgroundResource(R.color.green);
                break;
            case R.id.color_list_blue:
                Log.e("blue", "blue");
                writeNote.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case R.id.color_list_pink:
                writeNote.setBackgroundColor(getResources().getColor(R.color.pink));
                break;
            case R.id.color_list_purple:
                writeNote.setBackgroundColor(getResources().getColor(R.color.purple));
                break;
            case R.id.color_list_white:
                writeNote.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.color_list_yellow:
                writeNote.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;
            case R.id.color_list_brown:
                writeNote.setBackgroundResource(R.drawable.brown_wallpaper);
                break;
            case R.id.color_list_greenWallpaper:
                writeNote.setBackgroundResource(R.drawable.green_wallpaper);
                break;
            case R.id.color_list_default:
                writeNote.setBackgroundResource(R.drawable.rosewallpaper);
                break;
        }
    }
}
