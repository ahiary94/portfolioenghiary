package com.engfirstapp.abeer.mysecretportfolio.plugins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.engfirstapp.abeer.mysecretportfolio.AddNoteActivity;
import com.engfirstapp.abeer.mysecretportfolio.AddNoteDatabase;
import com.engfirstapp.abeer.mysecretportfolio.MainActivity;
import com.engfirstapp.abeer.mysecretportfolio.R;
import com.engfirstapp.abeer.mysecretportfolio.models.AddNoteModel;
import com.engfirstapp.abeer.mysecretportfolio.models.HomeModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.engfirstapp.abeer.mysecretportfolio.MainActivity.ACTIVITY_SOURCE;

public class PluginsGridActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<HomeModel> list = new ArrayList<>();
    private List<HomeModel> secretList = new ArrayList<>();
    private AddNoteDatabase database;
    private PluginsAdapter adapter;
    private FavouriteRecyclerAdapter favouriteAdapter;
    private Toolbar toolbar;
    private MenuItem itemDelete;
    private int idDelete = 1;
    private static int flag = 0;

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
         flag = getIntent().getIntExtra("flag", 0);
        Log.e("flag", "" + flag);
       notifyAdapter();


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

    public void deleteTheNote(final int id, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want delete " + title + " note?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PluginsGridActivity.this, "The note is deleted", Toast.LENGTH_SHORT).show();
                database.updateDeleteFlag(id);
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

    public void notifyAdapter() {
        Log.e("notifyAdapter", "true");
        if (flag == 11) {
            // secret
            setTitle("Secret Notes");
            secretList.clear();
            secretList = database.selectSecretContent();
            adapter = new PluginsAdapter(secretList, this, database);
            recyclerView.setAdapter(adapter);
        } else if (flag == 12) {
            // favourite
            setTitle("Favourite Notes");
            list.clear();
            list = database.selectFavouriteContent();
            favouriteAdapter = new FavouriteRecyclerAdapter(list, this, database);
            recyclerView.setAdapter(favouriteAdapter);
        }
    }

    public void goToAddNotePageForEditting(HomeModel model,int source) { // 1 => main , 2 => fav, 3 => secret
        Intent intent = new Intent(this, AddNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        intent.putExtras(bundle);
        intent.putExtra(ACTIVITY_SOURCE, source);
        AddNoteModel.bit = 1;
//        Log.e("id editing", "" + model.getId());
//        Log.e("pluginid editing", "" + model.getPluginId());
//        Log.e("favorite editing", "" + model.getFavorite());
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent goToHome = new Intent(PluginsGridActivity.this, MainActivity.class);
        startActivity(goToHome);
        finish();
        super.onBackPressed();
    }
}
