package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.time.Period;

import java.util.List;

interface CarOffersRepository {

    List<RentalCar> findRentalCars(CarCriteria criteria);

    CarBookinsByCar findCarBookins(List<CarId> carIds, Period desiredPeriod);

    CarsById findCars(List<CarId> carIds);
}
