package com.link_intersystems.carrental.booking;

import java.util.LinkedList;
import java.util.Queue;

public class CarBookingUseCaseMock implements CarBookingUseCase {

    private Queue<CarBookingResponseModel> responses = new LinkedList<>();

    @Override
    public CarBookingResponseModel bookCar(CarBookingRequestModel request) throws CarBookingException {
        return responses.remove();
    }

    public void addResponse(CarBookingResponseModel responseModel) {
        responses.offer(responseModel);
    }
}
