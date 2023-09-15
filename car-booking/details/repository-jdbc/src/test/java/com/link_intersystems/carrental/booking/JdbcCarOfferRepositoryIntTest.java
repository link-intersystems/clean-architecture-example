package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;
import com.link_intersystems.carrental.time.PeriodBuilder;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CarRentalDBExtension
class JdbcCarOfferRepositoryIntTest {

    private JdbcCarOfferRepository repository;

    @BeforeEach
    void setUp(Connection connection) {
        repository = new JdbcCarOfferRepository(new JdbcTemplate(() -> connection));
    }

    @Test
    void findRentalCars() {
        CarOfferRepository.CarCriteria criteria = new CarOfferRepository.CarCriteria();
        criteria.setVehicleType(VehicleType.SUV);

        List<RentalCar> rentalCars = repository.findRentalCars(criteria);

        assertEquals(1, rentalCars.size());
        assertEquals("YV4952CYXE1702329", rentalCars.get(0).getCarId().getValue());
    }

    @Test
    void findCarBookings() {
        Period period = PeriodBuilder.from("2023-01-01", "08:00:00").to("2023-03-01", "08:00:00");
        CarId carId = new CarId(new VIN("WMEEJ8AA3FK792135"));

        CarBookinsByCar carBookingByCar = repository.findCarBookings(Arrays.asList(carId), period);

        assertEquals(1, carBookingByCar.size());
        List<CarBooking> carBookings = carBookingByCar.get(carId);
        assertEquals(2, carBookings.size());
    }

    @Test
    void findCars() {
        CarId carId = new CarId(new VIN("WMEEJ8AA3FK792135"));

        CarsById cars = repository.findCars(Arrays.asList(carId));

        assertEquals(1, cars.size());
    }
}