package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.carrental.management.rental.FuelLevel;
import org.junit.jupiter.api.Test;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class PickupCarRequestModelTest {

    @Test
    void equalsAndHashCode() {
        PickupCarRequestModel requestModel1 = new PickupCarRequestModel();
        DriverRequestModel driverRequestModel = new DriverRequestModel();
        requestModel1.setDriver(driverRequestModel);
        requestModel1.setOdometer(12345);
        requestModel1.setFuelLevel(FuelLevel.FULL);
        requestModel1.setBookingNumber(123);
        requestModel1.setPickupDateTime(dateTime("2023-04-01", "15:40:23"));

        PickupCarRequestModel requestModel2 = new PickupCarRequestModel();
        requestModel2.setDriver(driverRequestModel);
        requestModel2.setOdometer(12345);
        requestModel2.setFuelLevel(FuelLevel.FULL);
        requestModel2.setBookingNumber(123);
        requestModel2.setPickupDateTime(dateTime("2023-04-01", "15:40:23"));


        assertEquals(requestModel1, requestModel2);
        assertEquals(requestModel1.hashCode(), requestModel2.hashCode());

        PickupCarRequestModel requestModel3 = new PickupCarRequestModel();
        requestModel3.setDriver(driverRequestModel);
        requestModel3.setOdometer(123456);
        requestModel3.setFuelLevel(FuelLevel.HALF);
        requestModel3.setBookingNumber(1234);
        requestModel3.setPickupDateTime(dateTime("2023-04-01", "15:41:23"));

        assertNotEquals(requestModel1, requestModel3);
    }
}