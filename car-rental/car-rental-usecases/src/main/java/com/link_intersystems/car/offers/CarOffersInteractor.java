package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VehicleType;
import com.link_intersystems.car.offer.RentalOffer;
import com.link_intersystems.car.rental.*;
import com.link_intersystems.time.Period;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CarOffersInteractor implements CarOffersUseCase {

    private final CarOffersRepository repository;

    public CarOffersInteractor(CarOffersRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarOffersResponseModel makeOffers(CarOffersRequestModel request) {
        List<Car> cars = findMatchingCars(request);

        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime resiredReturnDateTime = request.getReturnDateTime();
        Period desiredPeriod = new Period(desiredPickUpDateTime, resiredReturnDateTime);
        List<RentalCar> rentalCars = getRentalCars(cars, desiredPeriod);

        List<RentalOffer> rentalOffers = makeOffer(rentalCars, desiredPeriod);

        return new CarOffersResponseModel(rentalOffers);
    }

    private List<RentalOffer> makeOffer(List<RentalCar> rentalCars, Period desiredPeriod) {
        return rentalCars.stream().map(rc -> new RentalOffer(rc, desiredPeriod)).collect(Collectors.toList());
    }


    private List<Car> findMatchingCars(CarOffersRequestModel request) {
        CarCriteria carCriteria = new CarCriteria();

        VehicleType vehicleType = VehicleType.valueOf(request.getVehicleType());
        carCriteria.setVehicleType(vehicleType);

        return repository.findCars(carCriteria);
    }

    private List<RentalCar> getRentalCars(List<Car> cars, Period rentalPeriod) {

        List<Car> availableCars = filterAvailableCars(rentalPeriod, cars);

        List<CarId> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());
        RentalRateByCar rentalRates = repository.findRentalRates(carIds);


        return getRentalCars(availableCars, rentalRates);
    }

    private List<RentalCar> getRentalCars(List<Car> availableCars, RentalRateByCar rentalRates) {
        List<RentalCar> rentalCars = new ArrayList<>();

        for (Car availableCar : availableCars) {
            RentalRate rentalRateForCar = rentalRates.get(availableCar.getId());
            rentalCars.add(new RentalCar(availableCar, rentalRateForCar));
        }

        return rentalCars;
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
