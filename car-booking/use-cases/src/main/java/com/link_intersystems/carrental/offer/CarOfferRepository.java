package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;

import java.util.List;

interface CarOfferRepository {

    List<RentalCar> findRentalCars(CarCriteria criteria);

    CarBookinsByCar findCarBookings(List<CarId> carIds, Period desiredPeriod);

    CarsById findCars(List<CarId> carIds);
}
