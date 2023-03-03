package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.Specs;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.rental.RentalRate;
import com.link_intersystems.money.Amount;

import java.util.List;

class IntputOutputMapper {

    public CarOfferResponseModel toOutputModel(CarsById carsById, List<RentalOffer> rentalOffers) {
        CarOfferResponseModel carOfferResponseModel = new CarOfferResponseModel();

        CarOffersOutputModel carOfferOutputModels = new CarOffersOutputModel();
        for (RentalOffer rentalOffer : rentalOffers) {
            CarOfferOutputModel carOfferOutputModel = map(carsById, rentalOffer);
            carOfferOutputModels.addCarOffer(carOfferOutputModel);
        }

        carOfferResponseModel.setCarOffersOutputModel(carOfferOutputModels);
        return carOfferResponseModel;
    }

    private CarOfferOutputModel map(CarsById carsById, RentalOffer rentalOffer) {
        CarOfferOutputModel carOfferOutputModel = new CarOfferOutputModel();

        RentalCar rentalCar = rentalOffer.getRentalCar();
        CarId carId = rentalCar.getCarId();
        Car car = carsById.getCar(carId);
        carOfferOutputModel.setId(car.getId().getValue());
        carOfferOutputModel.setName(car.getName());
        carOfferOutputModel.setVehicleType(car.getVehicleType().name());

        Amount totalRentalAmount = rentalOffer.getTotalRentalAmount();
        carOfferOutputModel.setTotalRentalRate(totalRentalAmount.getValue());

        RentalRate rentalRate = rentalCar.getRentalRate();
        carOfferOutputModel.setPerDayRentalRate(rentalRate.getAmount().getValue());

        CarSpecModel carSpecModel = mapCarSpec(car);
        carOfferOutputModel.setCarSpecModel(carSpecModel);

        return carOfferOutputModel;
    }

    private CarSpecModel mapCarSpec(Car car) {
        CarSpecModel carSpecModel = new CarSpecModel();
        Specs specs = car.getSpecs();
        if (specs != null) {
            carSpecModel.setSeats(specs.getSeats().getValue());
            carSpecModel.setDoors(specs.getDoors().getValue());
            carSpecModel.setConsumption(specs.getConsumption().getUnitsPerKm());
            carSpecModel.setEnergyType(specs.getConsumption().getFuelType().name());
        }
        return carSpecModel;
    }
}