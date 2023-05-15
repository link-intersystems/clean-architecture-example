package com.link_intersystems.carrental.swing.context;

import java.util.EventListener;

public interface ModelContextListener<T> extends EventListener {

    public void modelAdded(T model);
    public void modelRemoved(T model);

}
