package com.application.sample.selectcardviewprototype.app.model;

/**
 * Created by davide on 24/01/16.
 */
public class Setting {
    private String label;
    private String description;

    public Setting(String s, String s1) {
        this.label = s;
        this.description = s1;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

}
