package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.car.booking.CarBookings;

import java.util.*;

public class RentalsByCar extends AbstractMap<CarId, CarBookings> {

    private Map<CarId, CarBookings> rentalsByCar = new HashMap<>();

    public RentalsByCar(Map<CarId, CarBookings> rentalsByCar) {
        this.rentalsByCar.putAll(rentalsByCar);
    }

    @Override
    public Set<Entry<CarId, CarBookings>> entrySet() {
        return Collections.unmodifiableMap(rentalsByCar).entrySet();
    }

}
