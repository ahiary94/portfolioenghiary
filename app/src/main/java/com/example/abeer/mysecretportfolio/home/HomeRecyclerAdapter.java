package com.example.abeer.mysecretportfolio.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abeer.mysecretportfolio.R;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private ArrayList<HomeModel> list;
    private Context context;

    public HomeRecyclerAdapter(ArrayList<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_layout, parent, false);
//        Log.e("adapter", "true");
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getNote());
        holder.textView.setBackgroundResource(list.get(position).getDrawable());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
