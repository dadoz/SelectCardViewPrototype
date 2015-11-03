package com.application.sample.selectcardviewprototype.app.model;

import android.graphics.Bitmap;

/**
 * Created by davide on 04/09/15.
 */
public class ShoppingItem {
    private String id;
    private Bitmap thumbnail;
    private String name;
    private String description;

    public ShoppingItem(String id, Bitmap thumbnail, String name, String description) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.description = description;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
}
