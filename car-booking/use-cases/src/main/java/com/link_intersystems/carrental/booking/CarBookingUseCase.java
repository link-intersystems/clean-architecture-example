package com.link_intersystems.carrental.booking;

public interface CarBookingUseCase {

    public CarBookingResponseModel bookCar(CarBookingRequestModel request) throws CarBookingException;
}
