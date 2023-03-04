package com.link_intersystems.swing.table;

import javax.swing.event.ChangeListener;

public interface ListTableDescriptorModel<E> {

    public void addChangeListener(ChangeListener changeListener);

    public void removeChangeListener(ChangeListener changeListener);

    public int getColumnCount();

    public ColumnDescriptor<E> getColumnDescriptor(int column);
}
