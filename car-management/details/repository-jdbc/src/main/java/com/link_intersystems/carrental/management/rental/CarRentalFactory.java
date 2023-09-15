package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;

import java.sql.Timestamp;
import java.util.Map;

public class CarRentalFactory {
    public CarRental create(Map<String, Object> carRentalRow) {
        Integer bookingNumber = (Integer) carRentalRow.get("BOOKING_NUMBER");

        Driver driver = createDriver(carRentalRow);
        CarState pickupCarState = createPickupCarState(carRentalRow);
        CarRental carRental = new CarRental(new BookingNumber(bookingNumber), driver, pickupCarState);

        CarState returnCarState = createReturnCarState(carRentalRow);
        carRental.setReturnCarState(returnCarState);

        Timestamp pickupTimestamp = (Timestamp) carRentalRow.get("PICKUP_TIME");
        carRental.setPickupDateTime(pickupTimestamp.toLocalDateTime());
        Timestamp returnTimestamp = (Timestamp) carRentalRow.get("RETURN_TIME");
        if (returnTimestamp != null) {
            carRental.setReturnDateTime(returnTimestamp.toLocalDateTime());
        }
        return carRental;
    }

    private CarState createPickupCarState(Map<String, Object> carPickupRow) {
        FuelLevel fuelLevel = FuelLevel.ofPercentage((Integer) carPickupRow.get("PICKUP_CAR_STATE_FUEL"));
        Odometer odometer = new Odometer((Integer) carPickupRow.get("PICKUP_CAR_STATE_ODOMETER"));

        return new CarState(fuelLevel, odometer);
    }

    private CarState createReturnCarState(Map<String, Object> carPickupRow) {
        Object fuelLevelValue = carPickupRow.get("RETURN_CAR_STATE_FUEL");
        Object odometerValue = carPickupRow.get("RETURN_CAR_STATE_ODOMETER");

        if (fuelLevelValue != null && odometerValue != null) {
            FuelLevel fuelLevel = FuelLevel.ofPercentage((Integer) fuelLevelValue);
            Odometer odometer = new Odometer((Integer) odometerValue);

            return new CarState(fuelLevel, odometer);
        }

        return null;
    }

    private Driver createDriver(Map<String, Object> carPickupRow) {
        String driverFirstname = (String) carPickupRow.get("DRIVER_FIRSTNAME");
        String driverLastname = (String) carPickupRow.get("DRIVER_LASTNAME");
        String driverLicence = (String) carPickupRow.get("DRIVER_LICENCE");
        return new Driver(driverFirstname, driverLastname, driverLicence);
    }
}
