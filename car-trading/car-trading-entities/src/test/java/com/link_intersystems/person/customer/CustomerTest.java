package com.link_intersystems.person.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    private Customer customer;
    private CustomerFixture customerFixture;

    @BeforeEach
    void setup() {
        customerFixture = new CustomerFixture();
        customer = customerFixture.getById(1);

    }

    @Test
    void age17() {
        Clock aSecondBefore18 = customerFixture.getClockBeforeAge(customer.getBirthday(), 18);

        assertEquals(17, customer.getAge(aSecondBefore18).getYears());
    }

    @Test
    void age18() {
        Clock firstSecond18 = customerFixture.getClockAtAge(customer.getBirthday(), 18);

        assertEquals(18, customer.getAge(firstSecond18).getYears());
    }
}