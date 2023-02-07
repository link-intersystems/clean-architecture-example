package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakilla.film.listing.Film;

import java.util.List;

public interface FilmListingRepository {

    public List<Film> findFilms(FilmCriteria criteria);
}
