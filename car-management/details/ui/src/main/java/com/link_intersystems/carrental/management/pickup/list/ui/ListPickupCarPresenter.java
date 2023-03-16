package com.link_intersystems.carrental.management.pickup.list.ui;

import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.pickup.list.PickupCarResponseModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListPickupCarPresenter {

    public List<ListPickupCarModel> toPickupCarModels(List<PickupCarResponseModel> responseModels) {
        List<ListPickupCarModel> listPickupCarModels = new ArrayList<>();

        for (PickupCarResponseModel responseModel : responseModels) {
            ListPickupCarModel listPickupCarModel = new ListPickupCarModel();
            int bookingNumber = responseModel.getBookingNumber();
            listPickupCarModel.setBookingNumber(Integer.toString(bookingNumber));

            LocalDateTime pickupDate = responseModel.getPickupDate();
            listPickupCarModel.setPickupDate(pickupDate.toString());

            listPickupCarModels.add(listPickupCarModel);
        }

        return listPickupCarModels;
    }

    public BookingNumberModel parseBookingNumber(String bookingNumber) {
        return new BookingNumberModel(Integer.parseInt(bookingNumber.trim()));
    }
}
