package com.link_intersystems.car.offers;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class CarOffersModel extends AbstractList<CarOfferModel> {

    private List<CarOfferModel> carOfferModels = new ArrayList<>();

    void addCarOffer(CarOfferModel carOfferModel) {
        carOfferModels.add(carOfferModel);
    }

    @Override
    public CarOfferModel get(int index) {
        return carOfferModels.get(index);
    }

    @Override
    public int size() {
        return carOfferModels.size();
    }
}
