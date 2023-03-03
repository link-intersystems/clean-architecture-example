package com.link_intersystems.carrental.offer;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class CarOffersOutputModel extends AbstractList<CarOfferOutputModel> {

    private List<CarOfferOutputModel> carOfferOutputModels = new ArrayList<>();

    void addCarOffer(CarOfferOutputModel carOfferOutputModel) {
        carOfferOutputModels.add(carOfferOutputModel);
    }

    @Override
    public CarOfferOutputModel get(int index) {
        return carOfferOutputModels.get(index);
    }

    @Override
    public int size() {
        return carOfferOutputModels.size();
    }
}