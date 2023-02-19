package com.link_intersystems.car.booking;

public interface CarBookingUseCase {

    public CarBookingResponseModel bookCar(CarBookingRequestModel request) throws CarBookingException;
}
