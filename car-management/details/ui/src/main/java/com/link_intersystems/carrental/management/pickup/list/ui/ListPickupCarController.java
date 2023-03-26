package com.link_intersystems.carrental.management.pickup.list.ui;

import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.pickup.list.ListPickupCarResponseModel;
import com.link_intersystems.carrental.management.pickup.list.ListPickupCarUseCase;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.TaskProgress;
import com.link_intersystems.swing.selection.*;

import javax.swing.*;
import java.util.List;

public class ListPickupCarController extends AbstractTaskAction<List<ListPickupCarResponseModel>, Void> implements SelectionListener<ListPickupCarModel> {

    private DefaultListModel<ListPickupCarModel> pickupCarListModel = new DefaultListModel<>();
    private ListPickupCarPresenter listPickupCarPresenter = new ListPickupCarPresenter();
    private SelectionProviderSupport<BookingNumberModel> selectedBookingNumberSupport = new SelectionProviderSupport<>(this);
    private ListPickupCarUseCase listPickupCarUseCase;

    public ListPickupCarController(ListPickupCarUseCase listPickupCarUseCase) {
        this.listPickupCarUseCase = listPickupCarUseCase;

        putValue(Action.NAME, "List picked up cars");
    }

    public ListModel<ListPickupCarModel> getPickupCarListModel() {
        return pickupCarListModel;
    }

    @Override
    public void selectionChanged(SelectionChangeEvent<ListPickupCarModel> event) {
        Selection<ListPickupCarModel> newSelection = event.getNewSelection();
        if (newSelection.isEmpty()) {
            selectedBookingNumberSupport.setSelection(Selection.empty());
        } else {
            ListPickupCarModel listPickupCarModel = newSelection.getFirstElement();
            BookingNumberModel bookingNumber = listPickupCarPresenter.parseBookingNumber(listPickupCarModel.getBookingNumber());
            selectedBookingNumberSupport.setSelection(new DefaultSelection<>(bookingNumber));
        }
    }

    public void addBookingNumberSelectionListener(SelectionListener<BookingNumberModel> listener) {
        selectedBookingNumberSupport.addSelectionChangedListener(listener);
    }


    public void removeBookingNumberSelectionListener(SelectionListener<BookingNumberModel> listener) {
        selectedBookingNumberSupport.removeSelectionChangedListener(listener);
    }

    @Override
    protected List<ListPickupCarResponseModel> doInBackground(TaskProgress<Void> backgroundProgress) throws Exception {
        return listPickupCarUseCase.listPickedUpCars();
    }

    @Override
    protected void done(List<ListPickupCarResponseModel> result) {
        pickupCarListModel.clear();

        List<ListPickupCarModel> listPickupCarModels = listPickupCarPresenter.toPickupCarModels(result);
        pickupCarListModel.addAll(listPickupCarModels);
    }


}
