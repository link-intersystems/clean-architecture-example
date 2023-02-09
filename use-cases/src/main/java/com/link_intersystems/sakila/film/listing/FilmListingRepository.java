package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakilla.film.listing.Film;
import com.link_intersystems.sakilla.lender.Lender;

import java.util.List;

public interface FilmListingRepository {

    public List<Film> findFilms(FilmCriteria criteria);

    Lender findLender(Integer lenderId);
}
