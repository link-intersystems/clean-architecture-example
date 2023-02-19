package com.link_intersystems.person.customer;

import com.link_intersystems.time.EnableFixedClock;
import com.link_intersystems.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableFixedClock
class CustomerTest {

    private Customer customer;
    private CustomerFixture customerFixture;

    @BeforeEach
    void setup() {
        customerFixture = new CustomerFixture();
        customer = customerFixture.getById(1);

    }

    @Test
    @FixedClock("2018-07-18 23:59:59")
    void age17(Clock aSecondBefore18) {
        assertEquals(17, customer.getAge(aSecondBefore18).getYears());
    }

    @Test
    @FixedClock("2018-07-19 00:00:00")
    void age18(Clock firstSecond18) {
        assertEquals(18, customer.getAge(firstSecond18).getYears());
    }
}