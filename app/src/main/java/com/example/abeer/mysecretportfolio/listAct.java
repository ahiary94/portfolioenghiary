package com.example.abeer.mysecretportfolio;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class listAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.pluginsmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();

        if (item.getItemId()==R.id.plugins_background) //1
        {


        }

        if(item.getItemId()==R.id.plugins_delete)//2
        {

        }

        if (item.getItemId()==R.id.plugins_exit)//3
        {
            AlertDialog.Builder alertB=new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                    .setTitle("My Portfolio")
                    .setMessage("Are you want exit ?");

            alertB.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    users_account.usersAccount="";
                    android.os.Process.killProcess(android.os.Process.myPid());//exit from app
                    System.exit(1);//exit from app
                }
            });

            alertB.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    dialog.cancel();

                }
            });

            alertB.setCancelable(false);

        }

        if (item.getItemId()==R.id.plugins_save)// 4
        {

        }





        return super.onOptionsItemSelected(item);
    }
}
