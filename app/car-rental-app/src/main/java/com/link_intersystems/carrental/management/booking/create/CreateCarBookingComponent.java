package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.carrental.main.AOPConfig;

public abstract class CreateCarBookingComponent {

    private final AOPConfig aopConfig;

    public CreateCarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public CreateCarBookingUseCase getCreateCarBookingUseCase() {
        H2CreateCarBookingRepository repository = getRepository();
        CreateCarBookingInteractor interactor = new CreateCarBookingInteractor(repository);
        return aopConfig.applyAOP(CreateCarBookingUseCase.class, interactor);
    }

    protected abstract H2CreateCarBookingRepository getRepository();
}
