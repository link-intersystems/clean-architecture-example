package com.link_intersystems.carrental.management.pickup.ui;

import javax.swing.*;

public class PickupCarModel {

    private String vin;
    private String bookingNumber;
    private String pickupDate;
    private BoundedRangeModel fuelLevel = new DefaultBoundedRangeModel(100, 0, 0, 100);
    private String odometer;

    private String driverFirstname;
    private String driverLastname;
    private String driverLicence;

    public String getDriverFirstname() {
        return driverFirstname;
    }

    public void setDriverFirstname(String driverFirstname) {
        this.driverFirstname = driverFirstname;
    }

    public String getDriverLastname() {
        return driverLastname;
    }

    public void setDriverLastname(String driverLastname) {
        this.driverLastname = driverLastname;
    }

    public String getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public BoundedRangeModel getFuelLevel() {
        return fuelLevel;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getOdometer() {
        return odometer;
    }
}
