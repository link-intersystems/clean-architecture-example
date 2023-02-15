package com.link_intersystems.car.booking;

import com.link_intersystems.time.EnableFixedClock;
import com.link_intersystems.time.FixedClock;
import com.link_intersystems.time.LocalDateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableFixedClock
class CarBookingInteractorTest {

    private CarBookingInteractor carBookingInteractor;

    @BeforeEach
    void setUp() {
        MockCarBookingRepository repository = new MockCarBookingRepository();
        carBookingInteractor = new CarBookingInteractor(repository);
    }

    @Test
    @FixedClock("2018-05-20 08:00:00")
    void bookingPeriodIsInThePast() {
        CarBookingRequestModel request = new CarBookingRequestModel();
        request.setPickUpDateTime(LocalDateTimeUtils.dateTime("2018-05-13", "08:00:00"));
        request.setReturnDateTime(LocalDateTimeUtils.dateTime("2018-05-16", "17:00:00"));

        assertThrows(CarBookingException.class, () -> carBookingInteractor.bookCar(request));
    }
}