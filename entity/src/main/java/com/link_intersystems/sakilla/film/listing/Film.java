package com.link_intersystems.sakilla.film.listing;

import java.time.Duration;
import java.time.Year;

public class Film {

    private int id;
    private String title;
    private String description;
    private Year releaseYear;
    private Language language;
    private Duration length;
    private Amount rentalRate;
    private Rating rating;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public Duration getLength() {
        return length;
    }

    public Amount getRentalRate() {
        return rentalRate;
    }

    public Rating getRating() {
        return rating;
    }
}
