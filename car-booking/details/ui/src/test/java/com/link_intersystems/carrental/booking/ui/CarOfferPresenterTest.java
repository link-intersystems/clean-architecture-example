package com.link_intersystems.carrental.booking.ui;

import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.ui.CarOfferModel;
import com.link_intersystems.carrental.offer.ui.CarOfferPresenter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarOfferPresenterTest {

    @Test
    void carOffers() {
        CarOfferPresenter carOfferPresenter = new CarOfferPresenter();

        CarOfferResponseModel carOfferResponseModel = new CarOfferOutputModerBuilder()
                .setId("123") //
                .setTotalRentalRate("122.97") //
                .setPerDayRentalRate("40.99") //
                .setVehicleType("MICRO") //
                .build();

        CarOfferModel carOfferModel = carOfferPresenter.toCarOfferModel(carOfferResponseModel);

        assertEquals("123", carOfferModel.getId());
        assertEquals("122.97", carOfferModel.getTotalRentalRate());
        assertEquals("40.99", carOfferModel.getPerDayRentalRate());
        assertEquals("MICRO", carOfferModel.getVehicleType());
    }
}