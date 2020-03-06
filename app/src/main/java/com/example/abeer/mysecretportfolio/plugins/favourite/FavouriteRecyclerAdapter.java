package com.example.abeer.mysecretportfolio.plugins.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.models.FavouriteModel;

import java.util.List;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder> {

    private List<FavouriteModel> list;

    public FavouriteRecyclerAdapter(List<FavouriteModel> list) {
        this.list = list;
        Log.e("size favourite", "" + list.size());
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_favorite, parent, false);
        return new FavouriteViewHolder(view);
    }

    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.note.setText(list.get(position).getNote());
        holder.note.setBackgroundResource(list.get(position).getBackgroundColor());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class FavouriteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView note;

        public FavouriteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_favorite_title);
            note = itemView.findViewById(R.id.textView_favorite_note);
        }
    }

}
