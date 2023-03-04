package com.link_intersystems.carrental.swing.table;

import javax.swing.event.ChangeListener;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DefaultListTableModel<E> extends AbstractListTableModel<E> {

    private ChangeListener tableDescriptorChangeListener = e -> fireTableStructureChanged();

    private ListTableDescriptorModel<E> listTableDescriptorModel = new DefaultListTableDescriptorModel<>();

    private ColumnDescriptor<E> getColumnDescriptor(int column) {
        return listTableDescriptorModel.getColumnDescriptor(column);
    }

    public void setListTableDescriptorModel(ListTableDescriptorModel<E> listTableDescriptorModel) {

        if (this.listTableDescriptorModel != null) {
            this.listTableDescriptorModel.removeChangeListener(tableDescriptorChangeListener);
        }

        this.listTableDescriptorModel = listTableDescriptorModel == null ? new DefaultListTableDescriptorModel<>() : listTableDescriptorModel;

        if (this.listTableDescriptorModel != null) {
            this.listTableDescriptorModel.addChangeListener(tableDescriptorChangeListener);
        }

        fireTableStructureChanged();
    }

    @Override
    public int getColumnCount() {
        return listTableDescriptorModel.getColumnCount();
    }

    @Override
    protected Object getValue(E element, int column) {
        ColumnDescriptor<E> columnDescriptor = getColumnDescriptor(column);
        Function<E, Object> valueGetter = columnDescriptor.getValueGetter();
        return valueGetter.apply(element);
    }

    @Override
    protected void setValue(E element, int column, Object value) {
        ColumnDescriptor<E> columnDescriptor = getColumnDescriptor(column);
        BiConsumer<E, Object> valueSetter = columnDescriptor.getValueSetter();
        if (valueSetter != null) {
            valueSetter.accept(element, value);
        }
    }

    @Override
    public String getColumnName(int column) {
        ColumnDescriptor<E> columnDescriptor = getColumnDescriptor(column);
        return columnDescriptor.getName();
    }
}
