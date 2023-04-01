package com.link_intersystems.carrental.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(new CustomerId(6), "René", "Link");
    }

    @Test
    void getId() {
        assertEquals(new CustomerId(6), customer.getId());
    }

    @Test
    void getFirstname() {
        assertEquals("René", customer.getFirstname());
    }

    @Test
    void getLastname() {
        assertEquals("Link", customer.getLastname());
    }

    @Test
    void equalsAndHashCode(){
        Customer customer2 = new Customer(new CustomerId(6), "René", "Link");

        assertEquals(customer, customer2);
        assertEquals(customer.hashCode(), customer2.hashCode());

        Customer customer3 = new Customer(new CustomerId(6), "John", "Doe");
        assertNotEquals(customer, customer3);
    }
}