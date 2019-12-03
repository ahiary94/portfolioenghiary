package com.example.abeer.mysecretportfolio.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abeer.mysecretportfolio.R;
import com.example.abeer.mysecretportfolio.addnote.AddNoteActivity;
import com.example.abeer.mysecretportfolio.AddNoteDatabase;
import com.example.abeer.mysecretportfolio.models.AddNoteModel;
import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.util.ArrayList;

public class HomePageFragment extends Fragment implements HomeView {

    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;
    private ArrayList<HomeModel> list = new ArrayList<>();
    private Cursor cursor;
    private AddNoteDatabase database;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerView = view.findViewById(R.id.home_recyclerView);
        database = new AddNoteDatabase(getHomeContext());

        getRecyclerItems();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new HomeRecyclerAdapter(list, getHomeContext(), this, database);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void getRecyclerItems() {
        cursor = database.selectAllContent();
        if (cursor.moveToFirst()) {
            do {
                HomeModel model = new HomeModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setNote(cursor.getString(2));
                model.setColor(Integer.parseInt(cursor.getString(3)));
                model.setPluginId(cursor.getInt(4));
                model.setFavorite(cursor.getInt(5));
                model.setPinToTaskbar(cursor.getInt(6));

                Log.e("id " ,"" + model.getId());
                Log.e("fav " ,"" + model.getFavorite());

                list.add(model);
            } while (cursor.moveToNext());
        }
//        cursor.close();
    }

    @Override
    public void goToAddNotePageForEditting(HomeModel model) {
        Intent intent = new Intent(getActivity(), AddNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        intent.putExtras(bundle);
        AddNoteModel.bit = 1;
//        Log.e("id editing", "" + model.getId());
//        Log.e("pluginid editing", "" + model.getPluginId());
//        Log.e("favorite editing", "" + model.getFavorite());
        startActivity(intent);

    }

    @Override
    public Context getHomeContext() {
        return getContext();
    }

}
