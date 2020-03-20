package com.example.abeer.mysecretportfolio.plugins;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.util.List;

public class PluginsAdapter extends RecyclerView.Adapter<PluginsAdapter.PluginsViewHolder> {
    private List<HomeModel> list;

    public PluginsAdapter(List<HomeModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PluginsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_layout,parent, false);
        return new PluginsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PluginsViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.textView.setText(list.get(position).getNote());
        holder.textView.setBackgroundResource(list.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PluginsViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView title;

        public PluginsViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.home_add_new_note);
            title = itemView.findViewById(R.id.textView_favorite_title);
        }
    }
}
