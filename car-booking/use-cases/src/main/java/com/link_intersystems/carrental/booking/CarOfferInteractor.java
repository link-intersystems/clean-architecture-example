package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.CarsById;
import com.link_intersystems.carrental.VehicleType;
import com.link_intersystems.carrental.offer.RentalOffer;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

class CarOfferInteractor implements CarOfferUseCase {

    private final CarOfferRepository repository;
    private CarOfferModelMapper carOfferModelMapper = new CarOfferModelMapper();

    public CarOfferInteractor(CarOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarOfferResponseModel> makeOffers(CarOfferRequestModel request) {
        List<RentalCar> rentalCars = findMatchingCars(request);

        LocalDateTime desiredPickUpDateTime = request.getPickUpDateTime();
        LocalDateTime desiredReturnDateTime = request.getReturnDateTime();
        Period bookingPeriod = new Period(desiredPickUpDateTime, desiredReturnDateTime);

        List<RentalCar> availableCars = filterAvailableCars(bookingPeriod, rentalCars);

        List<RentalOffer> rentalOffers = makeOffer(availableCars, bookingPeriod);

        List<CarId> carIds = rentalOffers.stream().map(RentalOffer::getRentalCar).map(RentalCar::getCarId).collect(toList());
        CarsById carsById = repository.findCars(carIds);
        return carOfferModelMapper.toResponseModels(carsById, rentalOffers, bookingPeriod);
    }

    private List<RentalOffer> makeOffer(List<RentalCar> rentalCars, Period desiredPeriod) {
        return rentalCars
                .stream()
                .map(rc -> new RentalOffer(rc, desiredPeriod))
                .collect(toList());
    }


    private List<RentalCar> findMatchingCars(CarOfferRequestModel request) {
        CarOfferRepository.CarCriteria carCriteria = new CarOfferRepository.CarCriteria();

        VehicleType vehicleType = VehicleType.valueOf(request.getVehicleType());
        carCriteria.setVehicleType(vehicleType);

        return repository.findRentalCars(carCriteria);
    }

    private List<RentalCar> filterAvailableCars(Period desiredPeriod, List<RentalCar> rentalCars) {
        List<CarId> carIds = rentalCars
                .stream()
                .map(RentalCar::getCarId)
                .collect(toList());

        CarBookinsByCar carBookins = repository.findCarBookings(carIds, desiredPeriod);

        Predicate<RentalCar> notBookedPredicate = rc -> {
            CarId carId = rc.getCarId();
            return !carBookins.contains(carId);
        };

        return rentalCars
                .stream()
                .filter(notBookedPredicate).collect(toList());
    }
}
