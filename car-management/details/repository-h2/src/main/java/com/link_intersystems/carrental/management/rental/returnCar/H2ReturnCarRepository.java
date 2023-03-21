package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.accounting.RentalCar;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Map;

class H2ReturnCarRepository implements ReturnCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2ReturnCarRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }

    @Override
    public CarRental find(BookingNumber bookingNumber) {
        Map<String, Object> carRentalRow = jdbcTemplate.queryForMap("SELECT * FROM CAR_RENTAL WHERE BOOKING_NUMBER = ?", bookingNumber.getValue());
        return toDomainModel(carRentalRow);
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

    @Override
    public void persist(CarRental carRental) {
        int bookingNumber = carRental.getBookingNumber().getValue();
        LocalDateTime pickupDateTime = carRental.getPickupDateTime();
        LocalDateTime returnDateTime = carRental.getReturnDateTime();
        CarState pickupCarState = carRental.getPickupCarState();
        int pickupFuelLevel = pickupCarState.getFuelLevel().getPercent();
        int pickupOdometer = pickupCarState.getOdometer().getValue();

        CarState returnCarState = carRental.getReturnCarState();
        int returnFuelLevel = returnCarState.getFuelLevel().getPercent();
        int returnOdometer = returnCarState.getOdometer().getValue();

        int updated = jdbcTemplate.update("UPDATE CAR_RENTAL " +
                        "SET " +
                        "PICKUP_TIME = ?, " +
                        "PICKUP_CAR_STATE_FUEL = ?, " +
                        "PICKUP_CAR_STATE_ODOMETER = ?, " +
                        "RETURN_TIME = ?, " +
                        "RETURN_CAR_STATE_FUEL = ?, " +
                        "RETURN_CAR_STATE_ODOMETER = ? " +
                        "WHERE " +
                        "BOOKING_NUMBER = ?",
                pickupDateTime,
                pickupFuelLevel,
                pickupOdometer,
                returnDateTime,
                returnFuelLevel,
                returnOdometer,
                bookingNumber
        );

        if (updated != 1) {
            throw new IllegalStateException("Unable to persist CarReturn " + bookingNumber);
        }
    }

    @Override
    public RentalCar findRentalCar(BookingNumber bookingNumber) {
        return null;
    }
}
