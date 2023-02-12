package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;

import java.util.*;

public class RentalsByCar extends AbstractMap<CarId, CarRentals> {

    private Map<CarId, CarRentals> rentalsByCar = new HashMap<>();

    public RentalsByCar(Map<CarId, CarRentals> rentalsByCar) {
        this.rentalsByCar.putAll(rentalsByCar);
    }

    @Override
    public Set<Entry<CarId, CarRentals>> entrySet() {
        return Collections.unmodifiableMap(rentalsByCar).entrySet();
    }

}
