package com.link_intersystems.carrental.management.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.Map;

class H2GetPickupCarRepository implements GetPickupCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2GetPickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }

    @Override
    public CarRental find(BookingNumber bookingNumber) {
        Map<String, Object> carPickupRow = jdbcTemplate.queryForMap("SELECT * FROM CAR_RENTAL where BOOKING_NUMBER =?", bookingNumber.getValue());
        return toDomainModel(carPickupRow);
    }

    private CarRental toDomainModel(Map<String, Object> carPickupRow) {
        Integer bookingNumber = (Integer) carPickupRow.get("BOOKING_NUMBER");

        Driver driver = mapDriver(carPickupRow);
        CarState carState = mapCarState(carPickupRow);
        CarRental carPickup = new CarRental(new BookingNumber(bookingNumber), driver, carState);
        return carPickup;
    }

    private CarState mapCarState(Map<String, Object> carPickupRow) {
        FuelLevel fuelLevel = FuelLevel.ofPercentage((Integer) carPickupRow.get("PICKUP_CAR_STATE_FUEL"));
        Odometer odometer = new Odometer((Integer) carPickupRow.get("PICKUP_CAR_STATE_ODOMETER"));
        return new CarState(fuelLevel, odometer);
    }

    private Driver mapDriver(Map<String, Object> carPickupRow) {
        String driverFirstname = (String) carPickupRow.get("DRIVER_FIRSTNAME");
        String driverLastname = (String) carPickupRow.get("DRIVER_LASTNAME");
        String driverLicence = (String) carPickupRow.get("DRIVER_LICENCE");
        return new Driver(driverFirstname, driverLastname, driverLicence);
    }
}
