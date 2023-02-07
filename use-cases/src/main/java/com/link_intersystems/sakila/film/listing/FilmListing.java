package com.link_intersystems.sakila.film.listing;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class FilmListing extends AbstractList<ListedFilm> {

    private List<ListedFilm> listedFilms = new ArrayList<>();

    void setFilmListing(List<ListedFilm> listedFilms) {
        this.listedFilms.clear();
        this.listedFilms.addAll(listedFilms);
    }

    void addListedFilm(ListedFilm listedFilm) {
        listedFilms.add(listedFilm);
    }

    @Override
    public ListedFilm get(int index) {
        return listedFilms.get(index);
    }

    @Override
    public int size() {
        return listedFilms.size();
    }
}
