package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static com.link_intersystems.carrental.time.PeriodBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CarRentalMapperTest {

    private Map<String, Object> row;
    private CarRentalMapper mapper;

    @BeforeEach
    void setUp() {
        row = new HashMap<>();
        mapper = new CarRentalMapper();
    }

    @Test
    void toCarRental() {
        row.put("BOOKING_NUMBER", 42);
        row.put("PICKUP_TIME", Timestamp.valueOf(dateTime("2023-04-03", "08:07:42")));
        row.put("RETURN_TIME", Timestamp.valueOf(dateTime("2023-04-04", "07:03:23")));
        row.put("DRIVER_FIRSTNAME", "René");
        row.put("DRIVER_LASTNAME", "Link");
        row.put("DRIVER_LICENCE", "ABC");
        row.put("PICKUP_CAR_STATE_FUEL", 50);
        row.put("PICKUP_CAR_STATE_ODOMETER", 1234);
        row.put("RETURN_CAR_STATE_FUEL", 100);
        row.put("RETURN_CAR_STATE_ODOMETER", 12345);

        CarRental carRental = mapper.toCarRental(row);

        assertEquals(new BookingNumber(42), carRental.getBookingNumber());
        Period expectedRentalPeriod = from("2023-04-03", "08:07:42").to("2023-04-04", "07:03:23");
        assertEquals(expectedRentalPeriod, carRental.getRentalPeriod());

        Driver driver = carRental.getDriver();
        assertEquals("René", driver.getFirstname());
        assertEquals("Link", driver.getLastname());
        assertEquals("ABC", driver.getDrivingLicenceNumber());

        CarState pickupCarState = carRental.getPickupCarState();
        assertEquals(Odometer.of(1234), pickupCarState.getOdometer());
        assertEquals(FuelLevel.HALF, pickupCarState.getFuelLevel());

        CarState returnCarState = carRental.getReturnCarState();
        assertEquals(Odometer.of(12345), returnCarState.getOdometer());
        assertEquals(FuelLevel.FULL, returnCarState.getFuelLevel());
    }
}