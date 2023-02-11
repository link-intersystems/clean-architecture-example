package com.link_intersystems.film.listing;

import com.link_intersystems.film.*;
import com.link_intersystems.person.Age;
import com.link_intersystems.person.customer.CustomerFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmListingInteractorTest {

    private FilmListingInteractor filmListingInteractor;
    private RatingPolicy ratingPolicy;

    @BeforeEach
    void setUp() {
        FilmListingRepository repository = mock(FilmListingRepository.class);

        FilmFixture filmFixture = new FilmFixture();
        LanguageFixture languageFixture = new LanguageFixture();
        FindFilmsAnswer findFilmsAnswer = new FindFilmsAnswer(filmFixture, languageFixture);
        when(repository.findFilms(any(FilmCriteria.class))).thenAnswer(findFilmsAnswer);

        Answer<Category> findCategoryAnswer = new FindCategoryAnswer();
        when(repository.findCategoryByName(anyString())).thenAnswer(findCategoryAnswer);

        CustomerFixture customerFixture = new CustomerFixture();
        FindCustomerAnswer findCustomerAnswer = new FindCustomerAnswer(customerFixture);
        when(repository.findCustomer(any(Integer.class))).thenAnswer(findCustomerAnswer);

        FilmListingInteractor.Deps interactorDeps = mock(FilmListingInteractor.Deps.class);
        when(interactorDeps.getRepository()).thenReturn(repository);

        LocalDateTime fixtedDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        Clock fixedClock = Clock.fixed(fixtedDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.systemDefault());
        when(interactorDeps.getClock()).thenReturn(fixedClock);

        ratingPolicy = new RatingPolicy();
        when(interactorDeps.getRatingPolicy()).thenReturn(ratingPolicy);

        filmListingInteractor = new FilmListingInteractor(interactorDeps);
    }

    @Test
    void listFilmsByCategory() {
        FilmListingRequestModel requestModel = new FilmListingRequestModel();
        requestModel.setLanguage(Locale.ENGLISH);
        requestModel.setCustomerId(1);
        requestModel.setViewerAge(10);
        requestModel.setCategory("DOCUMENTARY");

        FilmListingResponseModel responseModel = filmListingInteractor.listFilms(requestModel);

        FilmListing filmListing = responseModel.getFilmListing();
        assertNotNull(filmListing);

        assertEquals(1, filmListing.size());
        assertFilmCategory("DOCUMENTARY", filmListing);
    }

    private void assertFilmCategory(String expectedCategory, FilmListing filmListing) {
        for (ListedFilm listedFilm : filmListing) {
            String currentCategory = listedFilm.getCategory();
            assertEquals(expectedCategory, currentCategory, "film category");
        }
    }

    @Test
    void listFilmsForA10YearOld() {
        FilmListingRequestModel requestModel = new FilmListingRequestModel();
        requestModel.setLanguage(Locale.ENGLISH);
        requestModel.setCustomerId(1);
        requestModel.setViewerAge(10);

        FilmListingResponseModel responseModel = filmListingInteractor.listFilms(requestModel);

        FilmListing filmListing = responseModel.getFilmListing();
        assertNotNull(filmListing);

        assertEquals(3, filmListing.size());
        assertFilmRating(new Age(10), filmListing);

        ListedFilm film1 = filmListing.get(0);
        assertEquals(1, film1.getId(), "filmId");
        assertEquals(Locale.ENGLISH, film1.getLanguage(), "language");
        assertEquals("ACADEMY DINOSAUR", film1.getTitle(), "title");
        assertEquals("PG", film1.getRating());
    }

    @Test
    void listFilmsForAnAdult() {
        FilmListingRequestModel requestModel = new FilmListingRequestModel();
        requestModel.setLanguage(Locale.ENGLISH);
        requestModel.setCustomerId(1);

        FilmListingResponseModel responseModel = filmListingInteractor.listFilms(requestModel);

        FilmListing filmListing = responseModel.getFilmListing();
        assertNotNull(filmListing);

        assertEquals(4, filmListing.size());
        assertFilmRating(new Age(18), filmListing);
    }

    private void assertFilmRating(Age ratingAge, FilmListing filmListing) {
        for (ListedFilm listedFilm : filmListing) {
            String currentRatingName = listedFilm.getRating();
            Rating currentRating = ratingPolicy.getRatingByName(currentRatingName);

            assertTrue(currentRating.isAgeAllowed(ratingAge));
        }
    }
}
