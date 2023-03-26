package com.link_intersystems.carrental.management.pickup.ui;

import com.link_intersystems.carrental.management.booking.list.ui.CustomerModel;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;
import com.link_intersystems.carrental.management.pickup.DriverRequestModel;
import com.link_intersystems.carrental.management.pickup.PickupCarRequestModel;
import com.link_intersystems.carrental.management.rental.FuelLevel;
import com.link_intersystems.carrental.time.ClockProvider;

import java.time.LocalDateTime;

public class PickupCarModelPresenter {

    public PickupCarModel toPickupCarModel(ListCarBookingModel listCarBookingModel) {
        PickupCarModel pickupCarModel = new PickupCarModel();

        pickupCarModel.setVin(listCarBookingModel.getVin());
        pickupCarModel.setBookingNumber(listCarBookingModel.getBookingNumber());
        pickupCarModel.setPickupDate(ClockProvider.now().toString());

        CustomerModel customerModel = listCarBookingModel.getCustomerModel();
        pickupCarModel.setDriverFirstname(customerModel.getFirstname());
        pickupCarModel.setDriverLastname(customerModel.getLastname());

        return pickupCarModel;
    }


    public PickupCarRequestModel toRequestModel(PickupCarModel pickupCarModel) {
        PickupCarRequestModel requestModel = new PickupCarRequestModel();

        String pickupDate = pickupCarModel.getPickupDate();
        requestModel.setPickupDateTime(LocalDateTime.parse(pickupDate));

        int value = pickupCarModel.getFuelLevel().getValue();
        requestModel.setFuelLevel(FuelLevel.ofPercentage(value));

        String bookingNumber = pickupCarModel.getBookingNumber();
        requestModel.setBookingNumber(Integer.parseInt(bookingNumber));

        String odometer = pickupCarModel.getOdometer();
        requestModel.setOdometer(Integer.valueOf(odometer));

        DriverRequestModel driverModel = new DriverRequestModel();
        driverModel.setFirstname(pickupCarModel.getDriverFirstname());
        driverModel.setLastname(pickupCarModel.getDriverLastname());
        driverModel.setDrivingLicenceNumber(pickupCarModel.getDriverLicence());
        requestModel.setDriver(driverModel);

        return requestModel;
    }
}
