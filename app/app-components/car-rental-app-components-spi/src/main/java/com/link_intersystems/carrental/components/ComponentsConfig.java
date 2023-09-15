package com.link_intersystems.carrental.components;

import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.management.booking.ManagementCarBookingComponent;
import com.link_intersystems.carrental.management.rental.ManagementRentalComponent;

public interface ComponentsConfig {

    CarBookingComponent getCarBookingComponent();

    ManagementCarBookingComponent getManagementCarBookingComponent();

    ManagementRentalComponent getManagementRentalComponent();

}
