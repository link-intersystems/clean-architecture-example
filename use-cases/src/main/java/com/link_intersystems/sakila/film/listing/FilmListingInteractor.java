package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakilla.film.listing.Film;
import com.link_intersystems.sakilla.film.listing.Language;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FilmListingInteractor implements FilmListingUseCase {
    Map<Language, Locale> languageMapping = new HashMap<>();

    private FilmListingRepository repository;

    public FilmListingInteractor(FilmListingRepository repository) {
        this.repository = repository;

        languageMapping.put(Language.ENGLISH, Locale.ENGLISH);
        languageMapping.put(Language.GERMAN, Locale.GERMAN);
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
        listedFilm.setTitle(film.getTitle());
        Language language = film.getLanguage();

        listedFilm.setLanguage(languageMapping.get(language));
        return listedFilm;
    }

}
