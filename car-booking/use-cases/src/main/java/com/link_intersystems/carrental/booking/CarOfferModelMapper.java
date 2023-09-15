package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.CarsById;
import com.link_intersystems.carrental.Specs;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.offer.RentalOffer;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;

import java.util.ArrayList;
import java.util.List;

class CarOfferModelMapper {

    public List<CarOfferResponseModel> toResponseModels(CarsById carsById, List<RentalOffer> rentalOffers, Period bookingPeriod) {
        List<CarOfferResponseModel> carOfferResponseModels = new ArrayList<>();

        for (RentalOffer rentalOffer : rentalOffers) {
            CarOfferResponseModel carOfferResponseModel = toResponseModel(carsById, rentalOffer, bookingPeriod);
            carOfferResponseModels.add(carOfferResponseModel);
        }

        return carOfferResponseModels;
    }

    private CarOfferResponseModel toResponseModel(CarsById carsById, RentalOffer rentalOffer, Period bookingPeriod) {
        CarOfferResponseModel carOfferResponseModel = new CarOfferResponseModel();

        carOfferResponseModel.setPickupDateTime(bookingPeriod.getBegin());
        carOfferResponseModel.setReturnDateTime(bookingPeriod.getEnd());

        RentalCar rentalCar = rentalOffer.getRentalCar();
        CarId carId = rentalCar.getCarId();
        Car car = carsById.getCar(carId);
        carOfferResponseModel.setId(car.getId().getValue());
        carOfferResponseModel.setName(car.getName());
        carOfferResponseModel.setVehicleType(car.getVehicleType().name());

        Amount totalRentalAmount = rentalOffer.getTotalRentalAmount();
        carOfferResponseModel.setTotalRentalRate(totalRentalAmount.getValue());

        carOfferResponseModel.setPerDayRentalRate(rentalCar.getRentalRate().getValue());

        CarSpecModel carSpecModel = toResponseModel(car);
        carOfferResponseModel.setCarSpecModel(carSpecModel);

        return carOfferResponseModel;
    }

    private CarSpecModel toResponseModel(Car car) {
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
