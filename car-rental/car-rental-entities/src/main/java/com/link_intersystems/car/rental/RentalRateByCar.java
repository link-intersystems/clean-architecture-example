package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;

import java.util.*;

public class RentalRateByCar extends AbstractMap<CarId, RentalRate> {

    private Map<CarId, RentalRate> rentalRateByCar = new LinkedHashMap<>();

    public RentalRateByCar(List<RentalRate> rentalRateList) {

        for (RentalRate rateRate : rentalRateList) {
            RentalRate previousRentalRate = this.rentalRateByCar.put(rateRate.getCarId(), rateRate);
            if (previousRentalRate != null) {
                throw new IllegalArgumentException("Ambiguous  " + rateRate + " -> " + previousRentalRate);
            }
        }
    }

    @Override
    public Set<Entry<CarId, RentalRate>> entrySet() {
        return Collections.unmodifiableMap(rentalRateByCar).entrySet();
    }

}
