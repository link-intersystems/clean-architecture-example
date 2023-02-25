package com.link_intersystems.carrental.offers;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VehicleType;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.offer.RentalOffer;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.time.Period;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class CarOffersInteractor implements CarOffersUseCase {

    private IntputOutputMapper intputOutputMapper = new IntputOutputMapper();

    private final CarOffersRepository repository;

    public CarOffersInteractor(CarOffersRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarOffersResponseModel makeOffers(CarOffersRequestModel request) {
        List<RentalCar> rentalCars = findMatchingCars(request);

        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime desiredReturnDateTime = request.getReturnDateTime();
        Period desiredPeriod = new Period(desiredPickUpDateTime, desiredReturnDateTime);

        List<RentalCar> availableCars = filterAvailableCars(desiredPeriod, rentalCars);

        List<RentalOffer> rentalOffers = makeOffer(availableCars, desiredPeriod);

        List<CarId> carIds = rentalOffers.stream().map(RentalOffer::getRentalCar).map(RentalCar::getCarId).collect(Collectors.toList());
        CarsById carsById = repository.findCars(carIds);
        return intputOutputMapper.toOutputModel(carsById, rentalOffers);
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

    private List<RentalCar> filterAvailableCars(Period desiredPeriod, List<RentalCar> rentalCars) {
        List<CarId> carIds = rentalCars
                .stream()
                .map(RentalCar::getCarId)
                .collect(Collectors.toList());

        CarBookinsByCar carBookins = repository.findCarBookins(carIds, desiredPeriod);

        return rentalCars
                .stream()
                .filter(rc -> {
                    CarId carId = rc.getCarId();
                    return !carBookins.contains(carId);
                }).collect(Collectors.toList());
    }
}
