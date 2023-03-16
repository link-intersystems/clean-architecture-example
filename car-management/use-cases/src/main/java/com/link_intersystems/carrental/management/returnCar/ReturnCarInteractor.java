package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.accounting.CarRental;
import com.link_intersystems.carrental.management.accounting.Invoice;
import com.link_intersystems.carrental.management.accounting.RentalCar;
import com.link_intersystems.carrental.management.pickup.CarPickup;
import com.link_intersystems.carrental.management.pickup.CarState;
import com.link_intersystems.carrental.management.pickup.Odometer;
import com.link_intersystems.carrental.time.Period;

import java.time.LocalDateTime;

class ReturnCarInteractor implements ReturnCarUseCase {

    private ReturnCarRepository returnCarRepository;

    public ReturnCarInteractor(ReturnCarRepository returnCarRepository) {
        this.returnCarRepository = returnCarRepository;
    }

    @Override
    public ReturnCarResponseModel returnCar(ReturnCarRequestModel requestModel) {

        CarState carState = createCarState(requestModel);

        BookingNumber bookingNumber = new BookingNumber(requestModel.getBookingNumber());
        CarReturn carReturn = new CarReturn(bookingNumber, carState);

        LocalDateTime pickupDataTime = requestModel.getReturnDateTime();
        carReturn.setReturnDateTime(pickupDataTime);

        returnCarRepository.persist(carReturn);

        CarPickup carPickup = returnCarRepository.findPickup(bookingNumber);
        Period rentalPeriod = carReturn.getRentalPeriod(carPickup);

        RentalCar rentalCar = returnCarRepository.findRentalCar(bookingNumber);
        CarRental carRental = new CarRental(bookingNumber, rentalCar, rentalPeriod);

        Invoice invoice = new Invoice(carRental, rentalCar);

        ReturnCarResponseModel responseModel = new ReturnCarResponseModel();
        responseModel.setTotalAmount(invoice.getTotal().getValue());
        return responseModel;
    }

    private CarState createCarState(ReturnCarRequestModel requestModel) {
        Integer odometerValue = requestModel.getOdometer();
        Odometer odometer = Odometer.of(odometerValue);
        return new CarState(requestModel.getFuelLevel(), odometer);
    }
}
