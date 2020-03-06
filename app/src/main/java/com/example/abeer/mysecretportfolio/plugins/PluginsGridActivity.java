package com.example.abeer.mysecretportfolio.plugins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.abeer.mysecretportfolio.AddNoteDatabase;
import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.home.HomeRecyclerAdapter;
import com.example.abeer.mysecretportfolio.models.AddNoteModel;
import com.example.abeer.mysecretportfolio.models.FavouriteModel;
import com.example.abeer.mysecretportfolio.models.HomeModel;
import com.example.abeer.mysecretportfolio.plugins.favourite.FavouriteActivity;

import java.util.ArrayList;
import java.util.List;

public class PluginsGridActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FavouriteModel> list = new ArrayList<>();
    private List<HomeModel> secretList = new ArrayList<>();
    private AddNoteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugins_grid);

        recyclerView = findViewById(R.id.plugins_activity_recyclerView);
        database = new AddNoteDatabase(getBaseContext());
//        getIntent();
        int flag = getIntent().getIntExtra("flag", 0);
        Log.e("flag", "" + flag);
        if (flag == 11) {
            // secret
            secretList = database.selectSecretContent();

        } else if (flag == 12) {
            // favourite
//            list.clear();
//            list = database.selectFavouriteContent();
            Intent intent = new Intent(this, FavouriteActivity.class);
            startActivity(intent);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        adapter = new HomeRecyclerAdapter(list, getHomeContext(), this, database);
//        recyclerView.setAdapter(adapter);
    }
}
