package com.link_intersystems.sakilla.film.rating;

import com.link_intersystems.sakilla.person.Age;

import java.time.Clock;
import java.util.Collection;
import java.util.List;

public interface RatingPolicy {
    List<Rating> getAllowedRagings(Clock clock, Age age);

    Rating getRatingByName(String name);
}
