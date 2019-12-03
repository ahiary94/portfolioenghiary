package com.example.abeer.mysecretportfolio.home;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.AddNoteDatabase;
import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.util.ArrayList;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private ArrayList<HomeModel> list;
    private Context context;
    private HomeModel modelForEditNote = new HomeModel();
    private HomeView homeView;
    private AddNoteDatabase addNoteDatabase;

    public HomeRecyclerAdapter(ArrayList<HomeModel> list, Context context, HomeView homeView, AddNoteDatabase addNoteDatabase) {
        this.list = list;
        this.context = context;
        this.homeView = homeView;
        this.addNoteDatabase = addNoteDatabase;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_layout, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.textView.setText(list.get(position).getNote());
        holder.textView.setBackgroundResource(list.get(position).getColor());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(position).getId();
                modelForEditNote.setId(id);
                modelForEditNote.setTitle(list.get(position).getTitle());
                modelForEditNote.setNote(list.get(position).getNote());
                modelForEditNote.setColor(list.get(position).getColor());
                Cursor cursor = addNoteDatabase.selectSpecificPluginsContent(id);
                if (cursor.moveToFirst()) {
                    do {
                        modelForEditNote.setPluginId(cursor.getInt(0));
                        modelForEditNote.setFavorite(cursor.getInt(1));
//                        Log.e("fav adapter", "" + cursor.getInt(1));
                        modelForEditNote.setPinToTaskbar(cursor.getInt(2));

                    } while (cursor.moveToNext());
                }
                homeView.goToAddNotePageForEditting(modelForEditNote);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(position).getId();
                modelForEditNote.setId(id);
                modelForEditNote.setTitle(list.get(position).getTitle());
                modelForEditNote.setNote(list.get(position).getNote());
                modelForEditNote.setColor(list.get(position).getColor());
                Cursor cursor = addNoteDatabase.selectSpecificPluginsContent(id);
                if (cursor.moveToFirst()) {
                    do {
                        modelForEditNote.setPluginId(cursor.getInt(0));
                        modelForEditNote.setFavorite(cursor.getInt(1));
//                        Log.e("fav adapter", "" + cursor.getInt(1));
                        modelForEditNote.setPinToTaskbar(cursor.getInt(2));

                    } while (cursor.moveToNext());
                }
                homeView.goToAddNotePageForEditting(modelForEditNote);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}