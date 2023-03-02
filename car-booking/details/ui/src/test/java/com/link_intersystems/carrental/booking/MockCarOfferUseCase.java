package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.*;

import java.math.BigDecimal;

public class MockCarOfferUseCase implements CarOfferUseCase {
    @Override
    public CarOfferResponseModel makeOffers(CarOfferRequestModel request) {
        CarOfferResponseBuilder responseBuilder = new CarOfferResponseBuilder();
        CarOfferOutputModerBuilder carOfferOutputModerBuilder = new CarOfferOutputModerBuilder();
        responseBuilder.add( //
                carOfferOutputModerBuilder //
                        .setId("1") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("40.00")) //
                        .setTotalRentalRate(new BigDecimal("120.00")) //
                        .build());

        responseBuilder.add( //
                carOfferOutputModerBuilder //
                        .setId("2") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("30.00")) //
                        .setTotalRentalRate(new BigDecimal("90.00")) //
                        .build());
        CarOfferResponseModel carOfferResponseModel = responseBuilder.build();

        return carOfferResponseModel;
    }
}
