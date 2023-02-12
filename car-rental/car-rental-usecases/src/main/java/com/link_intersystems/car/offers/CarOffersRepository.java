package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;
import com.link_intersystems.rental.RentalRatesByCar;
import com.link_intersystems.rental.RentalsByCar;

import java.util.List;

interface CarOffersRepository {

    List<Car> findCars(CarCriteria criteria);

    RentalsByCar findCarRentals(List<CarId> carIds, Period desiredPeriod);

    RentalRatesByCar findRentalRates(List<CarId> carIds);
}
