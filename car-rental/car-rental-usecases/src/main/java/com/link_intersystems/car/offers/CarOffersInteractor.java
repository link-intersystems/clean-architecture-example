package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VehicleType;
import com.link_intersystems.rental.CarRentals;
import com.link_intersystems.rental.RentalPeriod;
import com.link_intersystems.rental.RentalsByCar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarOffersInteractor implements CarOffersUseCase {

    private final CarOffersRepository repository;

    public CarOffersInteractor(CarOffersRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarOffersResponseModel findOffers(CarOffersRequestModel request) {
        List<Car> cars = findMatchingCars(request);
        List<Car> availableCars = getAvailableCars(request, cars);
        return new CarOffersResponseModel(availableCars);
    }

    private List<Car> findMatchingCars(CarOffersRequestModel request) {
        CarCriteria carCriteria = new CarCriteria();

        VehicleType vehicleType = VehicleType.valueOf(request.getVehicleType());
        carCriteria.setVehicleType(vehicleType);

        return repository.findCars(carCriteria);
    }

    private List<Car> getAvailableCars(CarOffersRequestModel request, List<Car> cars) {
        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime resiredReturnDateTime = request.getReturnDateTime();
        RentalPeriod desiredRentalPeriod = new RentalPeriod(desiredPickUpDateTime, resiredReturnDateTime);
        return filterAvailableCars(desiredRentalPeriod, cars);
    }

    private List<Car> filterAvailableCars(RentalPeriod desiredRentalPeriod, List<Car> cars) {
        List<Car> availableCars = new ArrayList<>();

        List<CarId> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());
        RentalsByCar rentalsByCar = repository.findCarRentals(carIds, desiredRentalPeriod);

        for (Car car : cars) {
            CarId carId = car.getId();
            CarRentals carRentals = rentalsByCar.getOrDefault(carId, new CarRentals());
            if (carRentals.isAvailable(desiredRentalPeriod)) {
                availableCars.add(car);
            }

        }

        return availableCars;
    }
}
