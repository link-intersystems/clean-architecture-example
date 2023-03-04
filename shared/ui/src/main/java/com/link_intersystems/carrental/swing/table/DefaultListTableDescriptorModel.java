package com.link_intersystems.carrental.swing.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DefaultListTableDescriptorModel<E> extends AbstractListTableDescriptorModel<E> {

    private List<ColumnDescriptor<E>> columnDescriptors = new ArrayList<>();

    public void addColumnDescriptor(String name) {
        addColumnDescriptor(name, String::valueOf, null);
    }

    public void addColumnDescriptor(String name, Function<E, Object> valueGetter) {
        addColumnDescriptor(name, valueGetter, null);
    }

    public void addColumnDescriptor(String name, Function<E, Object> valueGetter, BiConsumer<E, Object> valueSetter) {
        ColumnDescriptor<E> columnDescriptor = new ColumnDescriptor<>(name, valueGetter);
        columnDescriptor.setValueSetter(valueSetter);
        columnDescriptors.add(columnDescriptor);
        fireChanged();
    }

    public void removeColumnDescriptor(String name) {
        for (int i = 0; i < columnDescriptors.size(); i++) {
            if (columnDescriptors.get(i).getName().equals(name)) {
                columnDescriptors.remove(i);
                fireChanged();
                break;
            }
        }
    }

    @Override
    public int getColumnCount() {
        return columnDescriptors.size();
    }

    @Override
    public ColumnDescriptor<E> getColumnDescriptor(int column) {
        return columnDescriptors.get(column);
    }
}
