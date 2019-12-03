package com.example.abeer.mysecretportfolio.plugins.positivequotes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;

public class QuotesRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    public QuotesRecyclerViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView_possitive_quotes);
    }
}
