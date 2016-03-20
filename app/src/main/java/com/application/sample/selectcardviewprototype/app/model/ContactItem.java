package com.application.sample.selectcardviewprototype.app.model;

import android.graphics.Bitmap;

/**
 * Created by davide on 04/09/15.
 */
public class ContactItem {
    private final String position;
    private final String phone;
    private final String email;
    private String id;
    private Bitmap thumbnail;
    private String name;
    private String surname;

    /**
     *
     * @param id
     * @param thumbnail
     * @param name
     * @param surname
     */
    public ContactItem(String id, Bitmap thumbnail, String name, String surname, String phone,
                       String email, String position) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.position = position;
    }

    /**
     *
     * @return
     */
    public Bitmap getThumbnail() {
        return thumbnail;
    }

    /**
     *
     * @param thumbnail
     */
    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @return
     */
    public String getPosition() {
        return position;
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
}
