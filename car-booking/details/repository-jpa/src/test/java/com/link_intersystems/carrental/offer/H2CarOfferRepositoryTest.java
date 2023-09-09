package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.booking.CarBooking;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;
import com.link_intersystems.carrental.time.PeriodBuilder;
import com.link_intersystems.jdbc.test.db.h2.H2Database;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CarRentalDBExtension
class H2CarOfferRepositoryIntTest {

    private JpaCarOfferRepository repository;
    private EntityManager entityManager;

    @BeforeEach
    void setUp(H2Database connection) {
        entityManager = new JpaBootstrap(connection).create().createEntityManager();
        repository = new JpaCarOfferRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
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