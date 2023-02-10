package com.link_intersystems.film;

import com.link_intersystems.lender.Age;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class RatingPolicy {

    private List<Rating> ratings = new ArrayList<>();

    public RatingPolicy() {
        Rating generalAudiences = new Rating("G", 0);
        ratings.add(generalAudiences);

        Rating parentalGuidance = new Rating("PG", 0);
        ratings.add(parentalGuidance);

        Rating parentalGuidance13 = new Rating("PG-13", 13);
        ratings.add(parentalGuidance13);

        Rating restricted = new Rating("R", 17);
        ratings.add(restricted);

        Rating noOne17AndUnderAdmitted = new Rating("NC-17", 18);
        ratings.add(noOne17AndUnderAdmitted);
    }

    public List<Rating> getAllowedRatings(Clock clock, Age age) {
        List<Rating> allowedRatings = new ArrayList<>();

        for (Rating rating : ratings) {
            if (rating.isAgeAllowed(age, clock)) {
                allowedRatings.add(rating);
            }
        }

        return allowedRatings;
    }

    public Rating getRatingByName(String name) {
        return ratings.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }
}
