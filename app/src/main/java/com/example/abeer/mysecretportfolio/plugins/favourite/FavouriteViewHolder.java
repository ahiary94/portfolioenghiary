package com.example.abeer.mysecretportfolio.plugins.favourite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView note;

    public FavouriteViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textView_favorite_title);
        note = itemView.findViewById(R.id.textView_favorite_note);
    }
}
