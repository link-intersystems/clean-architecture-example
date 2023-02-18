package com.link_intersystems.car.booking;

import com.link_intersystems.time.EnableFixedClock;
import com.link_intersystems.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.link_intersystems.time.LocalDateTimeUtils.dateTime;
import static org.junit.jupiter.api.Assertions.*;

@EnableFixedClock
class CarBookingInteractorTest {

    private CarBookingInteractor carBookingInteractor;
    private MockCarBookingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockCarBookingRepository();
        carBookingInteractor = new CarBookingInteractor(repository);
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
        request.setCarId(2);
        request.setPickUpDateTime(dateTime("2018-05-13", "08:00:00"));
        request.setReturnDateTime(dateTime("2018-05-16", "17:00:00"));

        CarBookingResponseModel responseModel = carBookingInteractor.bookCar(request);

        assertEquals("1", responseModel.getBookingNumber());

        CarBooking carBooking = repository.getLastPersistedCarBooking();
        assertNotNull(carBooking);
        assertEquals(2, carBooking.getCarId().getValue());
        assertEquals(1, carBooking.getCustomerId().getValue());
        assertEquals(request.getPickUpDateTime(), carBooking.getBookingPeriod().getBegin());
        assertEquals(request.getReturnDateTime(), carBooking.getBookingPeriod().getEnd());
    }

    @Test
    @FixedClock("2018-05-01 08:00:00")
    void bookUnknwonCustomer() {
        CarBookingRequestModel request = new CarBookingRequestModel();
        request.setCustomerId(1000);
        request.setCarId(2);
        request.setPickUpDateTime(dateTime("2018-05-13", "08:00:00"));
        request.setReturnDateTime(dateTime("2018-05-16", "17:00:00"));

        assertThrows(CarBookingException.class, () -> carBookingInteractor.bookCar(request));
    }
}