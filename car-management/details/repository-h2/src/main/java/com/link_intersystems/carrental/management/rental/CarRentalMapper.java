package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;

import java.sql.Timestamp;
import java.util.Map;

public class CarRentalMapper {
    public CarRental toCarRental(Map<String, Object> carRentalRow) {
        Integer bookingNumber = (Integer) carRentalRow.get("BOOKING_NUMBER");

        Driver driver = mapDriver(carRentalRow);
        CarState pickupCarState = mapPickupCarState(carRentalRow);
        CarRental carRental = new CarRental(new BookingNumber(bookingNumber), driver, pickupCarState);

        CarState returnCarState = mapReturnCarState(carRentalRow);
        carRental.setReturnCarState(returnCarState);

        Timestamp pickupTimestamp = (Timestamp) carRentalRow.get("PICKUP_TIME");
        carRental.setPickupDateTime(pickupTimestamp.toLocalDateTime());
        Timestamp returnTimestamp = (Timestamp) carRentalRow.get("RETURN_TIME");
        if (returnTimestamp != null) {
            carRental.setReturnDateTime(returnTimestamp.toLocalDateTime());
        }
        return carRental;
    }

    private CarState mapPickupCarState(Map<String, Object> carPickupRow) {
        FuelLevel fuelLevel = FuelLevel.ofPercentage((Integer) carPickupRow.get("PICKUP_CAR_STATE_FUEL"));
        Odometer odometer = new Odometer((Integer) carPickupRow.get("PICKUP_CAR_STATE_ODOMETER"));

        return new CarState(fuelLevel, odometer);
    }

    private CarState mapReturnCarState(Map<String, Object> carPickupRow) {
        Object fuelLevelValue = carPickupRow.get("RETURN_CAR_STATE_FUEL");
        Object odometerValue = carPickupRow.get("RETURN_CAR_STATE_ODOMETER");

        if (fuelLevelValue != null && odometerValue != null) {
            FuelLevel fuelLevel = FuelLevel.ofPercentage((Integer) fuelLevelValue);
            Odometer odometer = new Odometer((Integer) odometerValue);

            return new CarState(fuelLevel, odometer);
        }

        return null;
    }

    private Driver mapDriver(Map<String, Object> carPickupRow) {
        String driverFirstname = (String) carPickupRow.get("DRIVER_FIRSTNAME");
        String driverLastname = (String) carPickupRow.get("DRIVER_LASTNAME");
        String driverLicence = (String) carPickupRow.get("DRIVER_LICENCE");
        return new Driver(driverFirstname, driverLastname, driverLicence);
    }
}
