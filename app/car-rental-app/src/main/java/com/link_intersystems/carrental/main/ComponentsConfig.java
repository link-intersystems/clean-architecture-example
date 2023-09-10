package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingComponent;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarComponent;
import com.link_intersystems.carrental.offer.CarOfferComponent;

public interface ComponentsConfig {

    CarOfferComponent getCarOfferComponent();

    CarBookingComponent getCarBookingComponent();

    ListCarBookingComponent getListCarBookingComponent();

    PickupCarComponent getPickupCarComponent();

    ListPickupCarComponent getListPickupCarComponent();

    ReturnCarComponent getReturnCarComponent();

    GetPickupCarComponent getGetPickupCarComponent();

    CreateCarBookingComponent getCreateCarBookingComponent();
}
