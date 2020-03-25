package com.example.abeer.mysecretportfolio;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abeer.mysecretportfolio.models.HomeModel;
import com.example.abeer.mysecretportfolio.models.AddNoteModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private LinearLayout coordinatorLayout;
    private Snackbar snackbar;
    private Activity activity;
    private EditText writeNote, titleEditText;
    private TextView pinkTextView, yellowTextView, blueTextView, whiteTextView, greenTextView, purpleTextView, orangeTextView, defaultTextView, greenWallpaperTextView, brownTextView, randomTextView;
    private AddNoteDatabase addNoteDatabase;
    private AddNoteModel model;
    private int id = 0, noteFlag = 0;
    private Dialog dialog;
    private NotificationManager mNotificationManager;
    private threadClassPart threadClassPart;
    private final int idSave = 1, idDelete = 2, idFavorite = 3, idLock = 4, idSecret = 5, idColor = 6;
    private MenuItem itemFavorite, itemLock, itemSave, itemDelete, itemSecret, itemColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        activity = this;

        toolbar = findViewById(R.id.add_note_toolbar);
        coordinatorLayout = findViewById(R.id.add_note_coordinatorLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addNoteDatabase = new AddNoteDatabase(this);
        model = new AddNoteModel(0, 0, 0, 0);
        model.setTitle("No Title");
        model.setNote("Empty...");
        model.setColor("" + R.drawable.rosewallpaper);

        writeNote = findViewById(R.id.add_note_editText);
        titleEditText = findViewById(R.id.add_note_toolbar_editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        itemSave = menu.add(Menu.NONE, idSave, 1, "Save");
        itemDelete = menu.add(Menu.NONE, idDelete, 2, "Delete");
        itemFavorite = menu.add(Menu.NONE, idFavorite, 3, "Favorite");
        itemLock = menu.add(Menu.NONE, idLock, 4, "Lock");
        itemSecret = menu.add(Menu.NONE, idSecret, 5, "Secret");
        itemColor = menu.add(Menu.NONE, idColor, 6, "Color");

        itemColor.setIcon(R.drawable.ic_color_lens);
        itemSave.setIcon(R.drawable.ic_save_black_24dp);
        itemDelete.setIcon(R.drawable.ic_delete_black_24dp);
        itemColor.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        itemSave.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        itemDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        itemFavorite.setCheckable(true);
        itemLock.setCheckable(true);
        itemSecret.setCheckable(true);

        if (AddNoteModel.bit == 1) {
            receiveIntentInformation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case 1: // Save
                if (writeNote.getText().length() != 0 || titleEditText.getText().length() != 0)
                    saveNote();
                else
                    Toast.makeText(this, "The note is empty!", Toast.LENGTH_SHORT).show();
                break;
            case 2: // Delete
                if (AddNoteModel.bit == 1) {
                    deleteTheNote();
                } else {
                    Toast.makeText(this, "The note doesn't saved yet!", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3: // Favourite
                itemFavorite.setCheckable(true);
                if (model.getFavourite() == 0) {
                    model.setFavourite(1);
                    itemSecret.setEnabled(false);
                    itemFavorite.setChecked(true);
                } else {
                    model.setFavourite(0);
                    itemFavorite.setChecked(false);
                    if (model.getLock() == 0)
                        itemSecret.setEnabled(true);
                }
                Log.e("checked ", " favourite " + model.getFavourite() + " lock " + model.getLock());
                break;
            case 4: // Lock
                    itemLock.setEnabled(true);
                    itemLock.setCheckable(true);
                    if (model.getLock() == 0) {
                        model.setLock(1);
                        itemLock.setChecked(true);
                        itemSecret.setEnabled(false);
                    } else {
                        model.setLock(0);
                        itemLock.setChecked(false);
                        if (model.getFavourite() == 0)
                            itemSecret.setEnabled(true);
                }
                break;
            case 5: // Secret
                itemSecret.setCheckable(true);
                if (model.getSecret() == 0) {
                    model.setSecret(1);
                    itemFavorite.setEnabled(false);
                    itemLock.setEnabled(false);
                    itemSecret.setChecked(true);
                    Log.e("secret", "" + model.getSecret());
                } else {
                    model.setSecret(0);
                    itemFavorite.setEnabled(true);
                    itemLock.setEnabled(true);
                    itemSecret.setChecked(false);
                    Log.e("secret", "" + model.getSecret());
                }
                break;
            case 6: // Color background
                dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.colors_dialog);

                pinkTextView = dialog.findViewById(R.id.color_list_pink);
                yellowTextView = dialog.findViewById(R.id.color_list_yellow);
                blueTextView = dialog.findViewById(R.id.color_list_blue);
                whiteTextView = dialog.findViewById(R.id.color_list_white);
                greenTextView = dialog.findViewById(R.id.color_list_green);
                purpleTextView = dialog.findViewById(R.id.color_list_purple);
                orangeTextView = dialog.findViewById(R.id.color_list_orange);
                defaultTextView = dialog.findViewById(R.id.color_list_default);
                greenWallpaperTextView = dialog.findViewById(R.id.color_list_greenWallpaper);
//                brownTextView = dialog.findViewById(R.id.color_list_brown);
//                randomTextView = dialog.findViewById(R.id.color_list_random);

                pinkTextView.setOnClickListener(this);
                blueTextView.setOnClickListener(this);
                purpleTextView.setOnClickListener(this);
                whiteTextView.setOnClickListener(this);
                yellowTextView.setOnClickListener(this);
                greenTextView.setOnClickListener(this);
                orangeTextView.setOnClickListener(this);
                defaultTextView.setOnClickListener(this);
                greenWallpaperTextView.setOnClickListener(this);
//                brownTextView.setOnClickListener(this);
//                randomTextView.setOnClickListener(this);
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(writeNote.getText().toString())
                && !TextUtils.isEmpty(titleEditText.getText().toString())) {
            saveNote();
        }
        super.onBackPressed();

    }

    public void lockNoteToNotificationBar() {
//        mNotificationManager = (NotificationManager)
//                getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                i, PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
//                        .setContentTitle(title)
//                        .setDefaults(Notification.DEFAULT_SOUND)
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText(msg))
//                        .setContentText(msg)
//                        .setPriority(Notification.PRIORITY_MAX);
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify((int) value, mBuilder.build());
    }

    public void saveNote() {
        model.setNote("" + writeNote.getText());
        model.setTitle("" + titleEditText.getText());
        if (AddNoteModel.bit == 0) { // new note
//            Log.e("after adding to db", "" + model.getId());
            addNoteDatabase.addContent(model.getTitle(), model.getNote(), model.getColor(), noteFlag);
//            Log.e("...........", "savePluginsInformation");
            new threadClassPart().start();///////////////
//            snackbar = Snackbar.make(coordinatorLayout, "Added Successfully", Snackbar.LENGTH_SHORT);
//            snackbar.show();
            Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
        } else if (AddNoteModel.bit == 1) { // edit note
            addNoteDatabase.updateContent(id, model.getTitle(), model.getNote(), model.getColor());
            Log.e("fav after edit", "" + model.getFavourite());
            Log.e("lock after edit", "" + model.getLock());
            addNoteDatabase.updatePlugins(model.getPluginsID(), model.getFavourite(), model.getLock(), model.getSecret());
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
//            snackbar = Snackbar.make(coordinatorLayout, "Updated Successfully", Snackbar.LENGTH_SHORT);
//            snackbar.show();
        }

        AddNoteModel.bit = 0;
        Intent goToHome = new Intent(AddNoteActivity.this, MainActivity.class);
        startActivity(goToHome);
        finish();
    }

    public class threadClassPart extends Thread {
        public void run() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Cursor cursor = addNoteDatabase.selectTheLastRow();
                    cursor.moveToFirst();
                    int noteID = cursor.getInt(0);
                    Log.e("returned id", "" + noteID);
                    cursor.close();
                    addNoteDatabase.addPluginsContent(noteID, model.getFavourite(), model.getLock(), model.getSecret());
                    Cursor cursor1 = addNoteDatabase.selectPluginsContent();
                    if (cursor1.moveToFirst()) {
                        do {
                            int column1 = cursor1.getInt(0);
                            Log.e("id", "" + column1);
                            int column2 = cursor1.getInt(1);
                            Log.e("fav", "" + column2);
                            int column3 = cursor1.getInt(2);
                            Log.e("lock", "" + column3);
                            int column4 = cursor1.getInt(3);
                            Log.e("secret", "" + column4);
                        } while (cursor1.moveToNext());
                    }
                    cursor1.close();
                }
            });
        }
    }

    public void deleteTheNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Want Delete it?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNoteActivity.this, "The note is deleted", Toast.LENGTH_SHORT).show();
                addNoteDatabase.clearNote(id);
                AddNoteModel.bit = 0;
                Intent goToHome = new Intent(AddNoteActivity.this, MainActivity.class);
                startActivity(goToHome);
                finish();
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

    public void receiveIntentInformation() {
//        Log.e("receiveIntentInfo", "Done");
        // this method will called from HomePageFragment for editting the note
        // the bit value is 1 now
        Bundle bundle = getIntent().getExtras();
        HomeModel model = (HomeModel) bundle.getSerializable("model");
        id = model.getId();
        this.model.setId(model.getId());
        this.model.setTitle(model.getTitle());
        this.model.setNote(model.getNote());
        this.model.setColor(String.valueOf(model.getColor()));
        this.model.setPluginsID(model.getPluginId());
        this.model.setFavourite(model.getFavorite());
        this.model.setLock(model.getPinToTaskbar());
        this.model.setSecret(model.getSecret());

//        Log.e("favorite state ", "" + this.model.getFavourite());
        titleEditText.setText(model.getTitle());
        writeNote.setText(model.getNote());
        writeNote.setBackgroundResource(model.getColor());
        if (this.model.getFavourite() == 1) {
//            Log.e("favorite received", "1");
            itemFavorite.setChecked(true);
        }
        if (this.model.getLock() == 1) {
            itemLock.setChecked(true);
        }
        if (this.model.getSecret() == 1) {
            itemSecret.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_list_green:
                writeNote.setBackgroundResource(R.color.green);
                model.setColor("" + R.color.green);
                Log.e("green", "" + R.color.green);
                break;
            case R.id.color_list_blue:
                writeNote.setBackgroundColor(getResources().getColor(R.color.blue));
                model.setColor("" + R.color.blue);
                break;
            case R.id.color_list_pink:
                writeNote.setBackgroundColor(getResources().getColor(R.color.pink));
                model.setColor("" + R.color.pink);
                break;
            case R.id.color_list_purple:
                writeNote.setBackgroundColor(getResources().getColor(R.color.purple));
                model.setColor("" + R.color.purple);
                break;
            case R.id.color_list_white:
                writeNote.setBackgroundColor(getResources().getColor(R.color.white));
                model.setColor("" + R.color.white);
                break;
            case R.id.color_list_yellow:
                writeNote.setBackgroundColor(getResources().getColor(R.color.yellow));
                model.setColor("" + R.color.yellow);
                break;
            case R.id.color_list_orange:
                writeNote.setBackgroundColor(getResources().getColor(R.color.orange));
                model.setColor("" + R.color.orange);
                break;
//            case R.id.color_list_brown:
//                writeNote.setBackgroundResource(R.drawable.brown_wallpaper);
//                model.setColor("" + R.drawable.brown_wallpaper);
//                Log.e("brown ", "" + R.drawable.brown_wallpaper);
//                break;
            case R.id.color_list_greenWallpaper:
                writeNote.setBackgroundResource(R.drawable.green_wallpaper);
                model.setColor("" + R.drawable.green_wallpaper);
                break;
            case R.id.color_list_default:
                writeNote.setBackgroundResource(R.drawable.rosewallpaper);
                model.setColor("" + R.drawable.rosewallpaper);
                break;
//            case R.id.color_list_random:
//                writeNote.setBackgroundResource(R.drawable.random_wallpaper);
//                model.setColor("" + R.drawable.random_wallpaper);
//                break;
        }
        dialog.dismiss();
    }


}
