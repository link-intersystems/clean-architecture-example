package com.link_intersystems.sakila.film.listing;

import java.util.Locale;

public class FilmListingRequestModel {

    private Integer lenderId;
    private Locale language;

    public void setLenderId(Integer lenderId) {
        this.lenderId = lenderId;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Locale getLanguage() {
        return language;
    }

    public Integer getLenderId() {
        return lenderId;
    }
}
