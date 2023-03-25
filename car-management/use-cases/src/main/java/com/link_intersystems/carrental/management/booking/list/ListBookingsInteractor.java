package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.management.booking.CarBooking;

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
        CarBookingResponseModel carBookingResponseModel = new CarBookingResponseModel();
        carBookingResponseModel.setBookingNumber(carBooking.getBookingNumber().getValue());
        VIN vin = carBooking.getVin();
        carBookingResponseModel.setVin(vin.getValue());
        return carBookingResponseModel;
    }
}
