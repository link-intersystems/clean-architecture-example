package com.link_intersystems.carrental.management.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class ListBookingsInteractor implements ListBookingsUseCase {

    private ListBookingsRepository listBookingsRepository;

    public ListBookingsInteractor(ListBookingsRepository listBookingsRepository) {
        this.listBookingsRepository = Objects.requireNonNull(listBookingsRepository);
    }

    @Override
    public List<CarBookingResponseModel> listBookings() {
        List<CarBooking> carBookings = listBookingsRepository.findBookings();

        return toResponseModel(carBookings);
    }

    private List<CarBookingResponseModel> toResponseModel(List<CarBooking> carBookings) {
        ArrayList<CarBookingResponseModel> carBookingsResponseModels = new ArrayList<>();

        for (CarBooking carBooking : carBookings) {
            CarBookingResponseModel carBookingResponseModel = toResponseModel(carBooking);
            carBookingsResponseModels.add(carBookingResponseModel);
        }

        return carBookingsResponseModels;
    }

    private CarBookingResponseModel toResponseModel(CarBooking carBooking) {
        CarBookingResponseModel response = new CarBookingResponseModel();

        response.setBookingNumber(carBooking.getBookingNumber().getValue());
        response.setVIN(carBooking.getVin().getValue());

        CustomerResponseModel customer = new CustomerResponseModel();
        customer.setFirstname(carBooking.getCustomer().getFirstname());
        customer.setLastname(carBooking.getCustomer().getLastname());
        response.setCustomer(customer);

        return response;
    }
}
