package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakila.film.FilmFixture;
import com.link_intersystems.sakila.film.LanguageFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmListingInteractorTest {

    private FilmListingInteractor filmListingInteractor;

    @BeforeEach
    void setUp() {
        FilmListingRepository repository = mock(FilmListingRepository.class);

        FilmFixture filmFixture = new FilmFixture();
        LanguageFixture languageFixture = new LanguageFixture();
        FilmListingAnswer filmListingAnswer = new FilmListingAnswer(filmFixture, languageFixture);
        when(repository.findFilms(any(FilmCriteria.class))).thenAnswer(filmListingAnswer);

        filmListingInteractor = new FilmListingInteractor(repository);
    }

    @Test
    void listGermanFilms() {
        FilmListingRequestModel requestModel = new FilmListingRequestModel();
        requestModel.setLanguage(Locale.GERMAN);

        FilmListingResponseModel responseModel = filmListingInteractor.listFilms(requestModel);

        FilmListing filmListing = responseModel.getFilmListing();
        assertNotNull(filmListing);

        assertEquals(1, filmListing.size());
        ListedFilm listedFilm = filmListing.get(0);

        assertEquals(1001, listedFilm.getId());
        assertEquals("ZORRO ARK",listedFilm.getTitle());
        assertEquals(Locale.GERMAN,listedFilm.getLanguage());
    }
}
