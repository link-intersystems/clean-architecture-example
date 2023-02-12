package com.link_intersystems.rental;

import com.link_intersystems.car.CarId;

import java.util.*;

public class RentalRatesByCar extends AbstractMap<CarId, RentalRates> {

    private Map<CarId, RentalRates> rentalRates = new HashMap<>();

    public RentalRatesByCar(Map<CarId, RentalRates> rentalRates) {
        this.rentalRates.putAll(rentalRates);
    }

    @Override
    public Set<Entry<CarId, RentalRates>> entrySet() {
        return Collections.unmodifiableMap(rentalRates).entrySet();
    }

}
