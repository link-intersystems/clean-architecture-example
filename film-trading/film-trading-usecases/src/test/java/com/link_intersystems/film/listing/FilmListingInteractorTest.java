package com.link_intersystems.film.listing;

import com.link_intersystems.customer.CustomerFixture;
import com.link_intersystems.film.FilmFixture;
import com.link_intersystems.film.LanguageFixture;
import com.link_intersystems.film.RatingPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        FilmListingInteractor.Deps interactorDeps = mock(FilmListingInteractor.Deps.class);
        when(interactorDeps.getRepository()).thenReturn(repository);

        LocalDateTime fixtedDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        Clock fixedClock = Clock.fixed(fixtedDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.systemDefault());
        when(interactorDeps.getClock()).thenReturn(fixedClock);

        RatingPolicy ratingPolicy = new RatingPolicy();
        when(interactorDeps.getRatingPolicy()).thenReturn(ratingPolicy);

        filmListingInteractor = new FilmListingInteractor(interactorDeps);
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
