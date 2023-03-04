package com.link_intersystems.carrental.swing.table;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import static java.util.Objects.*;

public abstract class AbstractListTableModel<E> extends AbstractTableModel {

    private ListDataListener listDataListener = new ListDataListener() {
        @Override
        public void intervalAdded(ListDataEvent e) {
            fireTableRowsInserted(e.getIndex0(), e.getIndex1());
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            fireTableDataChanged();
        }
    };

    private ListModel<E> listModel = new DefaultListModel<>();

    public void setListModel(ListModel<E> listModel) {
        requireNonNull(listModel);

        this.listModel.removeListDataListener(listDataListener);
        this.listModel = listModel;
        this.listModel.addListDataListener(listDataListener);

        fireTableStructureChanged();
    }

    public ListModel<E> getListModel() {
        return listModel;
    }

    @Override
    public int getRowCount() {
        return listModel.getSize();
    }

    @Override
    public Object getValueAt(int row, int column) {
        E elementAt = getElementAt(row);
        return getValue(elementAt, column);
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int column) {
        E elementAt = getElementAt(rowIndex);
        setValue(elementAt, column, value);
    }

    protected E getElementAt(int row) {
        return getListModel().getElementAt(row);
    }

    protected abstract Object getValue(E element, int column);

    protected void setValue(E element, int column, Object value) {
    }

}
