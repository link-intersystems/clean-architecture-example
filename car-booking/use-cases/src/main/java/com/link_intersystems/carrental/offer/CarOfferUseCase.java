package com.link_intersystems.carrental.offer;

import java.util.List;

public interface CarOfferUseCase {

    List<CarOfferResponseModel> makeOffers(CarOfferRequestModel request);
}
