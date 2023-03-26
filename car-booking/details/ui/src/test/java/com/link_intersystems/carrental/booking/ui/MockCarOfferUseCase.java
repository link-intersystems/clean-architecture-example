package com.link_intersystems.carrental.booking.ui;

import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.CarOfferRequestModel;
import com.link_intersystems.carrental.offer.CarOfferUseCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockCarOfferUseCase implements CarOfferUseCase {
    @Override
    public List<CarOfferResponseModel> makeOffers(CarOfferRequestModel request) {
        List<CarOfferResponseModel> response = new ArrayList<>();
        CarOfferOutputModerBuilder carOfferOutputModerBuilder = new CarOfferOutputModerBuilder();
        response.add( //
                carOfferOutputModerBuilder //
                        .setId("1") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("40.00")) //
                        .setTotalRentalRate(new BigDecimal("120.00")) //
                        .build());

        response.add( //
                carOfferOutputModerBuilder //
                        .setId("2") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("30.00")) //
                        .setTotalRentalRate(new BigDecimal("90.00")) //
                        .build());

        return response;
    }
}
