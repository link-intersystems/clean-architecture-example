package com.link_intersystems.film;

import com.link_intersystems.person.Age;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RatingPolicyTest {

    private static final short LOWER = -1;

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
    void includes() {
        assertTrue(Rating.NO_ONE_17_AND_UNDER_ADMITTED.includes(Rating.RESTRICTED));
        assertTrue(Rating.RESTRICTED.includes(Rating.PARENTAL_GUIDANCE_13));
        assertTrue(Rating.PARENTAL_GUIDANCE_13.includes(Rating.PARENTAL_GUIDANCE));
        assertTrue(Rating.PARENTAL_GUIDANCE.includes(Rating.GENERAL_AUDIENCES));
        assertTrue(Rating.GENERAL_AUDIENCES.includes(Rating.PARENTAL_GUIDANCE));

        assertFalse(Rating.RESTRICTED.includes(Rating.NO_ONE_17_AND_UNDER_ADMITTED));
        assertFalse(Rating.PARENTAL_GUIDANCE_13.includes(Rating.RESTRICTED));
        assertFalse(Rating.PARENTAL_GUIDANCE.includes(Rating.PARENTAL_GUIDANCE_13));
    }

    @Test
    void ratingToString() {
        List<Rating> allowedRagings = ratingPolicy.getAllowedRatings(new Age(30));
        allowedRagings.forEach(Object::toString);
    }
}