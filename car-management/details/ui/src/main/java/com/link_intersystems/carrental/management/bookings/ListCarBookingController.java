package com.link_intersystems.carrental.management.bookings;

import com.link_intersystems.carrental.management.booking.list.CarBookingResponseModel;
import com.link_intersystems.carrental.management.booking.list.ListBookingsRequestModel;
import com.link_intersystems.carrental.management.booking.list.ListBookingsResponseModel;
import com.link_intersystems.carrental.management.booking.list.ListBookingsUseCase;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.BackgroundProgress;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListCarBookingController extends AbstractWorkerAction<ListBookingsResponseModel, Void> {

    private DefaultListModel<CarBookingModel> carBookingListModel = new DefaultListModel<>();
    private CarBookingPresenter carBookingPresenter = new CarBookingPresenter();

    private ListBookingsUseCase listBookingsUseCase;

    public ListCarBookingController(ListBookingsUseCase listBookingsUseCase) {
        this.listBookingsUseCase = listBookingsUseCase;

        putValue(Action.NAME, "List Car Bookings");
    }

    public ListModel<CarBookingModel> getCarBookingListModel() {
        return carBookingListModel;
    }

    @Override
    protected ListBookingsResponseModel doInBackground(BackgroundProgress<Void> backgroundProgress) throws Exception {
        ListBookingsRequestModel requestModel = new ListBookingsRequestModel();
        return listBookingsUseCase.listBookings(requestModel);
    }

    @Override
    protected void done(ListBookingsResponseModel result) {
        carBookingListModel.clear();

        List<CarBookingResponseModel> carBookings = result.getCarBookings();
        List<CarBookingModel> carBookingModels = carBookingPresenter.toCarBookingModels(carBookings);
        carBookingListModel.addAll(carBookingModels);
    }

    @Override
    protected void failed(ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }

        throw new RuntimeException(e);
    }
}
