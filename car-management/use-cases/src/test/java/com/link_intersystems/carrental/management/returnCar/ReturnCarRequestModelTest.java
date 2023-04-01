package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.carrental.management.rental.FuelLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ReturnCarRequestModelTest {

    @Test
    void equalsAndHashCode() {
        ReturnCarRequestModel requestModel1 = new ReturnCarRequestModel();
        requestModel1.setOdometer(1234);
        requestModel1.setFuelLevel(FuelLevel.HALF);
        requestModel1.setBookingNumber(123);
        requestModel1.setReturnDateTime(dateTime("2023-04-01", "15:08:23"));

        ReturnCarRequestModel requestModel2 = new ReturnCarRequestModel();
        requestModel2.setOdometer(1234);
        requestModel2.setFuelLevel(FuelLevel.HALF);
        requestModel2.setBookingNumber(123);
        requestModel2.setReturnDateTime(dateTime("2023-04-01", "15:08:23"));

        assertEquals(requestModel1, requestModel2);
        assertEquals(requestModel1.hashCode(), requestModel2.hashCode());

        ReturnCarRequestModel requestModel3 = new ReturnCarRequestModel();
        requestModel3.setOdometer(12345);
        requestModel3.setFuelLevel(FuelLevel.HALF);
        requestModel3.setBookingNumber(123);
        requestModel3.setReturnDateTime(dateTime("2023-04-01", "15:08:23"));

        assertNotEquals(requestModel1, requestModel3);

    }
}