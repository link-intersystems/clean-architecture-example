package com.link_intersystems.sakila.film.listing;

public class ListedFilm {

    private int id;
    private String title;

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
}
