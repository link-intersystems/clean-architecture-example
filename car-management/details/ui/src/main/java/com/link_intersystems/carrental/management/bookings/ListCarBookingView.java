package com.link_intersystems.carrental.management.bookings;

import com.link_intersystems.carmanagement.booking.ListBookingsUseCase;

import javax.swing.*;
import java.awt.*;

public class ListCarBookingView {

    private JPanel panel = new JPanel(new BorderLayout());

    private JTable bookingsTable = new JTable();
    private JScrollPane bookingsTableScrollPane = new JScrollPane((bookingsTable));
    private ListBookingsUseCase listBookingsUseCase;

    public ListCarBookingView(ListBookingsUseCase listBookingsUseCase) {
        this.listBookingsUseCase = listBookingsUseCase;

        panel.add(bookingsTableScrollPane);
    }

    public Component getViewComponent() {
        return panel;
    }
}
