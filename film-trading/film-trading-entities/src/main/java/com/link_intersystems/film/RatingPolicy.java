package com.link_intersystems.film;

import com.link_intersystems.lender.Age;

import java.time.Clock;
import java.util.List;

public interface RatingPolicy {
    List<Rating> getAllowedRatings(Clock clock, Age age);

    Rating getRatingByName(String name);
}
