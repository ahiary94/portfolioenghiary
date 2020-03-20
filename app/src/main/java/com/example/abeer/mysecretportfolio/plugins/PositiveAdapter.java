package com.example.abeer.mysecretportfolio.plugins;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.models.PositiveQuoteModel;
import com.example.abeer.mysecretportfolio.plugins.positivequotes.PositiveQuotesList;

import java.util.ArrayList;
import java.util.List;

public class PositiveAdapter extends BaseAdapter {

//    private List<PositiveQuoteModel> list;
    private PositiveQuotesList quotesList = new PositiveQuotesList();
    private ArrayList<Integer> colorList = quotesList.returnColor();
    private ArrayList<String> quoteList = quotesList.returnQuotes();

//    public PositiveAdapter(PositiveQuotesList quotesList) {
//        this.quotesList = quotesList;
//    }

    @Override
    public int getCount() {
        return quoteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_row_possitive_quotes, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textView_possitive_quotes);
        textView.setText(quoteList.get(position));
        textView.setBackgroundResource(colorList.get(position));

        return convertView;
    }


}
