package com.link_intersystems.carrental.management.booking.list.ui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ListCarBookingViewControl {
    private ListCarBookingView listCarBookingView;

    public ListCarBookingViewControl(ListCarBookingView listCarBookingView) {
        this.listCarBookingView = listCarBookingView;
    }

    public void selectRow(int rowIndex) {
        JTable bookingsTable = listCarBookingView.getBookingsTable();
        bookingsTable.setRowSelectionInterval(rowIndex, rowIndex);
    }

    public String getValue(int row, int column) {
        JTable bookingsTable = listCarBookingView.getBookingsTable();
        TableCellRenderer cellRenderer = bookingsTable.getCellRenderer(row, column);
        Object valueAt = bookingsTable.getValueAt(row, column);
        JLabel label = (JLabel) cellRenderer.getTableCellRendererComponent(bookingsTable, valueAt, false, false, row, column);
        return label.getText();
    }
}
