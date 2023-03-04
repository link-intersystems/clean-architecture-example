package com.link_intersystems.carmanagement.booking.list;

import java.util.List;

public class ListBookingsResponseModel {
    private List<CarBookingResponseModel> carBookings;

    public List<CarBookingResponseModel> getCarBookings() {
        return carBookings;
    }

    public void setCarBookings(List<CarBookingResponseModel> carBookings) {
        this.carBookings = carBookings;
    }
}
