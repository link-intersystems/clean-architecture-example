package com.link_intersystems.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountTest {

    @Test
    void wrongAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal("-1")));
    }

    @Test
    void properlyScaled() {
        assertEquals(new Amount("37.50"), new Amount("37.504"));
        assertEquals(new Amount("37.50"), new Amount("37.495"));
    }

    @Test
    void multiply() {
        assertEquals(new Amount("37.50"), new Amount("12.5").multiply(3));
    }

    @Test
    void hashCodeTest() {
        assertEquals(new Amount("37.50").hashCode(), new Amount("37.50").hashCode());
    }
}