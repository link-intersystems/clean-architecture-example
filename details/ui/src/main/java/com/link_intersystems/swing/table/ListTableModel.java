package com.link_intersystems.swing.table;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import static java.util.Objects.*;

public class ListTableModel<E> extends AbstractTableModel {

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
    private ListTableModelSupport<E> listTableModelSupport = new DefaultListTableModelSupport<>();

    public void setListModel(ListModel<E> listModel) {
        requireNonNull(listModel);

        this.listModel.removeListDataListener(listDataListener);
        this.listModel = listModel;
        this.listModel.addListDataListener(listDataListener);
    }

    public void setListTableModelSupport(ListTableModelSupport<E> listTableModelSupport) {
        this.listTableModelSupport = requireNonNull(listTableModelSupport);
    }

    @Override
    public int getRowCount() {
        return listModel.getSize();
    }

    @Override
    public int getColumnCount() {
        return listTableModelSupport.getColumnCount();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return listTableModelSupport.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return listTableModelSupport.getColumnName(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        E elementAt = listModel.getElementAt(rowIndex);
        return listTableModelSupport.getValue(elementAt, columnIndex);
    }
}
