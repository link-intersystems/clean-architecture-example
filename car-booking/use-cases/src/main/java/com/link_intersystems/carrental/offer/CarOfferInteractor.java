package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.CarsById;
import com.link_intersystems.carrental.VehicleType;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class CarOfferInteractor implements CarOfferUseCase {

    private IntputOutputMapper intputOutputMapper = new IntputOutputMapper();

    private final CarOfferRepository repository;

    public CarOfferInteractor(CarOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarOfferResponseModel makeOffers(CarOfferRequestModel request) {
        List<RentalCar> rentalCars = findMatchingCars(request);

        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime desiredReturnDateTime = request.getReturnDateTime();
        Period bookingPeriod = new Period(desiredPickUpDateTime, desiredReturnDateTime);

        List<RentalCar> availableCars = filterAvailableCars(bookingPeriod, rentalCars);

        List<RentalOffer> rentalOffers = makeOffer(availableCars, bookingPeriod);

        List<CarId> carIds = rentalOffers.stream().map(RentalOffer::getRentalCar).map(RentalCar::getCarId).collect(Collectors.toList());
        CarsById carsById = repository.findCars(carIds);
        return intputOutputMapper.toOutputModel(carsById, rentalOffers, bookingPeriod);
    }

    private List<RentalOffer> makeOffer(List<RentalCar> rentalCars, Period desiredPeriod) {
        return rentalCars.stream().map(rc -> new RentalOffer(rc, desiredPeriod)).collect(Collectors.toList());
    }


    private List<RentalCar> findMatchingCars(CarOfferRequestModel request) {
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

        CarBookinsByCar carBookins = repository.findCarBookings(carIds, desiredPeriod);

        return rentalCars
                .stream()
                .filter(rc -> {
                    CarId carId = rc.getCarId();
                    return !carBookins.contains(carId);
                }).collect(Collectors.toList());
    }
}
