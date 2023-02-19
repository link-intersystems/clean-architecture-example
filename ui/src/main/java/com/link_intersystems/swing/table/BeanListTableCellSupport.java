package com.link_intersystems.swing.table;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.*;

public class BeanListTableCellSupport<E> implements ListTableCellSupport<E> {

    private BeanInfo beanInfo;
    private List<PropertyDescriptor> propertyDescriptors;
    private Comparator<PropertyDescriptor> propertyDescriptorOrder = (pd1, pd2) -> 0;

    public static <E> ListTableCellSupport<E> of(Class<E> beanType) {
        BeanListTableCellSupport<E> beanListTableCellSupport = new BeanListTableCellSupport<>();
        beanListTableCellSupport.setBeanInfo(beanType);
        return beanListTableCellSupport;
    }

    public void setBeanInfo(Class<E> beanType) {
        try {
            this.beanInfo = getBeanInfo(beanType);
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

    public void setPropertyDescriptorOrder(Comparator<PropertyDescriptor> propertyDescriptorOrder) {
        this.propertyDescriptorOrder = requireNonNull(propertyDescriptorOrder);
    }

    private List<PropertyDescriptor> getPropertyDescriptors() {
        if (propertyDescriptors == null) {
            propertyDescriptors = new ArrayList<>();

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (isColumnProperty(propertyDescriptor)) {
                    this.propertyDescriptors.add(propertyDescriptor);
                }
            }
            this.propertyDescriptors = sort(this.propertyDescriptors);
        }
        return propertyDescriptors;
    }

    protected List<PropertyDescriptor> sort(List<PropertyDescriptor> propertyDescriptors) {
        propertyDescriptors.sort(propertyDescriptorOrder);
        return propertyDescriptors;
    }

    protected boolean isColumnProperty(PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor.getReadMethod() != null;
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
        List<PropertyDescriptor> propertyDescriptors = getPropertyDescriptors();
        PropertyDescriptor propertyDescriptor = propertyDescriptors.get(column);
        Method readMethod = propertyDescriptor.getReadMethod();

        if (readMethod == null) {
            return null;
        }

        try {
            return readMethod.invoke(element);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
