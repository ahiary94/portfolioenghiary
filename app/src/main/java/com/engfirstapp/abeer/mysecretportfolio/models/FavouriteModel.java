package com.engfirstapp.abeer.mysecretportfolio.models;

public class FavouriteModel {

    private int backgroundColor;

    private String title;

    private String note;

    private int id;

    public FavouriteModel() {
    }

    public FavouriteModel(int id, String title, String note, int backgroundColor) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.backgroundColor = backgroundColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
