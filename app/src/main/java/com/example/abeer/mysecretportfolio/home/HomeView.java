package com.example.abeer.mysecretportfolio.home;

import android.content.Context;

import com.example.abeer.mysecretportfolio.models.HomeModel;

public interface HomeView {

    void getRecyclerItems();

    void goToAddNotePageForEditting(HomeModel homeModel);

    Context getHomeContext();
}
