package com.example.abeer.mysecretportfolio.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {

     TextView textView;
     TextView title;

    public HomeViewHolder(View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.home_add_new_note);
        title = itemView.findViewById(R.id.textView_favorite_title);
    }
}
