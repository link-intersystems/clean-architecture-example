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
        customer = customerFixture.getCustomer();

    }

    @Test
    void age17() {
        Clock aSecondBefore18 = customerFixture.getClockBeforeAge(18);

        assertEquals(17, customer.getAge(aSecondBefore18).getYears());
    }

    @Test
    void age18() {
        Clock firstSecond18 = customerFixture.getClockAtAge(18);

        assertEquals(18, customer.getAge(firstSecond18).getYears());
    }
}