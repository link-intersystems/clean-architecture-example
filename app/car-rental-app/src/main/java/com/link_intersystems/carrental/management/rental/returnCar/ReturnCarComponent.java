package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.main.AOPConfig;

public abstract class ReturnCarComponent {

    private final AOPConfig aopConfig;


    public ReturnCarComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public ReturnCarUseCase getReturnUseCase() {
        ReturnCarRepository repository = getRepository();
        ReturnCarInteractor interactor = new ReturnCarInteractor(repository);
        return aopConfig.applyAOP(ReturnCarUseCase.class, interactor);
    }

    protected abstract ReturnCarRepository getRepository();
}
