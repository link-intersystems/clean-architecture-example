package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.VehicleType;

import java.util.List;

public interface CarOffersRepository {

    public List<Car> findFilms(CarCriteria criteria);

    VehicleType findVehicleTypeByName(String category);
}
