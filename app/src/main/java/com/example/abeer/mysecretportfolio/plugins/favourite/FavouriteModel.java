package com.example.abeer.mysecretportfolio.plugins.favourite;

public class FavouriteModel {

    private int backgroundColor;

    private String title;

    private String note;

    public FavouriteModel(String title, String note,int backgroundColor) {
        this.title = title;
        this.note = note;
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
