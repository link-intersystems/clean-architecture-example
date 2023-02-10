package com.link_intersystems.film.listing;

import java.util.Locale;

public class FilmListingRequestModel {

    private Integer customerId;
    private Locale language;
    private Integer viewerAge;
    private String category;

    public void setViewerAge(Integer viewerAge) {
        this.viewerAge = viewerAge;
    }

    public Integer getViewerAge() {
        return viewerAge;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Locale getLanguage() {
        return language;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
