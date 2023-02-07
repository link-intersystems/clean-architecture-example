package com.link_intersystems.sakila.film.listing;

public interface FilmListingUseCase {

    public FilmListingResponseModel listFilms(FilmListingRequestModel request);
}
