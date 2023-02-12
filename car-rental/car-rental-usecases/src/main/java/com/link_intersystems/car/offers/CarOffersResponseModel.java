package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.Specs;
import com.link_intersystems.car.offer.RentalOffer;
import com.link_intersystems.car.rental.RentalCar;
import com.link_intersystems.car.rental.RentalRate;
import com.link_intersystems.money.Amount;

import java.util.List;

public class CarOffersResponseModel {

    private CarOffersModel carOffersModel = new CarOffersModel();

    CarOffersResponseModel(List<RentalOffer> rentalOffers) {
        for (RentalOffer rentalOffer : rentalOffers) {
            CarOfferModel carOfferModel = map(rentalOffer);
            carOffersModel.addCarOffer(carOfferModel);
        }
    }

    private CarOfferModel map(RentalOffer rentalOffer) {
        CarOfferModel carOfferModel = new CarOfferModel();

        RentalCar rentalCar = rentalOffer.getRentalCar();
        Car car = rentalCar.getCar();
        carOfferModel.setId(car.getId().getValue());
        carOfferModel.setName(car.getName());
        carOfferModel.setVerhicleType(car.getVehicleType().name());

        Amount totalRentalAmount = rentalOffer.getTotalRentalAmount();
        carOfferModel.setTotalRentalRate(totalRentalAmount.getValue());

        RentalRate rentalRate = rentalCar.getRentalRate();
        carOfferModel.setPerDayRentalRate(rentalRate.getAmount().getValue());

        CarSpecModel carSpecModel = mapCarSpec(car);
        carOfferModel.setCarSpecModel(carSpecModel);

        return carOfferModel;
    }

    private CarSpecModel mapCarSpec(Car car) {
        CarSpecModel carSpecModel = new CarSpecModel();
        Specs specs = car.getSpecs();
        carSpecModel.setSeats(specs.getSeats().getValue());
        carSpecModel.setDoors(specs.getDoors().getValue());
        carSpecModel.setConsumption(specs.getConsumption().getEnergyUnitsPerKm());
        carSpecModel.setEnergyType(specs.getConsumption().getEnergyType().name());
        return carSpecModel;
    }

    public CarOffersModel getCarOffers() {
        return carOffersModel;
    }

}
