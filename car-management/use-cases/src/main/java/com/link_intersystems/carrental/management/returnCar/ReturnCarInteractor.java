package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.management.rental.CarState;
import com.link_intersystems.carrental.management.rental.Odometer;

import java.time.LocalDateTime;

class ReturnCarInteractor implements ReturnCarUseCase {

    private ReturnCarRepository returnCarRepository;

    public ReturnCarInteractor(ReturnCarRepository returnCarRepository) {
        this.returnCarRepository = returnCarRepository;
    }

    @Override
    public void returnCar(ReturnCarRequestModel requestModel) {

        BookingNumber bookingNumber = new BookingNumber(requestModel.getBookingNumber());
        CarRental carRental = returnCarRepository.find(bookingNumber);

        CarState returnCarState = createCarState(requestModel);
        LocalDateTime returnDateTime = requestModel.getReturnDateTime();
        carRental.returnCar(returnCarState, returnDateTime);

        returnCarRepository.update(carRental);
    }

    private CarState createCarState(ReturnCarRequestModel requestModel) {
        Integer odometerValue = requestModel.getOdometer();
        Odometer odometer = Odometer.of(odometerValue);
        return new CarState(requestModel.getFuelLevel(), odometer);
    }
}
