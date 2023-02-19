package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.car.booking.CarBookinsByCar;
import com.link_intersystems.time.Period;
import com.link_intersystems.car.rental.RentalRateByCar;
import com.link_intersystems.car.rental.RentalsByCar;

import java.util.List;

interface CarOffersRepository {

    List<Car> findCars(CarCriteria criteria);

    CarBookinsByCar findCarBookins(List<CarId> carIds, Period desiredPeriod);

    RentalRateByCar findRentalRates(List<CarId> carIds);
}
