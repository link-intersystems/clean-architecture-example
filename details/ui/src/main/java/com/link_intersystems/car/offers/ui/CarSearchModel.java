package com.link_intersystems.car.offers.ui;

public class CarSearchModel {
    private String vehicleType;
    private String pickupDate;
    private String returnDate;

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}
