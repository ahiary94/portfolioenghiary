package com.engfirstapp.abeer.mysecretportfolio.plugins;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.engfirstapp.abeer.mysecretportfolio.AddNoteDatabase;
import com.engfirstapp.abeer.mysecretportfolio.R;
import com.engfirstapp.abeer.mysecretportfolio.models.HomeModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder> {

    private List<HomeModel> list;
    private PluginsGridActivity gridActivity;
    private HomeModel modelForEditNote = new HomeModel();
    private AddNoteDatabase addNoteDatabase;

    public FavouriteRecyclerAdapter(List<HomeModel> list, PluginsGridActivity gridActivity, AddNoteDatabase addNoteDatabase) {
        this.list = list;
        this.gridActivity = gridActivity;
        this.addNoteDatabase = addNoteDatabase;
        Log.e("size favourite", "" + list.size());
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_layout, parent, false);
        return new FavouriteViewHolder(view);
    }

    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.title.setBackgroundResource(list.get(position).getColor());
        holder.note.setText(list.get(position).getNote());
        holder.note.setBackgroundResource(list.get(position).getColor());
        holder.date.setText(list.get(position).getDate());

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
                gridActivity.goToAddNotePageForEditting(modelForEditNote, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class FavouriteViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout noteLayout;
        TextView note;
        TextView title, date;
        public FavouriteViewHolder(View itemView) {
            super(itemView);
            noteLayout = itemView.findViewById(R.id.home_container_note);
            note = itemView.findViewById(R.id.home_add_new_note);
            title = itemView.findViewById(R.id.textView_favorite_title);
            date = itemView.findViewById(R.id.textView_favorite_date);

        }
    }

}
