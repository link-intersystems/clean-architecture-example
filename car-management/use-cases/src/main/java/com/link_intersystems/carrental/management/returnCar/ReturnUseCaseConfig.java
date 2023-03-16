package com.link_intersystems.carrental.management.returnCar;

public class ReturnUseCaseConfig {

    public ReturnCarUseCase getReturnCarUseCase(ReturnCarRepository returnCarRepository) {
        return new ReturnCarInteractor(returnCarRepository);
    }

}
