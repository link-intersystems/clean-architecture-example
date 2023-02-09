package com.link_intersystems.film;

import com.link_intersystems.lender.Age;

import java.time.Clock;

public interface Rating {
    String getName();

    boolean isAgeAllowed(Age age, Clock clock);
}
