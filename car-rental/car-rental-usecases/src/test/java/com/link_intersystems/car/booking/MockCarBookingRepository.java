package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockCarBookingRepository implements CarBookingRepository {

    private CarBookingRepository repository = mock(CarBookingRepository.class);

    public MockCarBookingRepository() {
        Answer<CarBooking> findBookingAnswer = new FindBookingsAnswer();
        when(repository.findBooking(any(CarId.class), any(Period.class))).thenAnswer(findBookingAnswer);
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        return repository.findBooking(carId, bookingPeriod);
    }
}
