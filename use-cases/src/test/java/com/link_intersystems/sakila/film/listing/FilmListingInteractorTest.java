package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakila.customer.CustomerFixture;
import com.link_intersystems.sakila.film.FilmFixture;
import com.link_intersystems.sakila.film.LanguageFixture;
import com.link_intersystems.sakilla.film.rating.MotionPicturesRatingPolicy;
import com.link_intersystems.sakilla.film.rating.RatingPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
        FindFilmsAnswer findFilmsAnswer = new FindFilmsAnswer(filmFixture, languageFixture);
        when(repository.findFilms(any(FilmCriteria.class))).thenAnswer(findFilmsAnswer);

        CustomerFixture customerFixture = new CustomerFixture();
        FindLenderAnswer findLenderAnswer = new FindLenderAnswer(customerFixture);
        when(repository.findLender(any(Integer.class))).thenAnswer(findLenderAnswer);

        filmListingInteractor = new FilmListingInteractor(new FilmListingInteractor.Deps() {
            @Override
            public FilmListingRepository getRepository() {
                return repository;
            }

            @Override
            public Clock getClock() {
                ZoneId zoneId = ZoneOffset.systemDefault();
                return Clock.fixed(LocalDateTime.of(2020, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC), zoneId);
            }

            @Override
            public RatingPolicy getRatingPolicy() {
                return new MotionPicturesRatingPolicy();
            }
        });
    }

    @Test
    void listFilmsForMarySmith() {
        FilmListingRequestModel requestModel = new FilmListingRequestModel();
        requestModel.setLanguage(Locale.ENGLISH);
        requestModel.setLenderId(1);

        FilmListingResponseModel responseModel = filmListingInteractor.listFilms(requestModel);

        FilmListing filmListing = responseModel.getFilmListing();
        assertNotNull(filmListing);

        assertEquals(3, filmListing.size());

        ListedFilm film1 = filmListing.get(0);
        assertEquals(1, film1.getId(), "filmId");
        assertEquals(Locale.ENGLISH, film1.getLanguage(), "language");
        assertEquals("ACADEMY DINOSAUR", film1.getTitle(), "title");
        assertEquals("PG", film1.getRating());

        ListedFilm film2 = filmListing.get(1);
        assertEquals(2, film2.getId(), "filmId");
        assertEquals(Locale.ENGLISH, film2.getLanguage(), "language");
        assertEquals("ACE GOLDFINGER", film2.getTitle(), "title");

        ListedFilm film3 = filmListing.get(2);
        assertEquals(4, film3.getId(), "filmId");
        assertEquals(Locale.ENGLISH, film3.getLanguage(), "language");
        assertEquals("AFFAIR PREJUDICE", film3.getTitle(), "title");
    }
}
