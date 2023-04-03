package com.link_intersystems.carrental;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class DomainEventBus implements DomainEventPublisher, DomainEventDispatcher {

    private List<DomainEventSubscriber> subscribers = new ArrayList<>();

    private EventDispatchStrategy eventDispatchStrategy;

    public DomainEventBus() {
        this(new SyncEventDispatchStrategy());
    }

    public DomainEventBus(EventDispatchStrategy eventDispatchStrategy) {
        this.eventDispatchStrategy = requireNonNull(eventDispatchStrategy);
    }

    @Override
    public <T extends DomainEvent> void publish(T domainEvent) {
        List<DomainEventSubscriber> domainEventSubscribers = getSubscribers(domainEvent);
        eventDispatchStrategy.dispatch(domainEvent, domainEventSubscribers);
    }

    @Override
    public void addSubscribers(List<DomainEventSubscriber> subscribers) {
        this.subscribers.addAll(subscribers);
    }

    protected List<DomainEventSubscriber> getSubscribers(DomainEvent domainEvent) {
        Class<? extends DomainEvent> domainEventType = domainEvent.getClass();
        return subscribers.stream().filter(s -> s.subscribedTo(domainEventType)).collect(Collectors.toList());
    }

}
