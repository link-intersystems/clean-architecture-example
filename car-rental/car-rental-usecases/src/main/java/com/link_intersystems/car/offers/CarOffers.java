package com.link_intersystems.car.offers;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class CarOffers extends AbstractList<CarOffer> {

    private List<CarOffer> carOffers = new ArrayList<>();

    void addCarOffer(CarOffer carOffer) {
        carOffers.add(carOffer);
    }

    @Override
    public CarOffer get(int index) {
        return carOffers.get(index);
    }

    @Override
    public int size() {
        return carOffers.size();
    }
}
