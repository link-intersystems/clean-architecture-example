package com.link_intersystems.carrental;

import java.lang.reflect.ParameterizedType;

public abstract class SingleDomainEventSubscriber<E extends DomainEvent> implements DomainEventSubscriber {

    @Override
    public boolean subscribedTo(Class<?> domainEventType) {
        Class<?> eventType = getSubscribedEventType();
        return eventType.isAssignableFrom(domainEventType);
    }

    private Class<?> getSubscribedEventType() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<?>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public void handleEvent(Object domainEvent) {
        doHandleEvent((E) domainEvent);
    }

    protected abstract void doHandleEvent(E domainEvent);
}
