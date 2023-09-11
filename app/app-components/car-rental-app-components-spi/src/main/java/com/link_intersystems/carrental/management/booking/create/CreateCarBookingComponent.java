package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.carrental.components.AOPConfig;

public abstract class CreateCarBookingComponent {

    private final AOPConfig aopConfig;

    public CreateCarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public CreateCarBookingUseCase getCreateCarBookingUseCase() {
        CreateCarBookingRepository repository = getRepository();
        CreateCarBookingInteractor interactor = new CreateCarBookingInteractor(repository);
        return aopConfig.applyAOP(CreateCarBookingUseCase.class, interactor);
    }

    protected abstract CreateCarBookingRepository getRepository();
}
