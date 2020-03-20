package com.example.abeer.mysecretportfolio.plugins.positivequotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.etsy.android.grid.StaggeredGridView;
import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.plugins.PositiveAdapter;

public class PositiveQuotesActivity extends AppCompatActivity {

    private Toolbar toolbar;
//    private RecyclerView recyclerView;
        private StaggeredGridView gridView;
        private PositiveAdapter adapter;
//    private QuotesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_possitive_quotes);

        toolbar = findViewById(R.id.general_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //

         adapter = new PositiveAdapter();
         gridView = findViewById(R.id.recyclerView_possitive_quotes);
        gridView.setAdapter(adapter);
//        recyclerView = findViewById(R.id.recyclerView_possitive_quotes);
//        adapter = new QuotesRecyclerAdapter();
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerView.setAdapter(adapter);
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
