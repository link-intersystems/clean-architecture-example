package com.link_intersystems.carrental.management.rental.returnCar.ui;

import javax.swing.*;

public class ReturnCarModel {
    private String bookingNumber;
    private String returnDate;
    private BoundedRangeModel fuelModel = new DefaultBoundedRangeModel();
    private String odometer;

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public BoundedRangeModel getFuelModel() {
        return fuelModel;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }
}
