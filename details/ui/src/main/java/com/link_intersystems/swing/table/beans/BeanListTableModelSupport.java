package com.link_intersystems.swing.table.beans;

import com.link_intersystems.swing.table.ListTableModelSupport;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class BeanListTableModelSupport<E> implements ListTableModelSupport<E> {

    public static final Predicate<PropertyDescriptor> DEFAULT_PROPERTY_FILTER = p -> p.getReadMethod() != null;

    private BeanInfo beanInfo;
    private List<PropertyDescriptor> propertyDescriptors;
    private Optional<Comparator<PropertyDescriptor>> propertyOrder = Optional.empty();
    private Predicate<PropertyDescriptor> propertyFilter = DEFAULT_PROPERTY_FILTER;

    public static <E> BeanListTableModelSupport<E> of(Class<E> beanType) {
        BeanListTableModelSupport<E> beanListTableCellSupport = new BeanListTableModelSupport<>();
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

    @Override
    public int getColumnCount() {
        return getPropertyDescriptors().size();
    }

    @Override
    public String getColumnName(int column) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptors().get(column);
        return getColumnName(propertyDescriptor);
    }

    protected String getColumnName(PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor.getDisplayName();
    }

    @Override
    public Object getValue(Object element, int column) {
        Method readMethod = getReadMethod(column);

        if (readMethod == null) {
            return null;
        }

        try {
            return readMethod.invoke(element);
        } catch (Exception e) {
            handlePropertyReadException(e);
            return null;
        }
    }

    protected Method getReadMethod(int column) {
        List<PropertyDescriptor> propertyDescriptors = getPropertyDescriptors();
        PropertyDescriptor propertyDescriptor = propertyDescriptors.get(column);
        return propertyDescriptor.getReadMethod();
    }

    protected void handlePropertyReadException(Exception e) {
        throw new RuntimeException(e);
    }
}
