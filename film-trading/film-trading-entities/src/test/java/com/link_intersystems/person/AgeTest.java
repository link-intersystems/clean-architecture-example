package com.link_intersystems.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgeTest {

    @Test
    void getYears() {
        Age age = new Age(18);

        assertEquals(18, age.getYears());
    }

    @Test
    void wrongAge() {
        assertThrows(IllegalArgumentException.class, () -> new Age(-1));
    }
}