package com.example.abeer.mysecretportfolio.plugins.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.models.FavouriteModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {

    private List<FavouriteModel> list;

    public FavouriteRecyclerAdapter(List<FavouriteModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_favorite, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.note.setText(list.get(position).getNote());
        holder.note.setBackgroundResource(list.get(position).getBackgroundColor());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
