package com.link_intersystems.carrental;

public interface DomainEventBus {

    public <T extends DomainEvent> void publish(T domainEvent);

}
