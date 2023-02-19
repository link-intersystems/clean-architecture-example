package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VehicleType;
import com.link_intersystems.car.booking.CarBooking;
import com.link_intersystems.car.booking.CarBookingFixture;
import com.link_intersystems.car.booking.CarBookinsByCar;
import com.link_intersystems.car.rental.RentalCar;
import com.link_intersystems.car.rental.RentalCarFixture;
import com.link_intersystems.time.Period;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockCarOffersRepository implements CarOffersRepository {

    private RentalCarFixture carFixture;
    private final CarBookingFixture carBookingFixture;

    public MockCarOffersRepository(RentalCarFixture carFixture, CarBookingFixture carBookingFixture) {
        this.carFixture = carFixture;
        this.carBookingFixture = carBookingFixture;
    }

    @Override
    public List<RentalCar> findRentalCars(CarCriteria criteria) {
        return applyCriteria(carFixture, criteria);
    }

    private List<RentalCar> applyCriteria(List<RentalCar> rentalCars, CarCriteria carCriteria) {
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<RentalCar> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(fr.getCar().getVehicleType());

        return rentalCars.stream()
                .filter(vehicleTypePredicate)
                .collect(Collectors.toList());
    }

    @Override
    public CarBookinsByCar findCarBookins(List<CarId> carIds, Period desiredPeriod) {
        List<CarBooking> carBookings = carBookingFixture.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .filter(cr -> cr.getBookingPeriod().overlaps(desiredPeriod))
                .collect(Collectors.toList());
        return new CarBookinsByCar(carBookings);
    }
}
