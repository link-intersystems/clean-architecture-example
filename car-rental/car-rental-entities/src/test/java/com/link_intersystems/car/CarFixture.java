package com.link_intersystems.car;

import java.util.ArrayList;
import java.util.List;

public class CarFixture {

    private CarWhitebox carWhitebox = new CarWhitebox();
    private List<Car> cars = new ArrayList<>();

    public CarFixture() {
        cars.add(createAudiA6());
        cars.add(createBMW530());
        cars.add(createVWTRoc());
        cars.add(createFiat500());
        cars.add(createOpelCorsa());
        cars.add(createAudiQ3());
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

    public List<Car> getFilms() {
        return cars;
    }
}
