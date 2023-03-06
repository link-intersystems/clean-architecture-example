package com.link_intersystems.carrental.management.pickup.ui;

import com.link_intersystems.beans.BeanClass;
import com.link_intersystems.beans.BeansFactory;
import com.link_intersystems.beans.PropertyDesc;
import com.link_intersystems.beans.PropertyDescList;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.link_intersystems.carrental.management.pickup.ui.SpringUtilities.*;
import static java.util.Objects.*;

public class BeanEditForm<T> {

    private static BeansFactory beansFactory = BeansFactory.getDefault();

    private BeanClass<T> beanClass;

    private SpringLayout layout = new SpringLayout();
    private JPanel panel = new JPanel(layout);

    private int textFieldColumns = 20;

    private Map<PropertyDesc, JTextComponent> textComponentMap = new HashMap<>();
    private T bean;

    public BeanEditForm(Class<T> beanType) {
        this(BeansFactory.getDefault().createBeanClass(beanType, Object.class));
    }

    public BeanEditForm(BeanClass<T> beanClass) {
        this.beanClass = requireNonNull(beanClass);
    }

    public void setBean(T bean) {
        updateForm();
        this.bean = bean;
    }

    public void setTextFieldColumns(int textFieldColumns) {
        this.textFieldColumns = textFieldColumns;
    }

    protected int compareProperties(PropertyDescriptor pd1, PropertyDescriptor pd2) {
        String displayName1 = pd1.getDisplayName();
        String displayName2 = pd2.getDisplayName();

        return CharSequence.compare(displayName1, displayName2);
    }

    private void updateForm() {
        panel.removeAll();
        textComponentMap.clear();

        PropertyDescList properties = beanClass.getProperties();

        for (PropertyDesc propertyDesc : properties) {

            String displayName = propertyDesc.getName();
            panel.add(new JLabel(displayName));

            JTextField textField = new JTextField(textFieldColumns);
            panel.add(textField);

            textComponentMap.put(propertyDesc, textField);
        }

        makeGrid(panel, properties.size(), 2, 3, 3, 3, 3);
    }


    public Component getComponent() {
        updateForm();
        return panel;
    }
}
