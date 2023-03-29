package com.link_intersystems.carrental.offer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockCarOfferUseCase implements CarOfferUseCase {
    @Override
    public List<CarOfferResponseModel> makeOffers(CarOfferRequestModel request) {
        List<CarOfferResponseModel> response = new ArrayList<>();

        CarOfferResponseModelMock responseModel1 = new CarOfferResponseModelMock();
        responseModel1.setId("1");
        responseModel1.setVehicleType("MICRO");
        responseModel1.setPerDayRentalRate(new BigDecimal("40.00"));
        responseModel1.setTotalRentalRate(new BigDecimal("120.00"));
        response.add(responseModel1);

        CarOfferResponseModelMock responseModel2 = new CarOfferResponseModelMock();
        responseModel2.setId("2");
        responseModel2.setVehicleType("MICRO");
        responseModel2.setPerDayRentalRate(new BigDecimal("30.00"));
        responseModel2.setTotalRentalRate(new BigDecimal("90.00"));
        response.add(responseModel2);

        return response;
    }
}
