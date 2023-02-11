package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.VehicleType;

import java.time.Clock;
import java.util.List;

public class CarOffersInteractor implements CarOffersUseCase {

    private final Clock clock;

    public static interface Deps {
        public CarOffersRepository getRepository();

        public Clock getClock();
    }

    private final CarOffersRepository repository;

    public CarOffersInteractor(Deps deps) {
        repository = deps.getRepository();
        clock = deps.getClock();
    }

    @Override
    public CarOffersResponseModel findOffers(CarOffersRequestModel request) {

        CarCriteria carCriteria = new CarCriteria();

        VehicleType vehicleType = repository.findVehicleTypeByName(request.getVehicleType());
        carCriteria.setVehicleType(vehicleType);

        List<Car> cars = repository.findFilms(carCriteria);

        CarOffersResponseModel responseModel = new CarOffersResponseModel();

        CarOffers carOffers = mapCars(cars);
        responseModel.setFilmListing(carOffers);

        return responseModel;
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
        carOffer.setId(car.getId());
        carOffer.setName(car.getName());
        carOffer.setVerhicleType(car.getCategory().name());
        return carOffer;
    }

}
