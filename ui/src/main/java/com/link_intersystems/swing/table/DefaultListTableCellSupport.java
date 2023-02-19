package com.link_intersystems.swing.table;

public class DefaultListTableCellSupport<E> implements ListTableCellSupport<E> {

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int column) {
        return "element";
    }

    @Override
    public Object getValue(E element, int column) {
        return element;
    }
}
