package com.link_intersystems.film;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FilmRecord {

    public int id;
    public String title;
    public String description;
    public LocalDate releaseYear;
    public int languageId;
    public int rentalDuration;
    public double rentalRate;
    public int length;
    public double replacementCost;
    public String rating;
    public String specialFeatures;
    public LocalDateTime lastUpdate;

}
