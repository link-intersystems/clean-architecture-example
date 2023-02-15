package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MockCarBookingRepository implements CarBookingRepository {

    private final ArgumentCaptor<CarBooking> carBookingArgumentCaptor;
    private CarBookingRepository repository = mock(CarBookingRepository.class);

    public MockCarBookingRepository() {
        Answer<CarBooking> findBookingAnswer = new FindBookingsAnswer();
        when(repository.findBooking(any(CarId.class), any(Period.class))).thenAnswer(findBookingAnswer);
        carBookingArgumentCaptor = ArgumentCaptor.forClass(CarBooking.class);
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        return repository.findBooking(carId, bookingPeriod);
    }

    @Override
    public void persist(CarBooking carBooking) {
        repository.persist(carBooking);
    }

    public CarBooking getLastPersistedCarBooking() {
        verify(repository).persist(carBookingArgumentCaptor.capture());
        return carBookingArgumentCaptor.getValue();
    }
}
