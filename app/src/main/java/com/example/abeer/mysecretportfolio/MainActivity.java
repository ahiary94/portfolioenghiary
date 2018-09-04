package com.example.abeer.mysecretportfolio;

import android.app.DownloadManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    //////////////////////////////// text is default

    EditText et;
    LinearLayout ll;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.main_et);
        ll = (LinearLayout) findViewById(R.id.tex_container);

        pd=new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        //   et.setPaintFlags(et.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);  fast way to make underlined text

//        portfolioDB saveDB = new portfolioDB(this);
//        SQLiteDatabase database = saveDB.getWritableDatabase();
//        Cursor c = database.rawQuery("select * from userNotes", null);
//        if (c.getCount() > 0) {
//            c.moveToFirst();
//            //   et.setText(c.getString(0));
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pluginsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
        if (item.getItemId() == R.id.plugins_save)// 4
        {
            String url1="https://mynovelsbox.000webhostapp.com/connect/myPortfolioSave.php";
            RequestQueue requestQueue=Volley.newRequestQueue(this);
           StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
               }
           })
           {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String> map=new HashMap<>();
                   map.put("id",String.valueOf(users_account.uId));
                   map.put("notey",et.getText().toString());
                   return super.getParams();
               }
           };
             requestQueue.add(request);
//            portfolioDB saveDB = new portfolioDB(this);
//            SQLiteDatabase db = saveDB.getWritableDatabase();
//             db.execSQL("insert into userNotes values (?)",new String[]{et.getText().toString()});

             }
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
        return super.onOptionsItemSelected(item);

    }


}
