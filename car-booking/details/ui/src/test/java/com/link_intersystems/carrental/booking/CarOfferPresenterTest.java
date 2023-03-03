package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferModel;
import com.link_intersystems.carrental.offer.CarOfferOutputModel;
import com.link_intersystems.carrental.offer.CarOfferPresenter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarOfferPresenterTest {


    @Test
    void carOffers() {
        CarOfferPresenter carOfferPresenter = new CarOfferPresenter();

        CarOfferOutputModel carOfferOutputModel = new CarOfferOutputModerBuilder()
                .setId("123") //
                .setTotalRentalRate("122.97") //
                .setPerDayRentalRate("40.99") //
                .setVehicleType("MICRO") //
                .build();

        CarOfferModel carOfferModel = carOfferPresenter.toCarOfferModel(carOfferOutputModel);

        assertEquals("123", carOfferModel.getId());
        assertEquals("122.97", carOfferModel.getTotalRentalRate());
        assertEquals("40.99", carOfferModel.getPerDayRentalRate());
        assertEquals("MICRO", carOfferModel.getVehicleType());
    }
}