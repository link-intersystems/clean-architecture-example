package com.link_intersystems.swing.table;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public abstract class AbstractListTableDescriptorModel<E> implements ListTableDescriptorModel<E> {

    private EventListenerList eventListenerList = new EventListenerList();

    @Override
    public void addChangeListener(ChangeListener changeListener) {
        eventListenerList.add(ChangeListener.class, changeListener);
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
        eventListenerList.remove(ChangeListener.class, changeListener);
    }

    protected void fireChanged() {
        ChangeListener[] listeners = eventListenerList.getListeners(ChangeListener.class);
        if (listeners.length > 0) {
            ChangeEvent e = new ChangeEvent(this);
            for (ChangeListener listener : listeners) {
                listener.stateChanged(e);
            }
        }
    }
}
