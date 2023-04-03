package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.carrental.customer.CustomerFixture;
import com.link_intersystems.carrental.time.EnableFixedClock;
import com.link_intersystems.carrental.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@EnableFixedClock
class CarBookingInteractorTest {

    private CarBookingInteractor carBookingInteractor;
    private MockCarBookingRepository repository;
    private CarFixture carFixture;

    @BeforeEach
    void setUp() {
        CustomerFixture customers = new CustomerFixture();
        carFixture = new CarFixture();
        CarBookingFixture carBookingFixture = new CarBookingFixture(carFixture);
        repository = new MockCarBookingRepository(carFixture, customers, carBookingFixture);
        carBookingInteractor = new CarBookingInteractor(repository, new DomainEventBus());
    }

    @Test
    @FixedClock("2018-05-20 08:00:00")
    void bookingPeriodIsInThePast() {
        CarBookingRequestModel request = new CarBookingRequestModel();
        request.setPickUpDateTime(dateTime("2018-05-13", "08:00:00"));
        request.setReturnDateTime(dateTime("2018-05-16", "17:00:00"));

        assertThrows(CarBookingException.class, () -> carBookingInteractor.bookCar(request));
    }

    @Test
    @FixedClock("2018-05-01 08:00:00")
    void bookFiat500() throws CarBookingException {
        CarBookingRequestModel request = new CarBookingRequestModel();
        request.setCustomerId(1);
        request.setCarId(carFixture.getVolvoXC90().getId().getValue());
        request.setPickUpDateTime(dateTime("2018-05-13", "08:00:00"));
        request.setReturnDateTime(dateTime("2018-05-16", "17:00:00"));

        CarBookingResponseModel responseModel = carBookingInteractor.bookCar(request);

        assertEquals("1", responseModel.getBookingNumber());

        CarBooking carBooking = repository.getLastPersistedCarBooking();
        assertNotNull(carBooking);

        assertEquals("1", responseModel.getBookingNumber());
        assertEquals(carFixture.getVolvoXC90().getId().getValue(), carBooking.getCarId().getValue());
        assertEquals(1, carBooking.getCustomerId().getValue());
        assertEquals(request.getPickUpDateTime(), carBooking.getBookingPeriod().getBegin());
        assertEquals(request.getReturnDateTime(), carBooking.getBookingPeriod().getEnd());
    }

    @Test
    @FixedClock("2018-05-01 08:00:00")
    void bookUnknownCustomer() {
        CarBookingRequestModel request = new CarBookingRequestModel();
        request.setCustomerId(1000);
        request.setCarId(carFixture.getVolvoXC90().getId().getValue());
        request.setPickUpDateTime(dateTime("2018-05-13", "08:00:00"));
        request.setReturnDateTime(dateTime("2018-05-16", "17:00:00"));

        assertThrows(CarBookingException.class, () -> carBookingInteractor.bookCar(request));
    }

    @Test
    @FixedClock("2018-05-01 08:00:00")
    void unavailableCar() {
        CarBookingRequestModel request = new CarBookingRequestModel();
        request.setCustomerId(1);
        request.setCarId(carFixture.getSmartFortwo().getId().getValue());
        request.setPickUpDateTime(dateTime("2023-01-13", "08:00:00"));
        request.setReturnDateTime(dateTime("2023-01-15", "08:00:00"));

        assertThrows(CarNotAvailableException.class, () -> carBookingInteractor.bookCar(request));
    }
}