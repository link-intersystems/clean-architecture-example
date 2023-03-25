package com.link_intersystems.carrental.booking;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CarNotAvailableExceptionTest {

    @Test
    void getCarId() {
        LocalDateTime pickupDateTime = LocalDateTime.now();
        LocalDateTime returnDateTime = pickupDateTime.plusSeconds(30);
        CarNotAvailableException carNotAvailableException = new CarNotAvailableException("WMEEJ8AA3FK792135", pickupDateTime, returnDateTime);

        assertEquals("WMEEJ8AA3FK792135", carNotAvailableException.getCarId());

        assertEquals(pickupDateTime, carNotAvailableException.getPickUpDateTime());
        assertEquals(returnDateTime, carNotAvailableException.getReturnDateTime());

        assertNotNull(carNotAvailableException.getMessage());
    }
}