package com.link_intersystems.carrental;

public interface DomainEventSubscriber {

    boolean subscribedTo(Class<?> domainEventType);

    void handleEvent(Object domainEvent);
}
