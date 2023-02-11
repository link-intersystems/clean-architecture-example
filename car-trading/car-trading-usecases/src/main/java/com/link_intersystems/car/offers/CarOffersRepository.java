package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.rental.RentalsByCar;

import java.util.List;

public interface CarOffersRepository {

    public List<Car> findCars(CarCriteria criteria);

    RentalsByCar findCarRentals(List<CarId> carIds);
}
