package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;

import java.util.*;

public class RentalsByCar extends AbstractMap<CarId, CarRental> {

    private Map<CarId, CarRental> rentalsByCar = new HashMap<>();

    public RentalsByCar(List<CarRental> carRentals) {
        for (CarRental carRental : carRentals) {
            CarId carId = carRental.getCarId();
            rentalsByCar.put(carId, carRental);
        }
    }

    @Override
    public Set<Entry<CarId, CarRental>> entrySet() {
        return Collections.unmodifiableMap(rentalsByCar).entrySet();
    }

}
