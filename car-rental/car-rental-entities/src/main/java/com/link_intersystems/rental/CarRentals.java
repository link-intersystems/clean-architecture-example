package com.link_intersystems.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarRentals extends AbstractList<CarRental> {

    private List<CarRental> carRentalList = new ArrayList<>();

    public CarRentals() {
    }

    public CarRentals(List<CarRental> carRentalList) {
        this.carRentalList.addAll(carRentalList);
    }

    public RentalsByCar groupByCar() {
        Map<CarId, List<CarRental>> rentalsByCar = stream().collect(Collectors.groupingBy(CarRental::getCarId));
        Map<CarId, CarRentals> collect = rentalsByCar.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> new CarRentals(e.getValue())));
        return new RentalsByCar(collect);
    }

    @Override
    public CarRental get(int index) {
        return carRentalList.get(index);
    }

    @Override
    public int size() {
        return carRentalList.size();
    }

    public boolean isAvailable(Period period) {
        for (CarRental carRental : this) {
            Period rentedPeriod = carRental.getRentalPeriod();
            if (rentedPeriod.overlaps(period)) {
                return false;
            }
        }
        return true;
    }
}
