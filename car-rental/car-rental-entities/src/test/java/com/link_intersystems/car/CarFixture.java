package com.link_intersystems.car;

import com.link_intersystems.EntityFixture;

import java.util.List;

public class CarFixture extends EntityFixture<Car> {

    private CarWhitebox carWhitebox = new CarWhitebox();

    @Override
    protected void init(List<Car> entities) {
        entities.add(createAudiA6());
        entities.add(createBMW530());
        entities.add(createVWTRoc());
        entities.add(createFiat500());
        entities.add(createOpelCorsa());
        entities.add(createAudiQ3());
    }

    private Car createOpelCorsa() {
        Car car = new Car();
        carWhitebox.setId(car, 1);
        carWhitebox.setName(car, "Open Corsa");
        carWhitebox.setVehicleType(car, VehicleType.MICRO);
        return car;
    }

    private Car createFiat500() {
        Car car = new Car();
        carWhitebox.setId(car, 2);
        carWhitebox.setName(car, "Fiat 500");
        carWhitebox.setVehicleType(car, VehicleType.MICRO);
        return car;
    }

    private Car createVWTRoc() {
        Car car = new Car();
        carWhitebox.setId(car, 3);
        carWhitebox.setName(car, "VW T-Roc");
        carWhitebox.setVehicleType(car, VehicleType.SUV);
        return car;
    }

    private Car createBMW530() {
        Car car = new Car();
        carWhitebox.setId(car, 4);
        carWhitebox.setName(car, "BMW 530");
        carWhitebox.setVehicleType(car, VehicleType.SEDAN);
        return car;
    }

    private Car createAudiA6() {
        Car car = new Car();
        carWhitebox.setId(car, 5);
        carWhitebox.setName(car, "Audi A6");
        carWhitebox.setVehicleType(car, VehicleType.SEDAN);
        return car;
    }


    private Car createAudiQ3() {
        Car car = new Car();
        carWhitebox.setId(car, 6);
        carWhitebox.setName(car, "AUDI Q3");
        carWhitebox.setVehicleType(car, VehicleType.SUV);
        return car;
    }
}
