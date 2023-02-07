package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakilla.film.listing.Film;

import java.util.List;

public class FilmListingInteractor implements FilmListingUseCase {

    private FilmListingRepository repository;

    public FilmListingInteractor(FilmListingRepository repository) {
        this.repository = repository;
    }

    @Override
    public FilmListingResponseModel listFilms(FilmListingRequestModel request) {

        FilmCriteria filmCriteria = new FilmCriteria();
        filmCriteria.setLanguage(request.language);
        List<Film> films = repository.findFilms(filmCriteria);

        FilmListingResponseModel responseModel = new FilmListingResponseModel();

        FilmListing filmListing = mapFilms(films);
        responseModel.setFilmListing(filmListing);

        return responseModel;
    }

    private FilmListing mapFilms(List<Film> films) {
        FilmListing filmListing = new FilmListing();

        for (Film film : films) {
            ListedFilm listedFilm = map(film);
            filmListing.addListedFilm(listedFilm);
        }

        return filmListing;
    }

    private ListedFilm map(Film film) {
        ListedFilm listedFilm = new ListedFilm();
        listedFilm.setId(film.getId());
        return listedFilm;
    }
}
