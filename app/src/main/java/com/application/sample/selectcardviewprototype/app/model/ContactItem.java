package com.application.sample.selectcardviewprototype.app.model;

import android.graphics.Bitmap;

/**
 * Created by davide on 04/09/15.
 */
public class ContactItem {
//    private String name;
//    private String surname;
//    private final String position;
//    private Bitmap thumbnail;
    private Name name;
    private Location location;
    private String id;
    private final String gender;
    private Picture picture;
    private final String phone;
    private final String email;

    /**
     *
     * @param gender
     * @param id
     * @param picture
     * @param name
     */
    public ContactItem(String gender, String id, Picture picture, Name name, String phone,
                       String email, Location location) {
        this.gender = gender;
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name.getFirst();
    }

    /**
     *
     * @return
     */
    public String getSurname() {
        return name.getLast();
    }

    /**
     *
     * @return
     */
    public String getPosition() {
        return location.getFullLocation();
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

    /**
     *
     */
    private class Name {
        private String title;
        private String first;
        private String last;

        /**
         *
         * @param title
         * @param first
         * @param last
         */
        private Name(String title, String first, String last) {
            this.title = title;
            this.first = first;
            this.last = last;
        }

        public String getTitle() {
            return title;
        }

        public String getLast() {
            return last;
        }

        public String getFirst() {
            return first;
        }
    }

    /**
     *
     */
    private class Location {
        private String street;
        private String city;
        private String state;
        private String postcode;

        private Location(String street, String city, String state, String postcode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.postcode = postcode;
        }

        /**
         *
         * @return
         */
        public String getFullLocation() {
            return street + " - " + city + " (" + state + ")";
        }
    }

    /**
     *
     */
    private class Picture {
        private String large;
        private String medium;
        private String thumbnail;

        private Picture(String large, String medium, String thumbnail) {
            this.large = large;
            this.medium = medium;
            this.thumbnail = thumbnail;
        }
    }
}
