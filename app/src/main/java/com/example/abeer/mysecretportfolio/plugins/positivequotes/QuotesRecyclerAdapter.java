package com.example.abeer.mysecretportfolio.plugins.positivequotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;

import java.util.ArrayList;

public class QuotesRecyclerAdapter extends RecyclerView.Adapter<QuotesRecyclerAdapter.QuotesViewHolder> {
    private PositiveQuotesList quotesList = new PositiveQuotesList();
    private ArrayList<Integer> colorList = quotesList.returnColor();
    private ArrayList<String> quoteList = quotesList.returnQuotes();


    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_possitive_quotes, parent, false );
        return new QuotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {
        holder.textView.setText(quoteList.get(position));
        holder.textView.setBackgroundResource(colorList.get(position));

    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public QuotesViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_possitive_quotes);
        }
    }

}
