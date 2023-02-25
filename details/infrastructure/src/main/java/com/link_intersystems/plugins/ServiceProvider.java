package com.link_intersystems.plugins;

public interface ServiceProvider {

    void init(ApplicationContext applicationContext);

    public boolean provides(Class<?> type);

    <T> T getService(Class<T> type);
}
