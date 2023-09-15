package com.link_intersystems.carrental.booking;

import java.util.List;

public interface CarOfferUseCase {

    List<CarOfferResponseModel> makeOffers(CarOfferRequestModel request);
}
