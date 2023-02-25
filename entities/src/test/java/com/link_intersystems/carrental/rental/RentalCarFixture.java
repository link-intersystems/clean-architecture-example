package com.link_intersystems.carrental.rental;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.money.Amount;

import java.util.List;

public class RentalCarFixture extends EntityFixture<RentalCar> {


    private CarFixture carFixture;

    public RentalCarFixture(CarFixture carFixture) {
        this.carFixture = carFixture;
    }

    @Override
    protected void init(List<RentalCar> entities) {
        RentalCar smartForTwoRentalCar = new RentalCar(carFixture.getSmartFortwo().getId(), new RentalRate(new Amount("89.99")));
        entities.add(smartForTwoRentalCar);

        RentalCar fiat500RentalCar = new RentalCar(carFixture.getFiat500().getId(), new RentalRate(new Amount("95.00")));
        entities.add(fiat500RentalCar);

        RentalCar bmw500 = new RentalCar(carFixture.getBmw530().getId(), new RentalRate(new Amount("116.98")));
        entities.add(bmw500);

        RentalCar volvoXC90 = new RentalCar(carFixture.getVolvoXC90().getId(), new RentalRate(new Amount("165.99")));
        entities.add(volvoXC90);

        RentalCar mercedesBenzE200 = new RentalCar(carFixture.getMercedesBenzE200().getId(), new RentalRate(new Amount("120.96")));
        entities.add(mercedesBenzE200);
    }

}
