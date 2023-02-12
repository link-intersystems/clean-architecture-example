package com.link_intersystems.rental;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.car.CarId;
import com.link_intersystems.money.Amount;

import java.math.BigDecimal;
import java.util.List;

public class RentalRateFixture extends EntityFixture<RentalRate> {

    @Override
    protected void init(List<RentalRate> entities) {
        entities.add(new RentalRate(new CarId(1), new Amount(new BigDecimal("89.99"))));
        entities.add(new RentalRate(new CarId(2), new Amount(new BigDecimal("95.00"))));
        entities.add(new RentalRate(new CarId(3), new Amount(new BigDecimal("116.98"))));
        entities.add(new RentalRate(new CarId(4), new Amount(new BigDecimal("165.99"))));
        entities.add(new RentalRate(new CarId(5), new Amount(new BigDecimal("174.97"))));
        entities.add(new RentalRate(new CarId(6), new Amount(new BigDecimal("151.96"))));
    }
}
