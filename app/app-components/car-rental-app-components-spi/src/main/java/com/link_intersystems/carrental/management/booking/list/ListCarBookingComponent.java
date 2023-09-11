package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.components.AOPConfig;

public abstract class ListCarBookingComponent {

    private final AOPConfig aopConfig;

    public ListCarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public ListBookingsUseCase getListBookingsUseCase() {
        ListBookingsRepository repository = getRepository();
        ListBookingsInteractor interactor = new ListBookingsInteractor(repository);
        return aopConfig.applyAOP(ListBookingsUseCase.class, interactor);
    }

    protected abstract ListBookingsRepository getRepository();
}
