package com.link_intersystems.sakila.film.listing;

import java.util.Locale;

public class FilmCriteria {

    public static final Locale DEFAULT_LANGUAGE = Locale.ENGLISH;
    private Locale language = DEFAULT_LANGUAGE;

    public void setLanguage(Locale language) {
        if (language == null) {
            language = DEFAULT_LANGUAGE;
        }
        this.language = language;
    }

    public Locale getLanguage() {
        return language;
    }
}
