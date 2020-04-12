package com.engfirstapp.abeer.mysecretportfolio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.engfirstapp.abeer.mysecretportfolio.models.HomeModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class TrashCanActivity extends AppCompatActivity {

    private TrashCanAdapter adapter;
    private RecyclerView recyclerView;
    private List<HomeModel> list = new ArrayList<>();
    private AddNoteDatabase database;
    private Snackbar snackbar;
    private FrameLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_can);

        coordinator = findViewById(R.id.trashCan_coordinator);
        database = new AddNoteDatabase(this);
        list = database.selectAllDeleted();
        showLog("create", "list", "" + list.size());

        recyclerView = findViewById(R.id.trashCan_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrashCanAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

     void showLog(String method, String key, String value) {
        Log.e("TrashCan", method + "/" + key + "/" + value);
    }

    public void notifyAdapter() {
        Log.e("notifyAdapter", "true");
        list = database.selectAllDeleted();
        adapter = new TrashCanAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    public void deleteTheNote(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Want Delete it?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.clearNote(id);
                showSnackbar("The note is deleted", R.drawable.ic_check);
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

    public void undoTheNote(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want recover it?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.updateDeleteFlagZero(id);
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

    void showSnackbar(String message, int drawable) {

        snackbar = Snackbar.make(coordinator, Html.fromHtml("<font color=\"#5951EE\">" + message + "</font>"), Snackbar.LENGTH_SHORT);
        View snackbarLayout = snackbar.getView();
        TextView textView = snackbarLayout.findViewById(R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
        textView.setCompoundDrawablePadding(15);
//        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
        snackbar.show();

    }
}
