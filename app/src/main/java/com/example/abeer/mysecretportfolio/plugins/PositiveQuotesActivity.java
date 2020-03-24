package com.example.abeer.mysecretportfolio.plugins;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.etsy.android.grid.StaggeredGridView;
import com.example.abeer.mysecretportfolio.AddNoteDatabase;
import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.plugins.PositiveAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PositiveQuotesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView gridView;
//        private StaggeredGridView gridView;
        private PositiveAdapter adapter;
        private AddNoteDatabase database;
        private List<Bitmap> bitmapList = new ArrayList<>();
//    private QuotesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_possitive_quotes);

        database = new AddNoteDatabase(this);
        gridView = findViewById(R.id.recyclerView_possitive_quotes);
        toolbar = findViewById(R.id.general_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //

//        database.clearQuote();
//        pushQuoteIntoDB(R.drawable.q1);
//        pushQuoteIntoDB(R.drawable.q2);
//        pushQuoteIntoDB(R.drawable.q3);
//        pushQuoteIntoDB(R.drawable.q4);
//        pushQuoteIntoDB(R.drawable.q5);
//        pushQuoteIntoDB(R.drawable.q6);
//        pushQuoteIntoDB(R.drawable.q7);
//        pushQuoteIntoDB(R.drawable.q8);
//        pushQuoteIntoDB(R.drawable.q9);
//        pushQuoteIntoDB(R.drawable.q10);

        bitmapList = database.getImage();
        adapter = new PositiveAdapter(this, bitmapList);
        Log.e("size1", "" + bitmapList.size());
        gridView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        gridView.setAdapter(adapter);
//        recyclerView = findViewById(R.id.recyclerView_possitive_quotes);
//        adapter = new QuotesRecyclerAdapter();
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerView.setAdapter(adapter);
    }

    void pushQuoteIntoDB(Integer drawable){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable);
        database.addImageQuote(getBytes(bitmap));
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
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
}
