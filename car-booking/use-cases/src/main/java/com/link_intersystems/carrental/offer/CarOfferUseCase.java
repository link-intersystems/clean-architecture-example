package com.link_intersystems.carrental.offer;

import java.util.List;

public interface CarOfferUseCase {

    List<CarOfferOutputModel> makeOffers(CarOfferRequestModel request);
}
