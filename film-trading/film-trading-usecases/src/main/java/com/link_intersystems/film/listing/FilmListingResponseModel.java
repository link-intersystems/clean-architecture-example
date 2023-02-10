package com.link_intersystems.film.listing;

public class FilmListingResponseModel {

    private FilmListing filmListing = new FilmListing();

    FilmListingResponseModel() {
    }

    public FilmListing getFilmListing() {
        return filmListing;
    }

    void setFilmListing(FilmListing filmListing) {
        this.filmListing = filmListing;
    }
}
