package com.example.abeer.mysecretportfolio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.abeer.mysecretportfolio.home.HomeRecyclerAdapter;

import static android.os.Build.ID;

public class AddNoteDatabase extends SQLiteOpenHelper {

    private static final String NOTE_DB_NAME = "myNoteAppDB";
    private static final int NOTE_DB_VERSION = 1;

    private String NOTES_TABLE = "notes_table";

    private String NOTES_ID = "note_id";
    private String NOTES_TITLE = "title";
    private String NOTES_NOTE = "note";
    private String NOTES_COLOR = "color";
    // **********************************************************
    private String PLUGUNS_TABLE = "plugins_table";

    private String PLUGUNS_ID = "plugin_id";
    private String PLUGUNS_RID = "plugin_rid";
    private String PLUGUNS_FAVORITE = "favorite";
    private String PLUGUNS_LOCK = "lock";
    // **********************************************************
    private String PASSWORD_TABLE = "password_table";

    private String PASSWORD = "password";
    // **********************************************************
    private String SECRET_TABLE = "secret_table";

    private String SECRET_ID = "id";
    private String SECRET_NOTE = "note";

// **********************************************************


    public AddNoteDatabase(@Nullable Context context) {
        super(context, NOTE_DB_NAME, null, NOTE_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        clearDatabase();

        String sqlQuery = "CREATE TABLE IF NOT EXISTS " + NOTES_TABLE + "("
                + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTES_TITLE + " VARCHAR,"
                + NOTES_NOTE + " VARCHAR,"
                + NOTES_COLOR + " VARCHAR);";

        String sqlQuery2 = "CREATE TABLE IF NOT EXISTS " + PLUGUNS_TABLE + "("
                + PLUGUNS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PLUGUNS_RID + " INTEGER,"
                + PLUGUNS_FAVORITE + " INTEGER,"
                + PLUGUNS_LOCK + " INTEGER,FOREIGN KEY(" + PLUGUNS_RID + ") REFERENCES tableOfMyNote(" + NOTES_ID + "));";

        String sqlQuery3 = "CREATE TABLE IF NOT EXISTS " + PASSWORD_TABLE + "("
                + PASSWORD + "VARCHAR);";

        String sqlQuery4 = "CREATE TABLE IF NOT EXISTS " + SECRET_TABLE + "("
                + SECRET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SECRET_NOTE + " VARCHAR);";

//        String sqlQuery = "ALTER TABLE tableOfMyNote ADD ID INTEGER PRIMARY KEY AUTOINCREMENT;";
        db.execSQL(sqlQuery);
        db.execSQL(sqlQuery2);
        db.execSQL(sqlQuery3);
        db.execSQL(sqlQuery4);

//        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlQuery = "DROP TABLE IF EXISTS " + NOTES_TABLE;
        String sqlQuery2 = "DROP TABLE IF EXISTS " + PLUGUNS_TABLE;
        String sqlQuery3 = "DROP TABLE IF EXISTS " + PASSWORD_TABLE;
        String sqlQuery4 = "DROP TABLE IF EXISTS " + SECRET_TABLE;

        db.execSQL(sqlQuery);
        db.execSQL(sqlQuery2);
        db.execSQL(sqlQuery3);
        db.execSQL(sqlQuery4);

        onCreate(db);

    }

    public boolean addContent(String title, String note, String color) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_NOTE, note);
        contentValues.put(NOTES_COLOR, color);
        database.insert(NOTES_TABLE, null, contentValues);
        return true;
    }

    public boolean addPluginsContent(int id, int favourite, int lock) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLUGUNS_RID, id);
        contentValues.put(PLUGUNS_FAVORITE, favourite);
        contentValues.put(PLUGUNS_LOCK, lock);

        database.insert(PLUGUNS_TABLE, null, contentValues);
        return true;
    }

    public boolean addSecretPassword(String password) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, password);
        database.insert(PASSWORD_TABLE, null, contentValues);
        return true;

    }

    public boolean addSecretNotes(String note) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECRET_NOTE, note);
        database.insert(SECRET_TABLE, null, contentValues);
        return true;

    }

    //------------------------------------------------------------------
//    public

    //------------------------------------------------------------------

    public void updateContent(int id, String title, String note, String color) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_NOTE, note);
        contentValues.put(NOTES_COLOR, color);
        database.update(NOTES_TABLE, contentValues, NOTES_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void updatePlugins(int id, int favorite, int lock) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLUGUNS_FAVORITE, favorite);
        contentValues.put(PLUGUNS_LOCK, lock);
        database.update(PLUGUNS_TABLE, contentValues, PLUGUNS_RID + " = ?", new String[]{String.valueOf(id)});
    }

    public void updateSecretPassword(String newPassword) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, newPassword);
        database.update(PASSWORD_TABLE, contentValues, null, null);
    }

    public void updateSecretNote(int id, String newNote) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECRET_NOTE, newNote);
        database.update(SECRET_TABLE, contentValues, SECRET_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //------------------------------------------------------------------

    public void clearDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(NOTES_TABLE, "1", null);
    }

    public void clearNote(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(NOTES_TABLE, NOTES_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor selectAllContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT note_id,title,note,color,plugin_rid,favorite,lock FROM notes_table,plugins_table";
        Cursor c = db.rawQuery(query, null);
//        if (c.moveToFirst()){
//            do {
//                // Passing values
//                String column1 = c.getString(0);
//                Log.e("Title", column1);
//                String column2 = c.getString(1);
//                Log.e("not", column2);
//                String column3 = c.getString(2);
//                Log.e("color", column3);
//            } while(c.moveToNext());
//        }
//        c.close();
//        db.close();
        return c;
    }

    public Cursor selectPluginsContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT plugin_rid,favorite,lock FROM plugins_table ", null);
        return cursor;
    }

    ////////////////////////////////////////////////////////////////////////////////////////// down error
    public Cursor selectSpecificPluginsContent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT plugin_rid,favorite,lock FROM plugins_table WHERE plugin_rid=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        return cursor;
    }

    public Cursor selectFavouriteContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT title,note,color FROM notes_table,plugins_table WHERE favorite = 1";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor selectTheLastRow() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + NOTES_ID + ")FROM notes_table";
        Cursor c = db.rawQuery(query, null);
        return c;
    }


}
