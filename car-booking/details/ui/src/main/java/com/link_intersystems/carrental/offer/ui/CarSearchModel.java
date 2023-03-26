package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.carrental.swing.binding.BindingValue;
import com.link_intersystems.carrental.swing.binding.DefaultBindingValue;

public class CarSearchModel {
    private BindingValue<String> vehicleType = new DefaultBindingValue<>();
    private BindingValue<String> pickupDate = new DefaultBindingValue<>();
    private BindingValue<String> returnDate = new DefaultBindingValue<>();

    public BindingValue<String> getPickupDate() {
        return pickupDate;
    }

    public BindingValue<String> getReturnDate() {
        return returnDate;
    }

    public BindingValue<String> getVehicleType() {
        return vehicleType;
    }
}
