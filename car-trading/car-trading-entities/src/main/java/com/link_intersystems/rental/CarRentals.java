package com.link_intersystems.rental;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class CarRentals extends AbstractList<CarRental> {

    private List<CarRental> carRentalList = new ArrayList<>();

    @Override
    public CarRental get(int index) {
        return carRentalList.get(index);
    }

    @Override
    public int size() {
        return carRentalList.size();
    }

    public boolean isAvailable(RentalPeriod rentalPeriod) {
        for (CarRental carRental : carRentalList) {
            RentalPeriod rentedPeriod = carRental.getRentalPeriod();
            if (rentedPeriod.overlaps(rentalPeriod)) {
                return false;
            }
        }
        return true;
    }
}
