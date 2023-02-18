package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarFixture;
import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VehicleType;
import com.link_intersystems.car.booking.CarBookings;
import com.link_intersystems.car.rental.*;
import com.link_intersystems.time.Period;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockCarOffersRepository implements CarOffersRepository {

    private CarFixture carFixture;
    private final CarRentalFixture carRentalFixture;
    private final RentalRateFixture rentalRateFixture;

    public MockCarOffersRepository(CarFixture carFixture, CarRentalFixture carRentalFixture, RentalRateFixture rentalRateFixture) {
        this.carFixture = carFixture;
        this.carRentalFixture = carRentalFixture;
        this.rentalRateFixture = rentalRateFixture;
    }

    @Override
    public List<Car> findCars(CarCriteria criteria) {
        return applyCriteria(carFixture, criteria);
    }

    private List<Car> applyCriteria(List<Car> cars, CarCriteria carCriteria) {
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<Car> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(fr.getVehicleType());

        return cars.stream()
                .filter(vehicleTypePredicate)
                .collect(Collectors.toList());
    }

    @Override
    public RentalsByCar findCarRentals(List<CarId> carIds, Period desiredPeriod) {
        List<CarRental> filteredCarRentals = carRentalFixture.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .filter(cr -> cr.getRentalPeriod().overlaps(desiredPeriod))
                .collect(Collectors.toList());
        return new CarBookings(filteredCarRentals).groupByCar();
    }

    @Override
    public RentalRateByCar findRentalRates(List<CarId> carIds) {
        List<RentalRate> filteredCarRentals = rentalRateFixture.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .collect(Collectors.toList());

        return new RentalRateByCar(filteredCarRentals);
    }
}
