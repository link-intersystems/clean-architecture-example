package com.link_intersystems.carrental.management.rental.returnCar;

public class ReturnUseCaseConfig {

    public ReturnCarUseCase getReturnCarUseCase(ReturnCarRepository returnCarRepository) {
        return new ReturnCarInteractor(returnCarRepository);
    }

}
