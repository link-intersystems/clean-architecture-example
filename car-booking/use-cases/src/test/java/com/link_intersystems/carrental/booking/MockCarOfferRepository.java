package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.booking.CarBooking;
import com.link_intersystems.carrental.booking.CarBookingFixture;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.booking.CarOfferRepository;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.rental.RentalCarFixture;
import com.link_intersystems.carrental.time.Period;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockCarOfferRepository implements CarOfferRepository {

    private RentalCarFixture rentalCarFixture;
    private final CarBookingFixture carBookingFixture;
    private CarFixture carFixture;

    public MockCarOfferRepository(RentalCarFixture rentalCarFixture, CarBookingFixture carBookingFixture, CarFixture carFixture) {
        this.rentalCarFixture = rentalCarFixture;
        this.carBookingFixture = carBookingFixture;
        this.carFixture = carFixture;
    }

    @Override
    public List<RentalCar> findRentalCars(CarCriteria criteria) {
        List<CarId> carIds = rentalCarFixture.stream().map(RentalCar::getCarId).collect(Collectors.toList());
        CarsById carsById = findCars(carIds);
        return applyCriteria(rentalCarFixture, carsById, criteria);
    }

    private List<RentalCar> applyCriteria(List<RentalCar> rentalCars, CarsById carsById, CarCriteria carCriteria) {
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<RentalCar> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(carsById.getCar(fr.getCarId()).getVehicleType());

        return rentalCars.stream()
                .filter(vehicleTypePredicate)
                .collect(Collectors.toList());
    }

    @Override
    public CarBookinsByCar findCarBookings(List<CarId> carIds, Period desiredPeriod) {
        List<CarBooking> carBookings = carBookingFixture.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .filter(cr -> cr.getBookingPeriod().overlaps(desiredPeriod))
                .collect(Collectors.toList());
        return new CarBookinsByCar(carBookings);
    }

    @Override
    public CarsById findCars(List<CarId> carIds) {
        List<Car> cars = carFixture.stream().filter(c -> carIds.contains(c.getId())).collect(Collectors.toList());
        return new CarsById(cars);
    }
}
