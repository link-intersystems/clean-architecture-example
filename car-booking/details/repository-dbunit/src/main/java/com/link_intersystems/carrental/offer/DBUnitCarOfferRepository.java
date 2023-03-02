package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.DataSetRegistry;
import com.link_intersystems.carrental.VehicleType;
import com.link_intersystems.carrental.booking.CarBooking;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.time.Period;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DBUnitCarOfferRepository implements CarOfferRepository {

    private final DataSetRegistry dataSetRegistry;

    public DBUnitCarOfferRepository(DataSetRegistry dataSetRegistry) {
        this.dataSetRegistry = Objects.requireNonNull(dataSetRegistry);
    }

    @Override
    public List<RentalCar> findRentalCars(CarCriteria criteria) {
        List<RentalCar> rentalcar = dataSetRegistry.getDomainEntities("rentalcar");
        return applyCriteria(rentalcar, criteria);
    }

    private List<RentalCar> applyCriteria(List<RentalCar> rentalCars, CarCriteria carCriteria) {
        List<CarId> carIds = rentalCars.stream().map(RentalCar::getCarId).collect(Collectors.toList());
        CarsById carsById = findCars(carIds);
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<RentalCar> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(carsById.getCar(fr.getCarId()).getVehicleType());

        return rentalCars.stream()
                .filter(vehicleTypePredicate)
                .collect(Collectors.toList());
    }

    @Override
    public CarBookinsByCar findCarBookings(List<CarId> carIds, Period desiredPeriod) {
        List<CarBooking> carBookingList = dataSetRegistry.getDomainEntities("carbooking");
        List<CarBooking> carBookings = carBookingList.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .filter(cr -> cr.getBookingPeriod().overlaps(desiredPeriod))
                .collect(Collectors.toList());
        return new CarBookinsByCar(carBookings);
    }

    @Override
    public CarsById findCars(List<CarId> carIds) {
        List<Car> cars = dataSetRegistry.getDomainEntities("car");
        List<Car> filteredCars = cars.stream().filter(c -> carIds.contains(c.getId())).collect(Collectors.toList());
        return new CarsById(filteredCars);
    }

}

