package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalInteractor;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import com.link_intersystems.carrental.management.booking.UpdateCarBookingRentalUseCase;

public abstract class CreateCarBookingComponent {

    private final AOPConfig aopConfig;

    public CreateCarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public CreateCarBookingUseCase getCreateCarBookingUseCase() {
        CreateCarBookingRepository repository = getCreateCarBookingRepository();
        CreateCarBookingInteractor interactor = new CreateCarBookingInteractor(repository);
        return aopConfig.applyAOP(CreateCarBookingUseCase.class, interactor);
    }

    protected abstract CreateCarBookingRepository getCreateCarBookingRepository();

    public UpdateCarBookingRentalUseCase getUpdateCarBookingRentalUseCase() {
        UpdateCarBookingRentalRepository repository = getUpdateCarBookingRentalRepository();
        UpdateCarBookingRentalUseCase interactor = new UpdateCarBookingRentalInteractor(repository);
        return aopConfig.applyAOP(UpdateCarBookingRentalUseCase.class, interactor);
    }

    protected abstract UpdateCarBookingRentalRepository getUpdateCarBookingRentalRepository();
}
