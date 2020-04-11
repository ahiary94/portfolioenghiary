package com.example.abeer.mysecretportfolio.plugins;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.abeer.mysecretportfolio.AddNoteDatabase;
import com.example.abeer.mysecretportfolio.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class PositiveQuotesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView gridView;
    private PositiveAdapter adapter;
    private AddNoteDatabase database;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private AdView adView;
    private AdRequest adRequest;
    private static final int ITEM_PER_AD = 5;
    private static final int AD_HIGHT = 150;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_possitive_quotes);

        database = new AddNoteDatabase(this);
        gridView = findViewById(R.id.recyclerView_possitive_quotes);
        toolbar = findViewById(R.id.general_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //

        adView = findViewById(R.id.positive_ad);
        adRequest = new AdRequest.Builder().build();
//        adView.setAdUnitId(getString(R.string.quote_banner_id));
//        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(adRequest);

        Cursor cursor = database.selectQuotesSize();
        cursor.moveToFirst();
        count = cursor.getInt(0);
        Log.e("returned id", "" + count);
        cursor.close();
        if (count == 0) {
            pushQuoteIntoDB(R.drawable.q1);
            pushQuoteIntoDB(R.drawable.q2);
            pushQuoteIntoDB(R.drawable.q3);
            pushQuoteIntoDB(R.drawable.q4);
            pushQuoteIntoDB(R.drawable.q5);
            pushQuoteIntoDB(R.drawable.q6);
            pushQuoteIntoDB(R.drawable.q7);
            pushQuoteIntoDB(R.drawable.q8);
            pushQuoteIntoDB(R.drawable.q9);
            pushQuoteIntoDB(R.drawable.q10);
            pushQuoteIntoDB(R.drawable.q11);
            pushQuoteIntoDB(R.drawable.q12);
            pushQuoteIntoDB(R.drawable.q13);
            pushQuoteIntoDB(R.drawable.q14);
            pushQuoteIntoDB(R.drawable.q15);
            pushQuoteIntoDB(R.drawable.q16);
            pushQuoteIntoDB(R.drawable.q17);
            pushQuoteIntoDB(R.drawable.q18);
            pushQuoteIntoDB(R.drawable.q19);
            pushQuoteIntoDB(R.drawable.q20);
            pushQuoteIntoDB(R.drawable.q21);
            pushQuoteIntoDB(R.drawable.q22);
            pushQuoteIntoDB(R.drawable.q23);
            pushQuoteIntoDB(R.drawable.q24);
            pushQuoteIntoDB(R.drawable.q25);
            pushQuoteIntoDB(R.drawable.q26);
            pushQuoteIntoDB(R.drawable.q27);
        }

        new FetchImages().execute();
    }

    void pushQuoteIntoDB(Integer drawable) {
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

    class FetchImages extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            bitmapList = database.getImage();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new PositiveAdapter(PositiveQuotesActivity.this, bitmapList);
            Log.e("size1", "" + bitmapList.size());
            gridView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            gridView.setAdapter(adapter);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
