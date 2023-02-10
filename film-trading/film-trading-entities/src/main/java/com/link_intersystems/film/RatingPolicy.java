package com.link_intersystems.film;

import com.link_intersystems.person.Age;

import java.util.ArrayList;
import java.util.List;

public class RatingPolicy {

    private List<Rating> ratings = new ArrayList<>();

    public RatingPolicy() {
        ratings.add(Rating.GENERAL_AUDIENCES);
        ratings.add(Rating.PARENTAL_GUIDANCE);
        ratings.add(Rating.PARENTAL_GUIDANCE_13);
        ratings.add(Rating.RESTRICTED);
        ratings.add(Rating.NO_ONE_17_AND_UNDER_ADMITTED);
    }

    public List<Rating> getAllowedRatings(Age age) {
        List<Rating> allowedRatings = new ArrayList<>();

        for (Rating rating : ratings) {
            if (rating.isAgeAllowed(age)) {
                allowedRatings.add(rating);
            }
        }

        return allowedRatings;
    }

    public Rating getRatingByName(String name) {
        return ratings.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }
}
