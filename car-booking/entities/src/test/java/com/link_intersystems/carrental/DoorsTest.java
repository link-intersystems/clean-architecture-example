package com.link_intersystems.carrental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorsTest {

    @Test
    void wrongValues() {
        assertThrows(IllegalArgumentException.class, () -> new Doors(0));
    }

    @Test
    void getValue() {
        assertEquals(2, new Doors(2).getValue());
    }

    @Test
    void equalsAndHashCode() {
        Doors doors1 = new Doors(2);
        Doors doors2 = new Doors(2);

        assertEquals(doors1, doors2);
        assertEquals(doors1.hashCode(), doors2.hashCode());

        Doors doors3 = new Doors(3);
        assertNotEquals(doors1, doors3);
    }
}