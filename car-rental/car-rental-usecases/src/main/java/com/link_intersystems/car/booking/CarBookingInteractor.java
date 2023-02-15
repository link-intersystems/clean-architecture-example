package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;

import java.time.LocalDateTime;

public class CarBookingInteractor implements CarBookingUseCase {

    private CarBookingRepository repository;

    public CarBookingInteractor(CarBookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarBookingResponseModel bookCar(CarBookingRequestModel request) throws CarNotAvailableException {
        CarId carId = new CarId(request.getCarId());

        LocalDateTime pickUpDateTime = request.getPickUpDateTime();
        LocalDateTime returnDateTime = request.getReturnDateTime();
        Period bookingPeriod = new Period(pickUpDateTime, returnDateTime);
        CarBooking carBooking = repository.findBooking(carId, bookingPeriod);

        if (carBooking != null) {
            throw new CarNotAvailableException(request.getCarId(), pickUpDateTime, returnDateTime);
        }

        return new CarBookingResponseModel();
    }
}
