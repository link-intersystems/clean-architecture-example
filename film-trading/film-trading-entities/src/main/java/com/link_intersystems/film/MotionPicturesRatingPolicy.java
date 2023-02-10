package com.link_intersystems.film;

import com.link_intersystems.lender.Age;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class MotionPicturesRatingPolicy implements RatingPolicy {

    private List<Rating> ratings = new ArrayList<>();

    public MotionPicturesRatingPolicy() {
        Rating generalAudiences = new MotionPicturesRating("G", 0);
        ratings.add(generalAudiences);

        Rating parentalGuidance = new MotionPicturesRating("PG", 0);
        ratings.add(parentalGuidance);

        Rating parentalGuidance13 = new MotionPicturesRating("PG-13", 13);
        ratings.add(parentalGuidance13);

        Rating restricted = new MotionPicturesRating("R", 17);
        ratings.add(restricted);

        Rating noOne17AndUnderAdmitted = new MotionPicturesRating("NC-17", 18);
        ratings.add(noOne17AndUnderAdmitted);
    }

    @Override
    public List<Rating> getAllowedRatings(Clock clock, Age age) {
        List<Rating> allowedRatings = new ArrayList<>();

        for (Rating rating : ratings) {
            if (rating.isAgeAllowed(age, clock)) {
                allowedRatings.add(rating);
            }
        }

        return allowedRatings;
    }

    @Override
    public Rating getRatingByName(String name) {
        return ratings.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }
}
