package com.link_intersystems.swing.table;

public class DefaultListTableModelSupport<E> implements ListTableModelSupport<E> {

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int column) {
        return "A";
    }

    @Override
    public Object getValue(E element, int column) {
        return element;
    }
}
