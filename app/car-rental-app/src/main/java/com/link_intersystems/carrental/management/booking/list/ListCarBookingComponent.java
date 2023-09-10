package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.main.AOPConfig;

public abstract class ListCarBookingComponent {

    private final AOPConfig aopConfig;

    public ListCarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public ListBookingsUseCase getListBookingsUseCase() {
        H2ListBookingsRepository repository = getRepository();
        ListBookingsInteractor interactor = new ListBookingsInteractor(repository);
        return aopConfig.applyAOP(ListBookingsUseCase.class, interactor);
    }

    protected abstract H2ListBookingsRepository getRepository();
}
