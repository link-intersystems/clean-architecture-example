package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.accounting.RentalCar;
import com.link_intersystems.carrental.management.pickup.CarPickup;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2ReturnCarRepository implements ReturnCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2ReturnCarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void persist(CarReturn carReturn) {

    }

    @Override
    public CarPickup findPickup(BookingNumber bookingNumber) {
        return null;
    }

    @Override
    public RentalCar findRentalCar(BookingNumber bookingNumber) {
        return null;
    }
}
