package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.booking.CarBookedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class ListBookingsInteractor implements ListBookingsUseCase, DomainEventSubscriber {

    private ListBookingsRepository listBookingsRepository;

    public ListBookingsInteractor(ListBookingsRepository listBookingsRepository) {
        this.listBookingsRepository = Objects.requireNonNull(listBookingsRepository);
    }

    @Override
    public ListBookingsResponseModel listBookings(ListBookingsRequestModel requestModel) {
        LocalDateTime from = requestModel.getFrom();
        LocalDateTime to = requestModel.getTo();

        List<CarBooking> carBookings = listBookingsRepository.findBookings(from, to);

        return toResponseModel(carBookings);
    }

    private ListBookingsResponseModel toResponseModel(List<CarBooking> carBookings) {
        ListBookingsResponseModel responseModel = new ListBookingsResponseModel();

        ArrayList<CarBookingResponseModel> carBookingsResponseModels = new ArrayList<>();
        for (CarBooking carBooking : carBookings) {
            CarBookingResponseModel carBookingResponseModel = toResponseModel(carBooking);
            carBookingsResponseModels.add(carBookingResponseModel);
        }
        responseModel.setCarBookings(carBookingsResponseModels);

        return responseModel;
    }

    private CarBookingResponseModel toResponseModel(CarBooking carBooking) {
        CarBookingResponseModel carBookingResponseModel = new CarBookingResponseModel();
        carBookingResponseModel.setBookingNumber(carBooking.getBookingNumber());
        VIN vin = carBooking.getVin();
        carBookingResponseModel.setVin(vin.getValue());
        return carBookingResponseModel;
    }

    @Override
    public boolean subscribedTo(Class<?> domainEventType) {
        return CarBookedEvent.class.isAssignableFrom(domainEventType);
    }

    @Override
    public void handleEvent(Object domainEvent) {
        CarBookedEvent carBookedEvent = (CarBookedEvent) domainEvent;
        System.out.println(carBookedEvent);
    }
}
