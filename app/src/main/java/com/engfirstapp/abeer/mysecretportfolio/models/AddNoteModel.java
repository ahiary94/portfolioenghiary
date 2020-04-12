package com.engfirstapp.abeer.mysecretportfolio.models;

public class AddNoteModel {

    public static int bit = 0;// to distinguish if new note or editting
    // 0 => new note
    // 1 => for edit

    private int id;
    private String title;
    private String note;
    private String color;
    private int pluginsID;
    private int lock;
    private int secret;
    private int favourite;
    private int flag;// 0 => hand written, 1 => voice

    public AddNoteModel() {
    }

    public AddNoteModel(int lock, int secret, int favourite, int flag) {
        this.lock = lock;
        this.secret = secret;
        this.favourite = favourite;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getPluginsID() {
        return pluginsID;
    }

    public void setPluginsID(int pluginsID) {
        this.pluginsID = pluginsID;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
