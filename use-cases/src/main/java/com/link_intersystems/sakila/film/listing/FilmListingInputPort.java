package com.link_intersystems.sakila.film.listing;

public interface FilmListingInputPort {

    public FilmListingResponseModel listFilms(FilmListingRequestModel request);
}
