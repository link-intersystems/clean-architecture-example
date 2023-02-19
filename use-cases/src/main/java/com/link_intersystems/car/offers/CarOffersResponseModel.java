package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.Specs;
import com.link_intersystems.car.offer.RentalOffer;
import com.link_intersystems.car.rental.RentalCar;
import com.link_intersystems.car.rental.RentalRate;
import com.link_intersystems.money.Amount;

import java.util.List;

public class CarOffersResponseModel {

    private CarOffersOutputModel carOffersOutputModel = new CarOffersOutputModel();

    CarOffersResponseModel(List<RentalOffer> rentalOffers) {
        for (RentalOffer rentalOffer : rentalOffers) {
            CarOfferOutputModel carOfferOutputModel = map(rentalOffer);
            carOffersOutputModel.addCarOffer(carOfferOutputModel);
        }
    }

    private CarOfferOutputModel map(RentalOffer rentalOffer) {
        CarOfferOutputModel carOfferOutputModel = new CarOfferOutputModel();

        RentalCar rentalCar = rentalOffer.getRentalCar();
        Car car = rentalCar.getCar();
        carOfferOutputModel.setId(car.getId().getValue());
        carOfferOutputModel.setName(car.getName());
        carOfferOutputModel.setVerhicleType(car.getVehicleType().name());

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
        carSpecModel.setSeats(specs.getSeats().getValue());
        carSpecModel.setDoors(specs.getDoors().getValue());
        carSpecModel.setConsumption(specs.getConsumption().getUnitsPerKm());
        carSpecModel.setEnergyType(specs.getConsumption().getFuelType().name());
        return carSpecModel;
    }

    public CarOffersOutputModel getCarOffers() {
        return carOffersOutputModel;
    }

}
