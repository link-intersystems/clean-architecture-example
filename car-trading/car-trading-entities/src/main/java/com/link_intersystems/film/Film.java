package com.link_intersystems.film;

public class Film {

    private int id;
    private String title;
    private Language language;
    private Rating rating;
    private Category category;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Language getLanguage() {
        return language;
    }

    public Rating getRating() {
        return rating;
    }

    public Category getCategory() {
        return category;
    }
}
