package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.management.booking.list.CarBookingResponseModel;
import com.link_intersystems.carrental.management.booking.list.ListBookingsUseCase;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.*;
import java.util.List;

public class ListCarBookingController extends AbstractTaskAction<List<CarBookingResponseModel>, Void> {

    private DefaultListModel<ListCarBookingModel> carBookingListModel = new DefaultListModel<>();
    private CarBookingPresenter carBookingPresenter = new CarBookingPresenter();

    private ListBookingsUseCase listBookingsUseCase;

    public ListCarBookingController(ListBookingsUseCase listBookingsUseCase) {
        this.listBookingsUseCase = listBookingsUseCase;

        putValue(Action.NAME, "List Car Bookings");
    }

    public ListModel<ListCarBookingModel> getCarBookingListModel() {
        return carBookingListModel;
    }

    @Override
    protected List<CarBookingResponseModel> doInBackground(TaskProgress<Void> backgroundProgress) throws Exception {
        return listBookingsUseCase.listBookings();
    }

    protected void done(List<CarBookingResponseModel> carBookings) {
        carBookingListModel.clear();

        List<ListCarBookingModel> listCarBookingModels = carBookingPresenter.toCarBookingModels(carBookings);
        carBookingListModel.addAll(listCarBookingModels);
    }


}
