package com.link_intersystems.car.rental;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.car.CarFixture;
import com.link_intersystems.money.Amount;

import java.math.BigDecimal;
import java.util.List;

public class RentalRateFixture extends EntityFixture<RentalRate> {

    private CarFixture carFixture;

    public RentalRateFixture(CarFixture carFixture) {
        this.carFixture = carFixture;
    }

    @Override
    protected void init(List<RentalRate> entities) {
        entities.add(new RentalRate(carFixture.getSmartFortwo().getId(), new Amount(new BigDecimal("89.99"))));
        entities.add(new RentalRate(carFixture.getFiat500().getId(), new Amount(new BigDecimal("95.00"))));
        entities.add(new RentalRate(carFixture.getBmw530().getId(), new Amount(new BigDecimal("116.98"))));
        entities.add(new RentalRate(carFixture.getVolvoXC90().getId(), new Amount(new BigDecimal("165.99"))));
        entities.add(new RentalRate(carFixture.getMercedesBenzE200().getId(), new Amount(new BigDecimal("120.96"))));
    }
}
