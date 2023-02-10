package com.link_intersystems.film.listing;

import com.link_intersystems.film.Film;
import com.link_intersystems.film.Language;
import com.link_intersystems.lender.Lender;
import com.link_intersystems.film.Rating;
import com.link_intersystems.film.RatingPolicy;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FilmListingInteractor implements FilmListingUseCase {

    private final Clock clock;
    private final RatingPolicy ratingPolicy;

    public static interface Deps {
        public FilmListingRepository getRepository();
        public Clock getClock();
        public RatingPolicy getRatingPolicy();
    }

    Map<Language, Locale> languageMapping = new HashMap<>();

    private final FilmListingRepository repository;

    public FilmListingInteractor(Deps deps) {
        repository = deps.getRepository();
        clock = deps.getClock();
        ratingPolicy = deps.getRatingPolicy();

        languageMapping.put(Language.ENGLISH, Locale.ENGLISH);
        languageMapping.put(Language.GERMAN, Locale.GERMAN);
    }

    @Override
    public FilmListingResponseModel listFilms(FilmListingRequestModel request) {

        Integer lenderId = request.getLenderId();

        Lender lender = repository.findLender(lenderId);

        FilmCriteria filmCriteria = new FilmCriteria();
        filmCriteria.setLanguage(request.getLanguage());

        List<Rating> allowedRatings = ratingPolicy.getAllowedRatings(clock, lender.getAge());
        filmCriteria.setRatings(allowedRatings);

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
        listedFilm.setRating(film.getRating().getName());
        return listedFilm;
    }

}
