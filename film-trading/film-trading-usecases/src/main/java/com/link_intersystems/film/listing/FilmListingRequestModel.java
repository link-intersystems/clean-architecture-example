package com.link_intersystems.film.listing;

import java.util.Locale;

public class FilmListingRequestModel {

    private Integer lenderId;
    private Locale language;
    private Integer viewerAge;

    public void setViewerAge(Integer viewerAge) {
        this.viewerAge = viewerAge;
    }

    public Integer getViewerAge() {
        return viewerAge;
    }

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
