package com.link_intersystems.film.rating;

import com.link_intersystems.customer.AgeFixture;
import com.link_intersystems.film.Rating;
import com.link_intersystems.film.RatingPolicy;
import com.link_intersystems.lender.Age;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RatingPolicyTest {

    private AgeFixture ageFixture;
    private Age age;
    private RatingPolicy ratingPolicy;

    @BeforeEach
    void setUp() {
        ageFixture = new AgeFixture();
        age = ageFixture.getAge();

        ratingPolicy = new RatingPolicy();
    }

    @Test
    void getAllowedRagingsAge0() {
        Clock clockAtAge10 = ageFixture.getClockAtAge(10);

        List<Rating> allowedRagings = ratingPolicy.getAllowedRatings(clockAtAge10, age);
        assertEquals(2, allowedRagings.size());

        assertEquals(ratingPolicy.getRatingByName("G"), allowedRagings.get(0));
        assertEquals(ratingPolicy.getRatingByName("PG"), allowedRagings.get(1));
    }

    @Test
    void getAllowedRagingsAdult() {
        Clock clockAtAge18 = ageFixture.getClockAtAge(18);

        List<Rating> allowedRagings = ratingPolicy.getAllowedRatings(clockAtAge18, age);
        assertEquals(5, allowedRagings.size());

        assertEquals(ratingPolicy.getRatingByName("G"), allowedRagings.get(0));
        assertEquals(ratingPolicy.getRatingByName("PG"), allowedRagings.get(1));
        assertEquals(ratingPolicy.getRatingByName("PG-13"), allowedRagings.get(2));
        assertEquals(ratingPolicy.getRatingByName("R"), allowedRagings.get(3));
        assertEquals(ratingPolicy.getRatingByName("NC-17"), allowedRagings.get(4));
    }

    @Test
    void ratingToString() {
        Clock clockAtAge18 = ageFixture.getClockAtAge(18);

        List<Rating> allowedRagings = ratingPolicy.getAllowedRatings(clockAtAge18, age);
        allowedRagings.forEach(Object::toString);
    }
}