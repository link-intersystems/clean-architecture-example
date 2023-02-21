package com.link_intersystems.swing.table;

public interface ListTableModelSupport<E> {

    public int getColumnCount();

    default public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    public String getColumnName(int column);

    public Object getValue(E element, int column);
}
