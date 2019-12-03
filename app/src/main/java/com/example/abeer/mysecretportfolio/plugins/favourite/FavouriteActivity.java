package com.example.abeer.mysecretportfolio.plugins.favourite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.AddNoteDatabase;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FavouriteRecyclerAdapter adapter;
    private AddNoteDatabase database;
    private ArrayList<FavouriteModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        toolbar = findViewById(R.id.general_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //

        database = new AddNoteDatabase(this);
        recyclerView = findViewById(R.id.recyclerView_favourite_list);
        fillFavouriteList();
        adapter = new FavouriteRecyclerAdapter(list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void fillFavouriteList() {
        Cursor cursor = database.selectFavouriteContent();
        if (cursor.moveToFirst()) {
            do {
                FavouriteModel model = new FavouriteModel(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
                Log.e("title from fav", cursor.getString(1));
                list.add(model);
            } while (cursor.moveToNext());
        }
    }
}
