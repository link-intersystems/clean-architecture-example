package com.link_intersystems.carrental.management.booking;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalInteractor;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;

public abstract class ManagementCarBookingComponent {

    private final AOPConfig aopConfig;

    public ManagementCarBookingComponent(AOPConfig aopConfig) {
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

    public ListBookingsUseCase getListBookingsUseCase() {
        ListBookingsRepository repository = getListBookingsRepository();
        ListBookingsInteractor interactor = new ListBookingsInteractor(repository);
        return aopConfig.applyAOP(ListBookingsUseCase.class, interactor);
    }

    protected abstract ListBookingsRepository getListBookingsRepository();
}
