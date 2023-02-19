package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarId;
import com.link_intersystems.car.booking.CarBookinsByCar;
import com.link_intersystems.car.rental.RentalCar;
import com.link_intersystems.time.Period;

import java.util.List;

interface CarOffersRepository {

    List<RentalCar> findRentalCars(CarCriteria criteria);

    CarBookinsByCar findCarBookins(List<CarId> carIds, Period desiredPeriod);

    CarsById findCars(List<CarId> carIds);
}
