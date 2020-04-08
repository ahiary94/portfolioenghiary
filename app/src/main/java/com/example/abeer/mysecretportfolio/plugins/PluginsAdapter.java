package com.example.abeer.mysecretportfolio.plugins;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.AddNoteDatabase;
import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PluginsAdapter extends RecyclerView.Adapter<PluginsAdapter.PluginsViewHolder> {
    private List<HomeModel> list;
    private PluginsGridActivity gridActivity;
    private HomeModel modelForEditNote = new HomeModel();
    private AddNoteDatabase addNoteDatabase;

    public PluginsAdapter(List<HomeModel> list, PluginsGridActivity gridActivity, AddNoteDatabase addNoteDatabase) {
        this.list = list;
        this.gridActivity = gridActivity;
        this.addNoteDatabase = addNoteDatabase;
    }

    @NonNull
    @Override
    public PluginsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_layout, parent, false);
        return new PluginsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PluginsViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.title.setBackgroundResource(list.get(position).getColor());
        holder.textView.setText(list.get(position).getNote());
        holder.textView.setBackgroundResource(list.get(position).getColor());
        holder.date.setText(list.get(position).getDate());
        Log.e("secret", "date" + list.get(position).getDate());

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                gridActivity.deleteTheNote(list.get(position).getId(), list.get(position).getTitle());
//                return false;
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
                        modelForEditNote.setSecret(cursor.getInt(3));

                    } while (cursor.moveToNext());
                }
                gridActivity.goToAddNotePageForEditting(modelForEditNote, 3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PluginsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView title;
        TextView date;

        public PluginsViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.home_add_new_note);
            title = itemView.findViewById(R.id.textView_favorite_title);
            date = itemView.findViewById(R.id.textView_favorite_date);

        }
    }
}
