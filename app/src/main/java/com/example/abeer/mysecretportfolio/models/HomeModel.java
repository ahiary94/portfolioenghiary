package com.example.abeer.mysecretportfolio.models;

import java.io.Serializable;

public class HomeModel implements Serializable {

    private int id;
    private String title;
    private String note;
    private int color;
    private int pluginId;
    private int favorite;
    private int pinToTaskbar;
    private int secret;

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPluginId() {
        return pluginId;
    }

    public void setPluginId(int pluginId) {
        this.pluginId = pluginId;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getPinToTaskbar() {
        return pinToTaskbar;
    }

    public void setPinToTaskbar(int pinToTaskbar) {
        this.pinToTaskbar = pinToTaskbar;
    }
}
