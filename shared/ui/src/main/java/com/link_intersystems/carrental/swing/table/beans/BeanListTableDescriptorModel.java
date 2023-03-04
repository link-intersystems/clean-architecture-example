package com.link_intersystems.carrental.swing.table.beans;

import com.link_intersystems.carrental.swing.table.AbstractListTableDescriptorModel;
import com.link_intersystems.carrental.swing.table.ColumnDescriptor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class BeanListTableDescriptorModel<E> extends AbstractListTableDescriptorModel<E> {

    public static final Predicate<PropertyDescriptor> DEFAULT_PROPERTY_FILTER = p -> p.getReadMethod() != null;

    private BeanInfo beanInfo;
    private List<PropertyDescriptor> propertyDescriptors;
    private List<ColumnDescriptor<E>> columnDescriptors;
    private Optional<Comparator<PropertyDescriptor>> propertyOrder = Optional.empty();
    private Predicate<PropertyDescriptor> propertyFilter = DEFAULT_PROPERTY_FILTER;

    public static <E> BeanListTableDescriptorModel<E> of(Class<E> beanType) {
        BeanListTableDescriptorModel<E> beanListTableCellSupport = new BeanListTableDescriptorModel<>();
        beanListTableCellSupport.setBeanInfo(beanType);
        return beanListTableCellSupport;
    }

    public void setBeanInfo(Class<E> beanType) {
        try {
            setBeanInfo(getBeanInfo(beanType));
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

    protected BeanInfo getBeanInfo(Class<E> beanType) throws IntrospectionException {
        return Introspector.getBeanInfo(beanType, Object.class);
    }

    public void setBeanInfo(BeanInfo beanInfo) {
        this.beanInfo = requireNonNull(beanInfo);
        this.propertyDescriptors = null;
        this.columnDescriptors = null;
        fireChanged();
    }

    public void setPropertyOrder(Comparator<PropertyDescriptor> propertyOrder) {
        this.propertyOrder = Optional.ofNullable(propertyOrder);
        this.propertyDescriptors = null;
    }

    public void setPropertyFilter(Predicate<PropertyDescriptor> propertyFilter) {
        this.propertyFilter = requireNonNull(propertyFilter);
        this.propertyDescriptors = null;
    }

    private List<PropertyDescriptor> getPropertyDescriptors() {
        if (propertyDescriptors == null) {
            List<PropertyDescriptor> propertyDescriptors = new ArrayList<>(Arrays.asList(beanInfo.getPropertyDescriptors()));

            propertyDescriptors = propertyDescriptors.stream().filter(propertyFilter).collect(Collectors.toList());
            propertyOrder.ifPresent(propertyDescriptors::sort);

            this.propertyDescriptors = propertyDescriptors;
        }
        return propertyDescriptors;
    }

    public List<ColumnDescriptor<E>> getColumnDescriptors() {
        if (columnDescriptors == null) {
            List<PropertyDescriptor> propertyDescriptors = getPropertyDescriptors();
            this.columnDescriptors = propertyDescriptors.stream().map(this::toColumnDescriptor).collect(Collectors.toList());
        }
        return columnDescriptors;
    }

    private ColumnDescriptor<E> toColumnDescriptor(PropertyDescriptor propertyDescriptor) {
        String displayName = propertyDescriptor.getDisplayName();
        Function<E, Object> valueGetter = getValueGetter(propertyDescriptor);
        ColumnDescriptor<E> columnDescriptor = new ColumnDescriptor<>(displayName, valueGetter);

        BiConsumer<E, Object> valueSetter = getValueSetter(propertyDescriptor);
        columnDescriptor.setValueSetter(valueSetter);

        return columnDescriptor;
    }

    private Function<E, Object> getValueGetter(PropertyDescriptor propertyDescriptor) {
        Function<E, Object> valueGetter = e -> null;

        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod != null) {
            valueGetter = e -> {
                try {
                    return readMethod.invoke(e);
                } catch (Exception ex) {
                    handlePropertyReadException(ex);
                    return null;
                }
            };
        }
        return valueGetter;
    }

    private BiConsumer<E, Object> getValueSetter(PropertyDescriptor propertyDescriptor) {
        BiConsumer<E, Object> valueSetter = null;

        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod != null) {
            valueSetter = (element, value) -> {
                try {
                    writeMethod.invoke(element, value);
                } catch (Exception ex) {
                    handlePropertyWriteException(ex);
                }
            };
        }

        return valueSetter;
    }

    @Override
    public int getColumnCount() {
        return getPropertyDescriptors().size();
    }

    @Override
    public ColumnDescriptor<E> getColumnDescriptor(int column) {
        List<ColumnDescriptor<E>> columnDescriptors = getColumnDescriptors();
        return columnDescriptors.get(column);
    }

    protected void handlePropertyReadException(Exception e) {
        throw new RuntimeException(e);
    }

    protected void handlePropertyWriteException(Exception e) {
        throw new RuntimeException(e);
    }
}
