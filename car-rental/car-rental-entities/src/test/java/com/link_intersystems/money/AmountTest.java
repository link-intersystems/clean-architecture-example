package com.link_intersystems.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountTest {

    @Test
    void multiply() {
        assertEquals(new Amount("37.50"), new Amount("12.5").multiply(3));
    }
}