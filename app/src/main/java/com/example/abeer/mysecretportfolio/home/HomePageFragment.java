package com.example.abeer.mysecretportfolio.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abeer.mysecretportfolio.R;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;
    private ArrayList<HomeModel> list = new ArrayList<>();
    private HomeModel model = new HomeModel();
    private HomeModel model1 = new HomeModel();
    private HomeModel model2 = new HomeModel();

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerView = view.findViewById(R.id.home_recyclerView);
//        model.setDrawable(R.drawable.rosewallpaper);

        model.setNote("Never give up");
        model.setDrawable(R.drawable.rosewallpaper);
        list.add(model);

        model1.setNote("hello this is the first note");
        model1.setDrawable(R.color.colorAccent);
        list.add(model1);

        model2.setNote("dream all the time");
        model2.setDrawable(R.color.colorPrimary);
        list.add(model2);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new HomeRecyclerAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }



}
