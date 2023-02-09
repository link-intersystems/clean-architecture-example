package com.link_intersystems.sakila.film.listing;

import java.util.Locale;

public class ListedFilm {

    private int id;
    private String title;
    private Locale language;
    private String rating;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    void setLanguage(Locale language) {
        this.language = language;
    }

    public Locale getLanguage() {
        return language;
    }

    public String getRating() {
        return rating;
    }

    void setRating(String rating) {
        this.rating = rating;
    }
}
