package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VehicleType;
import com.link_intersystems.car.booking.CarBookinsByCar;
import com.link_intersystems.car.offer.RentalOffer;
import com.link_intersystems.car.rental.RentalCar;
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
        List<RentalCar> rentalCars = findMatchingCars(request);

        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime resiredReturnDateTime = request.getReturnDateTime();
        Period desiredPeriod = new Period(desiredPickUpDateTime, resiredReturnDateTime);
        List<RentalCar> carBookins = getCarBookins(rentalCars, desiredPeriod);

        List<RentalOffer> rentalOffers = makeOffer(carBookins, desiredPeriod);

        return new CarOffersResponseModel(rentalOffers);
    }

    private List<RentalOffer> makeOffer(List<RentalCar> rentalCars, Period desiredPeriod) {
        return rentalCars.stream().map(rc -> new RentalOffer(rc, desiredPeriod)).collect(Collectors.toList());
    }


    private List<RentalCar> findMatchingCars(CarOffersRequestModel request) {
        CarCriteria carCriteria = new CarCriteria();

        VehicleType vehicleType = VehicleType.valueOf(request.getVehicleType());
        carCriteria.setVehicleType(vehicleType);

        return repository.findRentalCars(carCriteria);
    }

    private List<RentalCar> getCarBookins(List<RentalCar> rentalCars, Period rentalPeriod) {

        return filterAvailableCars(rentalPeriod, rentalCars);
    }

    private List<RentalCar> filterAvailableCars(Period desiredPeriod, List<RentalCar> rentalCars) {
        List<RentalCar> availableCars = new ArrayList<>();

        List<CarId> carIds = rentalCars.stream().map(RentalCar::getCar).map(Car::getId).collect(Collectors.toList());
        CarBookinsByCar carBookins = repository.findCarBookins(carIds, desiredPeriod);

        for (RentalCar rentalCar : rentalCars) {
            Car car = rentalCar.getCar();
            CarId carId = car.getId();
            if (!carBookins.keySet().contains(carId)) {
                availableCars.add(rentalCar);
            }
        }

        return availableCars;
    }
}
