package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VehicleType;
import com.link_intersystems.rental.*;
import com.link_intersystems.time.Period;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class CarOffersInteractor implements CarOffersUseCase {

    private final CarOffersRepository repository;

    public CarOffersInteractor(CarOffersRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarOffersResponseModel findOffers(CarOffersRequestModel request) {
        List<Car> cars = findMatchingCars(request);

        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime resiredReturnDateTime = request.getReturnDateTime();
        Period desiredPeriod = new Period(desiredPickUpDateTime, resiredReturnDateTime);
        List<RentalCar> availableCars = getAvailableCars(desiredPeriod, cars);

        return new CarOffersResponseModel(availableCars, desiredPeriod);
    }


    private List<Car> findMatchingCars(CarOffersRequestModel request) {
        CarCriteria carCriteria = new CarCriteria();

        VehicleType vehicleType = VehicleType.valueOf(request.getVehicleType());
        carCriteria.setVehicleType(vehicleType);

        return repository.findCars(carCriteria);
    }

    private List<RentalCar> getAvailableCars(Period desiredPeriod, List<Car> cars) {

        List<Car> availableCars = filterAvailableCars(desiredPeriod, cars);

        List<CarId> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());
        RentalRatesByCar rentalRates = repository.findRentalRates(carIds);

        Function<Car, RentalCar> rentalCarMapper = car -> {
            RentalRates rentalRatesForCar = rentalRates.get(car.getId());
            RentalRate activeRentalRate = rentalRatesForCar.getActiveForPeriod(desiredPeriod);
            return new RentalCar(car, activeRentalRate);
        };

        return availableCars.stream().map(rentalCarMapper).collect(Collectors.toList());
    }

    private List<Car> filterAvailableCars(Period desiredPeriod, List<Car> cars) {
        List<Car> availableCars = new ArrayList<>();

        List<CarId> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());
        RentalsByCar rentalsByCar = repository.findCarRentals(carIds, desiredPeriod);

        for (Car car : cars) {
            CarId carId = car.getId();
            CarRentals carRentals = rentalsByCar.getOrDefault(carId, new CarRentals());
            if (carRentals.isAvailable(desiredPeriod)) {
                availableCars.add(car);
            }
        }

        return availableCars;
    }
}
