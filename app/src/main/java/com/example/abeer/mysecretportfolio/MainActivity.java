package com.example.abeer.mysecretportfolio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.abeer.mysecretportfolio.addnote.AddNoteActivity;
import com.example.abeer.mysecretportfolio.home.HomePageFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    private EditText et;
    private ProgressDialog pd;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private HomePageFragment homePageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        et = findViewById(R.id.home_fragment_editText);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigation_view);
        toolbar = findViewById(R.id.general_toolbar);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        manager = getSupportFragmentManager();
        homePageFragment = new HomePageFragment();
        transaction = manager.beginTransaction();
        transaction.add(R.id.main_drawer_layout, homePageFragment);
        transaction.commit();

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_note_btn) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);

        }

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;//super.onOptionsItemSelected(item)
    }

//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();

//        if (item.getItemId() == R.id.plugins_background) //1
//        {
//
//            //openDialog(false);
//        }
///////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_delete)//2
//        {
//            // deleteFrag DF=new deleteFrag();
//            //transaction.replace(R.id.tex_container,DF);
//            //transaction.commit();
//            AlertDialog.Builder alertB = new AlertDialog.Builder(this)
//                    .setIcon(R.drawable.trash)
//                    .setTitle("My Portofolio ")
//                    .setMessage("Are you want delete ?");
//
//            alertB.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    portfolioDB pdb = new portfolioDB(MainActivity.this);
//                    SQLiteDatabase db = pdb.getWritableDatabase();
//                    //   db.execSQL("update userNotes set note=?",new String[]{""});
//
//                }
//            });
//
//            alertB.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//
//                    dialog.cancel();
//
//                }
//            });
//
//            alertB.setCancelable(true);
//            AlertDialog dialog = alertB.create();
//            dialog.show();
//        }
//////////////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_exit)//3
//        {
//            AlertDialog.Builder alertB = new AlertDialog.Builder(this)
//                    .setIcon(R.drawable.exit)
//                    .setTitle("My Portfolio")
//                    .setMessage("Are you want to exit ?");
//
//            alertB.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    users_account.usersAccount = "";
//                    finish();
//                }
//            });
//
//            alertB.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//
//                    dialog.cancel();
//
//                }
//            });
//
//            alertB.setCancelable(false);
//            AlertDialog alertDialog = alertB.create();
//            alertDialog.show();
//
//        }
//////////////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_save)// 4
//        {
//            String url1 = "https://mynovelsbox.000webhostapp.com/connect/myPortfolioSave.php";
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("id", String.valueOf(users_account.uId));
//                    map.put("notey", et.getText().toString());
//                    return super.getParams();
//                }
//            };
//            requestQueue.add(request);
//            portfolioDB saveDB = new portfolioDB(this);
//            SQLiteDatabase db = saveDB.getWritableDatabase();
//             db.execSQL("insert into userNotes values (?)",new String[]{et.getText().toString()});

//}
//////////////////////////////////////////////////////////////////////////////////////////
//        if (item.getItemId() == R.id.plugins_edit)//5
//
//        {
//            Intent obj=getIntent();
//            String url="https://mynovelsbox.000webhostapp.com/connect/myPortfolioEdit.php?notey="+et.getText().toString()+
//                    "&usery="+users_account.usersAccount+"&passy="+obj.getStringExtra("password") ;
//
//            RequestQueue rq = Volley.newRequestQueue(this);
//            StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, "Failed...", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            rq.add(sr);
//        }

//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
