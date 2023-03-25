package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.CarsById;
import com.link_intersystems.carrental.Specs;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.rental.RentalRate;
import com.link_intersystems.carrental.time.Period;

import java.util.ArrayList;
import java.util.List;

class IntputOutputMapper {

    public List<CarOfferOutputModel> toOutputModel(CarsById carsById, List<RentalOffer> rentalOffers, Period bookingPeriod) {
        List<CarOfferOutputModel> carOfferOutputModels = new ArrayList<>();

        for (RentalOffer rentalOffer : rentalOffers) {
            CarOfferOutputModel carOfferOutputModel = map(carsById, rentalOffer, bookingPeriod);
            carOfferOutputModels.add(carOfferOutputModel);
        }

        return carOfferOutputModels;
    }

    private CarOfferOutputModel map(CarsById carsById, RentalOffer rentalOffer, Period bookingPeriod) {
        CarOfferOutputModel carOfferOutputModel = new CarOfferOutputModel();

        carOfferOutputModel.setPickupDateTime(bookingPeriod.getBegin());
        carOfferOutputModel.setReturnDateTime(bookingPeriod.getEnd());

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
