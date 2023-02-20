package com.link_intersystems.car.offers;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class CarOfferResponseBuilder {

    private CarOffersResponseModel responseModel = mock(CarOffersResponseModel.class);
    private CarOffersOutputModel carOffersOutputModel = new CarOffersOutputModel();

    public CarOfferResponseBuilder() {
        build();
    }

    public CarOffersResponseModel build() {
        CarOffersResponseModel returnModel = responseModel;
        responseModel = mock(CarOffersResponseModel.class);
        carOffersOutputModel = new CarOffersOutputModel();
        when(responseModel.getCarOffers()).thenReturn(carOffersOutputModel);
        return returnModel;
    }

    public void add(CarOfferOutputModel carOfferOutputModel) {
        carOffersOutputModel.addCarOffer(carOfferOutputModel);
    }
}
