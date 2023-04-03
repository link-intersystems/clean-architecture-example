package com.link_intersystems.carrental;

public interface DomainEventPublisher {

    public <T extends DomainEvent> void publish(T domainEvent);

}
