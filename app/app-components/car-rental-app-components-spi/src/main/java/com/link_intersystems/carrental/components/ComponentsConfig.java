package com.link_intersystems.carrental.components;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.management.booking.ManagementCarBookingComponent;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarComponent;

public interface ComponentsConfig {

    CarBookingComponent getCarBookingComponent();

    PickupCarComponent getPickupCarComponent(DomainEventPublisher eventPublisher);

    ListPickupCarComponent getListPickupCarComponent();

    ReturnCarComponent getReturnCarComponent();

    GetPickupCarComponent getGetPickupCarComponent();

    ManagementCarBookingComponent getCreateCarBookingComponent();
}
