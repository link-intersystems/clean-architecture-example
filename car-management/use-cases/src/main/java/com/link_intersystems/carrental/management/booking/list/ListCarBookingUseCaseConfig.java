package com.link_intersystems.carrental.management.booking.list;

public class ListCarBookingUseCaseConfig {

    public ListBookingsUseCase getListCarBookingsUseCase(ListBookingsRepository listBookingsRepository) {
        return new ListBookingsInteractor(listBookingsRepository);
    }


}
