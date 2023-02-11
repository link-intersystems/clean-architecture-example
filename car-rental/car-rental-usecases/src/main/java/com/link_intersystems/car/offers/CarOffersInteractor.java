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

        CarCriteria carCriteria = new CarCriteria();

        VehicleType vehicleType = VehicleType.valueOf(request.getVehicleType());
        carCriteria.setVehicleType(vehicleType);

        List<Car> cars = repository.findCars(carCriteria);


        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime resiredReturnDateTime = request.getReturnDateTime();
        RentalPeriod desiredRentalPeriod = new RentalPeriod(desiredPickUpDateTime, resiredReturnDateTime);
        List<Car> availableCars = findAvailableCars(desiredRentalPeriod, cars);

        CarOffersResponseModel responseModel = new CarOffersResponseModel();

        CarOffers carOffers = mapCars(availableCars);
        responseModel.setCarOffers(carOffers);

        return responseModel;
    }

    private List<Car> findAvailableCars(RentalPeriod desiredRentalPeriod, List<Car> cars) {
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

    private CarOffers mapCars(List<Car> cars) {
        CarOffers carOffers = new CarOffers();

        for (Car car : cars) {
            CarOffer carOffer = map(car);
            carOffers.addListedFilm(carOffer);
        }

        return carOffers;
    }

    private CarOffer map(Car car) {
        CarOffer carOffer = new CarOffer();
        carOffer.setId(car.getId().getValue());
        carOffer.setName(car.getName());
        carOffer.setVerhicleType(car.getVehicleType().name());
        return carOffer;
    }

}
