package com.link_intersystems.film.listing;

import com.link_intersystems.film.Film;
import com.link_intersystems.person.customer.Customer;

import java.util.List;

public interface FilmListingRepository {

    public List<Film> findFilms(FilmCriteria criteria);

    Customer findCustomer(Integer lenderId);
}
