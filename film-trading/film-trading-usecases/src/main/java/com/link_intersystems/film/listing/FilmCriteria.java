package com.link_intersystems.film.listing;

import com.link_intersystems.film.Category;
import com.link_intersystems.film.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FilmCriteria {

    public static final Locale DEFAULT_LANGUAGE = Locale.ENGLISH;
    private Locale language = DEFAULT_LANGUAGE;
    private List<Rating> ratings = Collections.emptyList();
    private Category category;

    public void setLanguage(Locale language) {
        if (language == null) {
            language = DEFAULT_LANGUAGE;
        }
        this.language = language;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = new ArrayList<>(ratings);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
