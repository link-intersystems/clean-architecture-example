package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.car.CarRentalDBExtension;
import com.link_intersystems.car.VIN;
import com.link_intersystems.jdbc.test.db.AbstractDataSource;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static com.link_intersystems.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@CarRentalDBExtension
class H2CarBookingRepositoryTest {

    private H2CarBookingRepository h2CarBookingRepository;

    @BeforeEach
    void setUp(Connection connection) {
        h2CarBookingRepository = new H2CarBookingRepository(new AbstractDataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                return connection;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return connection;
            }
        });
    }

    @Test
    void findBooking() {
        Period bookingPeriod = new Period(dateTime("2023-01-13", "08:00:00"), dateTime("2023-01-14", "17:00"));
        CarBooking carBooking = h2CarBookingRepository.findBooking(new CarId(new VIN("WMEEJ8AA3FK792135")), bookingPeriod);
        assertNull(carBooking);
    }

    @Test
    void findOverlappingBookingByReturnDate() {
        Period bookingPeriod = new Period(dateTime("2023-01-13", "08:00:00"), dateTime("2023-01-15", "17:00"));
        CarBooking carBooking = h2CarBookingRepository.findBooking(new CarId(new VIN("WMEEJ8AA3FK792135")), bookingPeriod);
        assertNotNull(carBooking);
    }

    @Test
    void findOverlappingBookingByPickupDate() {
        Period bookingPeriod = new Period(dateTime("2023-01-17", "16:00:00"), dateTime("2023-01-18", "17:00"));
        CarBooking carBooking = h2CarBookingRepository.findBooking(new CarId(new VIN("WMEEJ8AA3FK792135")), bookingPeriod);
        assertNotNull(carBooking);
    }

    @Test
    void findNoCarBooking() {
        Period bookingPeriod = new Period(dateTime("2023-01-18", "16:00:00"), dateTime("2023-01-19", "07:00"));
        CarBooking carBooking = h2CarBookingRepository.findBooking(new CarId(new VIN("WMEEJ8AA3FK792135")), bookingPeriod);
        assertNull(carBooking);
    }


    @Test
    void persist() {
        Period bookingPeriod = new Period(dateTime("2023-02-18", "16:00:00"), dateTime("2023-02-19", "07:00"));
        CarBooking carBooking = new CarBooking(new CustomerId(1), new CarId(new VIN("WMEEJ8AA3FK792135")), bookingPeriod);

        h2CarBookingRepository.persist(carBooking);

        assertEquals(3, carBooking.getBookingNumber().getValue());

        findNoCarBooking();
    }

    @Test
    void isCustomerExistent() {
        assertTrue(h2CarBookingRepository.isCustomerExistent(new CustomerId(1)));
        assertFalse(h2CarBookingRepository.isCustomerExistent(new CustomerId(1000)));
    }
}