package com.example.abeer.mysecretportfolio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abeer on 9/5/2017.
 */

public class portfolioDB extends SQLiteOpenHelper {
    public portfolioDB(Context context) {
        super(context,"userDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL("create table Users(userAcoo text,password text )");
        sdb.execSQL("create table userNotes(note text)");
    //   sqLiteDatabase.execSQL("create table users(accNo text,password text,credit integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
