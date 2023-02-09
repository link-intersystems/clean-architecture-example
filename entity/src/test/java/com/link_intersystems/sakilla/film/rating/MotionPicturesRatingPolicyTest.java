package com.link_intersystems.sakilla.film.rating;

import com.link_intersystems.sakilla.person.Age;
import com.link_intersystems.sakilla.person.AgeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MotionPicturesRatingPolicyTest {

    private AgeFixture ageFixture;
    private Age age;
    private MotionPicturesRatingPolicy ratingPolicy;

    @BeforeEach
    void setUp() {
        ageFixture = new AgeFixture();
        age = ageFixture.getAge();

        ratingPolicy = new MotionPicturesRatingPolicy();
    }

    @Test
    void getAllowedRagingsAge0() {
        Clock clockAtAge10 = ageFixture.getClockAtAge(10);

        List<Rating> allowedRagings = ratingPolicy.getAllowedRagings(clockAtAge10, age);
        assertEquals(2, allowedRagings.size());

        assertEquals(ratingPolicy.getRatingByName("G"), allowedRagings.get(0));
        assertEquals(ratingPolicy.getRatingByName("PG"), allowedRagings.get(1));
    }

    @Test
    void getAllowedRagingsAdult() {
        Clock clockAtAge10 = ageFixture.getClockAtAge(18);

        List<Rating> allowedRagings = ratingPolicy.getAllowedRagings(clockAtAge10, age);
        assertEquals(5, allowedRagings.size());

        assertEquals(ratingPolicy.getRatingByName("G"), allowedRagings.get(0));
        assertEquals(ratingPolicy.getRatingByName("PG"), allowedRagings.get(1));
        assertEquals(ratingPolicy.getRatingByName("PG-13"), allowedRagings.get(2));
        assertEquals(ratingPolicy.getRatingByName("R"), allowedRagings.get(3));
        assertEquals(ratingPolicy.getRatingByName("NC-17"), allowedRagings.get(4));
    }
}