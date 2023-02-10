package com.link_intersystems.customer;

import com.link_intersystems.lender.Age;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgeTest {

    private Age age;
    private AgeFixture ageFixture;

    @BeforeEach
    void setup() {
        ageFixture = new AgeFixture();
        age = ageFixture.getAge();

    }

    @Test
    void age17() {
        Clock aSecondBefore18 = ageFixture.getClockBeforeAge(18);

        assertEquals(17, age.getYears(aSecondBefore18));
    }

    @Test
    void age18() {
        Clock firstSecond18 = ageFixture.getClockAtAge(18);

        assertEquals(18, age.getYears(firstSecond18));
    }
}