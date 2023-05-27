package com.link_intersystems.carrental.swing.bean;

import com.link_intersystems.beans.Bean;
import com.link_intersystems.beans.BeansFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PropertyMediator {

    private List<Change> changes = new ArrayList<>();

    private Change add(Change change) {
        changes.add(change);
        return change;
    }


    public void dispose() {
        changes.forEach(Change::dispose);
        changes.clear();
    }

    public <T> Change update(Consumer<T> valueConsumer) {
        return add(new Change() {

            private Runnable dispose;

            @Override
            public void onChange(Object beanObject, String propertyName) {
                BeansFactory beansFactory = BeansFactory.getDefault();
                Bean<Object> bean = beansFactory.createBean(beanObject);

                PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals(propertyName)) {
                            Object newValue = evt.getNewValue();
                            valueConsumer.accept((T) newValue);
                        }
                    }
                };

                dispose = () -> bean.removeListener(propertyChangeListener);

                bean.addListener(propertyChangeListener);
            }

            @Override
            public void dispose() {
                if (dispose != null) {
                    dispose.run();
                }

                dispose = null;
            }
        });
    }

}