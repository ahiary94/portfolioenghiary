package com.example.abeer.mysecretportfolio.plugins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.abeer.mysecretportfolio.AddNoteDatabase;
import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.models.FavouriteModel;
import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class PluginsGridActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FavouriteModel> list = new ArrayList<>();
    private List<HomeModel> secretList = new ArrayList<>();
    private AddNoteDatabase database;
    private PluginsAdapter adapter;
    private FavouriteRecyclerAdapter favouriteAdapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugins_grid);

        toolbar = findViewById(R.id.general_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.plugins_activity_recyclerView);
        database = new AddNoteDatabase(getBaseContext());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

//        getIntent();
        int flag = getIntent().getIntExtra("flag", 0);
        Log.e("flag", "" + flag);
        if (flag == 11) {
            // secret
            setTitle("Secret Notes");
            secretList.clear();
            secretList = database.selectSecretContent();
            adapter = new PluginsAdapter(secretList);
            recyclerView.setAdapter(adapter);
        } else if (flag == 12) {
            // favourite
//            list.clear();
//            list = database.selectFavouriteContent();dddd
            setTitle("Favourite Notes");
            list.clear();
            list = database.selectFavouriteContent();
            favouriteAdapter = new FavouriteRecyclerAdapter(list);
            recyclerView.setAdapter(favouriteAdapter);
//            Intent intent = new Intent(this, FavouriteActivity.class);
//            startActivity(intent);
        }


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
}
