package com.example.abeer.mysecretportfolio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.abeer.mysecretportfolio.models.FavouriteModel;
import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class AddNoteDatabase extends SQLiteOpenHelper {

    private static final String NOTE_DB_NAME = "myNoteAppDB";
    private static final int NOTE_DB_VERSION = 7;

    private String NOTES_TABLE = "notes_table";

    private String NOTES_ID = "note_id";
    private String NOTES_TITLE = "title";
    private String NOTES_NOTE = "note";
    private String NOTES_COLOR = "color";
    private String NOTES_FLAG = "flag";// 0 => hand note , 1 => voice note

    // **********************************************************
    private String PLUGINS_TABLE = "plugins_table";

    private String PLUGINS_ID = "plugin_id";
    private String PLUGINS_RID = "plugin_rid";
    private String PLUGINS_FAVORITE = "favorite";
    private String PLUGINS_LOCK = "lock";
    private String PLUGINS_SECRET = "secret";

    // **********************************************************
    private String PASSWORD_TABLE = "password_table";

    private String PASSWORD = "password";
    // **********************************************************
    private String SECRET_TABLE = "secret_table";

    private String SECRET_ID = "id";
    private String SECRET_NOTE = "note";

    // **********************************************************
    private String QUOTE_TABLE = "quote_table";

    private String QUOTE_ID = "id";
    private String QUOTE_IMAGE = "image";


// **********************************************************


    public AddNoteDatabase(@Nullable Context context) {
        super(context, NOTE_DB_NAME, null, NOTE_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        clearDatabase();

        String sqlQuery5 = "CREATE TABLE IF NOT EXISTS " + QUOTE_TABLE + "("
                + QUOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + QUOTE_IMAGE + " BLOB"
                + ");";

        String sqlQuery = "CREATE TABLE IF NOT EXISTS " + NOTES_TABLE + "("
                + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTES_TITLE + " VARCHAR,"
                + NOTES_NOTE + " VARCHAR,"
                + NOTES_COLOR + " VARCHAR,"
                + NOTES_FLAG + " INTEGER"
                + ");";

        String sqlQuery2 = "CREATE TABLE IF NOT EXISTS " + PLUGINS_TABLE + "("
                + PLUGINS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PLUGINS_RID + " INTEGER,"
                + PLUGINS_FAVORITE + " INTEGER,"
                + PLUGINS_LOCK + " INTEGER,"
                + PLUGINS_SECRET + " INTEGER,FOREIGN KEY(" + PLUGINS_RID + ") REFERENCES tableOfMyNote(" + NOTES_ID + "));";

        String sqlQuery3 = "CREATE TABLE IF NOT EXISTS " + PASSWORD_TABLE + "("
                + PASSWORD + " VARCHAR);";

        String sqlQuery4 = "CREATE TABLE IF NOT EXISTS " + SECRET_TABLE + "("
                + SECRET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SECRET_NOTE + " VARCHAR);";

//        String sqlQuery = "ALTER TABLE tableOfMyNote ADD ID INTEGER PRIMARY KEY AUTOINCREMENT;";
        db.execSQL(sqlQuery);
        db.execSQL(sqlQuery2);
        db.execSQL(sqlQuery3);
        db.execSQL(sqlQuery4);
        db.execSQL(sqlQuery5);

//        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + NOTES_TABLE + " ADD COLUMN " + NOTES_FLAG + " INTEGER");
        db.execSQL("UPDATE " + NOTES_TABLE + " SET " + NOTES_FLAG + " = 0");
//        db.execSQL("ALTER TABLE " + PASSWORD_TABLE + " ADD COLUMN " + PASSWORD + " VARCHAR");
//        db.execSQL("CREATE TABLE IF NOT EXISTS " + QUOTE_TABLE + "("
//                + QUOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + QUOTE_IMAGE + " BLOB"
//                + ");");

//        String sqlQuery = "DROP TABLE IF EXISTS " + NOTES_TABLE;

//        db.execSQL(sqlQuery);

        onCreate(db);

    }

    public void addImageQuote(byte[] image) throws SQLiteException {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUOTE_IMAGE, image);
        database.insert(QUOTE_TABLE, null, contentValues);
    }

    public boolean addContent(String title, String note, String color, int flag) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_NOTE, note);
        contentValues.put(NOTES_COLOR, color);
        contentValues.put(NOTES_FLAG, flag);

        database.insert(NOTES_TABLE, null, contentValues);
        return true;
    }

    public boolean addPluginsContent(int id, int favourite, int lock, int secret) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLUGINS_RID, id);
        contentValues.put(PLUGINS_FAVORITE, favourite);
        contentValues.put(PLUGINS_LOCK, lock);
        contentValues.put(PLUGINS_SECRET, secret);

        database.insert(PLUGINS_TABLE, null, contentValues);
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

    //------------------------------------- UPDATE -----------------------------

    public void updateContent(int id, String title, String note, String color) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_NOTE, note);
        contentValues.put(NOTES_COLOR, color);
        database.update(NOTES_TABLE, contentValues, NOTES_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void updatePlugins(int id, int favorite, int lock, int secret) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLUGINS_FAVORITE, favorite);
        contentValues.put(PLUGINS_LOCK, lock);
        contentValues.put(PLUGINS_SECRET, secret);

        database.update(PLUGINS_TABLE, contentValues, PLUGINS_RID + " = ?", new String[]{String.valueOf(id)});
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

    //--------------------------------- CLEAR ---------------------------------

    public void clearDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(NOTES_TABLE, "1", null);
        database.delete(PLUGINS_TABLE, "1", null);

    }

    public void clearNote(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(NOTES_TABLE, NOTES_ID + " = ?", new String[]{String.valueOf(id)});
        database.delete(PLUGINS_TABLE, PLUGINS_RID + " = ?", new String[]{String.valueOf(id)});

    }

    public void clearQuote() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(QUOTE_TABLE, null, null);

    }

    //------------------------------------ SELECT ------------------------------


    public List<Bitmap> getImage() {
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        List<Bitmap> bitmapList = new ArrayList<>();
        String query = "SELECT * FROM " + QUOTE_TABLE;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {
                byte[] image = cursor.getBlob(1);
                bitmapList.add(BitmapFactory.decodeByteArray(image, 0, image.length));

            } while (cursor.moveToNext());
            database.endTransaction();
        return bitmapList;
    }

    public List<HomeModel> selectAllContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT note_id,title,note,color,plugin_rid,favorite,lock,secret,flag FROM notes_table,plugins_table WHERE \n" +
                "note_id == plugin_rid AND secret = 0";
        Cursor cursor = db.rawQuery(query, null);
        List<HomeModel> list = new ArrayList<>();
        if (cursor.moveToFirst())
            do {
                HomeModel model = new HomeModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setNote(cursor.getString(2));
                model.setColor(Integer.parseInt(cursor.getString(3)));
                model.setPluginId(cursor.getInt(4));
                model.setFavorite(cursor.getInt(5));
                model.setPinToTaskbar(cursor.getInt(6));
                model.setSecret(cursor.getInt(7));
                model.setNoteFlag(cursor.getInt(8));

                Log.e("secretDB ", "" + model.getSecret());

                list.add(model);
            } while (cursor.moveToNext());

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
        return list;
    }

    public List<HomeModel> selectSecretContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT note_id,title,note,color,secret FROM notes_table,plugins_table WHERE \n" +
                "note_id == plugin_rid AND secret = 1";
        Cursor cursor = db.rawQuery(query, null);
        List<HomeModel> list = new ArrayList<>();
        if (cursor.moveToFirst())
            do {
                HomeModel model = new HomeModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setNote(cursor.getString(2));
                model.setColor(Integer.parseInt(cursor.getString(3)));
                model.setSecret(cursor.getInt(4));

                Log.e("secret page", "secret" + model.getSecret());

                list.add(model);
            } while (cursor.moveToNext());
        return list;
    }

    public List<HomeModel> selectFavouriteContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT note_id,title,note,color FROM notes_table,plugins_table WHERE \n" +
                "note_id == plugin_rid AND favorite = 1";
        Cursor cursor = db.rawQuery(query, null);
        List<HomeModel> list = new ArrayList<>();
        if (cursor.moveToFirst())
            do {
                HomeModel model = new HomeModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setNote(cursor.getString(2));
                model.setColor(cursor.getInt(3));
//                model.setColor(cursor.getString(3));
//                model.setPluginId(cursor.getInt(4));
//                model.setFavorite(cursor.getInt(5));
//                model.setPinToTaskbar(cursor.getInt(6));
//                Log.e("id " ,"" + model.getId());
//                Log.e("fav " ,"" + model.getFavorite());
                list.add(model);
            } while (cursor.moveToNext());

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
        return list;
    }

    public Cursor selectPluginsContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT plugin_rid,favorite,lock,secret FROM plugins_table ", null);
        return cursor;
    }

    public String getSecretDialogPassword() {
        String password = "";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + PASSWORD + " FROM " + PASSWORD_TABLE, null);
        if (cursor.moveToFirst())
            password = cursor.getString(0);
        return password;

    }

    ////////////////////////////////////////////////////////////////////////////////////////// down error
    public Cursor selectSpecificPluginsContent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT plugin_rid,favorite,lock,secret FROM plugins_table WHERE plugin_rid=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        return cursor;
    }

//    public Cursor selectFavouriteContent() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT title,note,color FROM notes_table,plugins_table WHERE favorite = 1";
//        Cursor cursor = db.rawQuery(query, null);
//        return cursor;
//    }

    public Cursor selectTheLastRow() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + NOTES_ID + ")FROM notes_table";
        Cursor c = db.rawQuery(query, null);
        return c;
    }


}
