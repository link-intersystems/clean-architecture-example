package com.link_intersystems.carrental.management.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.pickup.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class H2GetPickupCarRepository implements GetPickupCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2GetPickupCarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CarPickup find(BookingNumber bookingNumber) {
        Map<String, Object> carPickupRow = jdbcTemplate.queryForMap("SELECT * FROM CAR_PICKUP where BOOKING_NUMBER =?", bookingNumber.getValue());
        return toDomainModel(carPickupRow);
    }

    private CarPickup toDomainModel(Map<String, Object> carPickupRow) {
        Integer bookingNumber = (Integer) carPickupRow.get("BOOKING_NUMBER");

        Driver driver = mapDriver(carPickupRow);
        CarState carState = mapCarState(carPickupRow);
        CarPickup carPickup = new CarPickup(new BookingNumber(bookingNumber), driver, carState);
        return carPickup;
    }

    private CarState mapCarState(Map<String, Object> carPickupRow) {
        FuelLevel fuelLevel = FuelLevel.ofPercentage((Integer) carPickupRow.get("CAR_STATE_FUEL"));
        Odometer odometer = new Odometer((Integer) carPickupRow.get("CAR_STATE_ODOMETER"));
        return new CarState(fuelLevel, odometer);
    }

    private Driver mapDriver(Map<String, Object> carPickupRow) {
        String driverFirstname = (String) carPickupRow.get("DRIVER_FIRSTNAME");
        String driverLastname = (String) carPickupRow.get("DRIVER_LASTNAME");
        String driverLicence = (String) carPickupRow.get("DRIVER_LICENCE");
        return new Driver(driverFirstname, driverLastname, driverLicence);
    }
}
