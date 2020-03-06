package com.example.abeer.mysecretportfolio;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.abeer.mysecretportfolio.models.AddNoteModel;
import com.example.abeer.mysecretportfolio.home.HomePageFragment;
import com.example.abeer.mysecretportfolio.plugins.CalenderActivity;
import com.example.abeer.mysecretportfolio.plugins.PluginsGridActivity;
import com.example.abeer.mysecretportfolio.plugins.positivequotes.PositiveQuotesActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    private ProgressDialog pd;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private HomePageFragment homePageFragment;
    private AddNoteDatabase database;
//    private static final int THREAD_ID = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TrafficStats.setThreadStatsTag(THREAD_ID);
        toolbar = findViewById(R.id.general_toolbar);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigation_view);
        database = new AddNoteDatabase(this);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        getHomePage();
//
        pd = new ProgressDialog(this);
        pd.setMessage("Please waiting...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addnote, menu);
        return true;
    }

    void getHomePage() {
        manager = getSupportFragmentManager();
        homePageFragment = new HomePageFragment();
        transaction = manager.beginTransaction();
        transaction.add(R.id.main_relativeLayout_container, homePageFragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.add_note_btn) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.add_note_voice_message){

        }
        return true;//super.onOptionsItemSelected(item)
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.e("cal", "calender");
        switch (item.getItemId()) {
            case R.id.plugins_calender:
                drawerLayout.closeDrawer(Gravity.START);
                Intent calenderIntent = new Intent(MainActivity.this, CalenderActivity.class);
                startActivity(calenderIntent);
                break;
            case R.id.plugins_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are You Want to Delete All Notes?");
                builder.setIcon(R.drawable.ic_delete_black_24dp);
                builder.setPositiveButton("Ok", this);
                builder.setNeutralButton("Cancel", this);
                builder.show();
                break;
            case R.id.plugins_secret:
                Intent intent0 = new Intent(MainActivity.this, PluginsGridActivity.class);
                intent0.putExtra("flag", 11); // secret
                startActivity(intent0);
                break;
            case R.id.plugins_star_list:
                Intent intent1 = new Intent(MainActivity.this, PluginsGridActivity.class);
                intent1.putExtra("flag", 12); // favourite
                startActivity(intent1);
//                Intent intent1 = new Intent(MainActivity.this, FavouriteActivity.class);
//                startActivity(intent1);
                break;
            case R.id.plugins_positive:
                Intent intent = new Intent(MainActivity.this, PositiveQuotesActivity.class);
                startActivity(intent);
                break;
            case R.id.plugins_exit:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you want to exit?");
                builder1.setIcon(R.drawable.ic_exit_to_app_black_24dp);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
                builder1.setNeutralButton("Cancel", this);
                builder1.show();

        }
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE: {
                Toast.makeText(this, "The note is deleted", Toast.LENGTH_SHORT).show();
                database.clearDatabase();
                AddNoteModel.bit = 0;
//                finish();
                startActivity(getIntent());
                break;
            }
            case DialogInterface.BUTTON_NEUTRAL: {
                dialog.cancel();
            }
        }
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


}
