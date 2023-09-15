package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.CarRentalDBExtension;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@CarRentalDBExtension
class JdbcCarBookingRepositoryIntTest {

    private JdbcCarBookingRepository h2CarBookingRepository;

    @BeforeEach
    void setUp(Connection connection) {
        h2CarBookingRepository = new JdbcCarBookingRepository(new JdbcTemplate(() -> connection));
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
    void findCustomer() {
        Customer customer = new Customer(new CustomerId(1), "Nick", "Wahlberg");
        assertEquals(customer, h2CarBookingRepository.findCustomer(new CustomerId(1)));
        assertNull(h2CarBookingRepository.findCustomer(new CustomerId(1000)));
    }
}