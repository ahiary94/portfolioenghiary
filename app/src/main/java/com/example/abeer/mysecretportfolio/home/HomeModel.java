package com.example.abeer.mysecretportfolio.home;

import com.example.abeer.mysecretportfolio.R;

public class HomeModel {

    private String note;

    private int drawable;

    public HomeModel() {
        this.drawable = R.drawable.rosewallpaper;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
