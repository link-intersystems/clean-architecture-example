package com.link_intersystems.carrental.management.pickup.list;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class H2ListPickupCarRepository implements ListPickupCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2ListPickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }

    @Override
    public List<CarRental> findAll() {
        List<Map<String, Object>> carPickupResult = jdbcTemplate.queryForList("SELECT * FROM CAR_RENTAL WHERE RETURN_TIME IS NULL");
        return toDomainModels(carPickupResult);
    }

    private List<CarRental> toDomainModels(List<Map<String, Object>> carPickupResult) {
        List<CarRental> carPickups = new ArrayList<>();

        for (Map<String, Object> carPickupRow : carPickupResult) {
            Integer bookingNumber = (Integer) carPickupRow.get("BOOKING_NUMBER");

            Driver driver = mapDriver(carPickupRow);
            CarState carState = mapCarState(carPickupRow);
            CarRental carPickup = new CarRental(new BookingNumber(bookingNumber), driver, carState);
            carPickups.add(carPickup);
        }

        return carPickups;
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
