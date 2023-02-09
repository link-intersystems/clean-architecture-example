package com.link_intersystems.sakila.film.listing;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class FilmCriteriaTest {

    @Test
    void setDefaultLanguage() {
        FilmCriteria filmCriteria = new FilmCriteria();
        filmCriteria.setLanguage(null);

        assertEquals(Locale.ENGLISH, filmCriteria.getLanguage());
    }
}