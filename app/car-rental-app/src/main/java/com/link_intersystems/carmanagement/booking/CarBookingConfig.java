package com.link_intersystems.carmanagement.booking;

public class CarBookingConfig {

    public ListBookingsUseCase getListCarBookingsUseCase(ListBookingsRepository listBookingsRepository) {
        return new ListBookingsInteractor(listBookingsRepository);
    }
}
