package com.link_intersystems.film.rating;

import com.link_intersystems.person.Age;
import com.link_intersystems.film.Rating;
import com.link_intersystems.film.RatingPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RatingPolicyTest {

    private RatingPolicy ratingPolicy;

    @BeforeEach
    void setUp() {
        ratingPolicy = new RatingPolicy();
    }

    @Test
    void getAllowedRagingsAge0() {
        List<Rating> allowedRagings = ratingPolicy.getAllowedRatings(new Age(10));
        assertEquals(2, allowedRagings.size());

        assertEquals(ratingPolicy.getRatingByName("G"), allowedRagings.get(0));
        assertEquals(ratingPolicy.getRatingByName("PG"), allowedRagings.get(1));
    }

    @Test
    void getAllowedRagingsAdult() {
        List<Rating> allowedRagings = ratingPolicy.getAllowedRatings(new Age(18));
        assertEquals(5, allowedRagings.size());

        assertEquals(ratingPolicy.getRatingByName("G"), allowedRagings.get(0));
        assertEquals(ratingPolicy.getRatingByName("PG"), allowedRagings.get(1));
        assertEquals(ratingPolicy.getRatingByName("PG-13"), allowedRagings.get(2));
        assertEquals(ratingPolicy.getRatingByName("R"), allowedRagings.get(3));
        assertEquals(ratingPolicy.getRatingByName("NC-17"), allowedRagings.get(4));
    }

    @Test
    void ratingToString() {
        List<Rating> allowedRagings = ratingPolicy.getAllowedRatings(new Age(30));
        allowedRagings.forEach(Object::toString);
    }
}