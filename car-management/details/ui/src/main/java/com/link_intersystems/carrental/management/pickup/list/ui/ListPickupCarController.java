package com.link_intersystems.carrental.management.pickup.list.ui;

import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarResponseModel;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.BackgroundProgress;
import com.link_intersystems.swing.selection.*;

import javax.swing.*;
import java.util.List;

public class ListPickupCarController extends AbstractWorkerAction<ListPickupCarResponseModel, Void> implements SelectionListener<ListPickupCarModel> {

    private DefaultListModel<ListPickupCarModel> pickupCarListModel = new DefaultListModel<>();
    private ListPickupCarPresenter listPickupCarPresenter = new ListPickupCarPresenter();
    private SelectionProviderSupport<BookingNumberModel> selectedBookingNumberSupport = new SelectionProviderSupport<>(this);
    private ListPickupCarUseCase listPickupCarUseCase;
    private MessageDialog messageDialog;

    public ListPickupCarController(ListPickupCarUseCase listPickupCarUseCase, MessageDialog messageDialog) {
        this.listPickupCarUseCase = listPickupCarUseCase;
        this.messageDialog = messageDialog;

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
    protected ListPickupCarResponseModel doInBackground(BackgroundProgress<Void> backgroundProgress) throws Exception {
        return listPickupCarUseCase.listPickedUpCars();
    }

    @Override
    protected void done(ListPickupCarResponseModel result) {
        pickupCarListModel.clear();

        List<ListPickupCarModel> listPickupCarModels = listPickupCarPresenter.toPickupCarModels(result.getPickupCars());
        pickupCarListModel.addAll(listPickupCarModels);
    }


}
