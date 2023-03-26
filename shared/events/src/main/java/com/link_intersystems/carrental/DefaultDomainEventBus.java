package com.link_intersystems.carrental;

import java.util.*;

public class DefaultDomainEventBus implements DomainEventBus {

    private ThreadLocal<Map<DomainEventSubscriber, Set<Object>>> publishedEventsHolder = ThreadLocal.withInitial(IdentityHashMap::new);

    private List<DomainEventSubscriber> subscribers = new ArrayList<>();

    public DefaultDomainEventBus() {
    }

    @Override
    public <T extends DomainEvent> void publish(T domainEvent) {
        Class<?> domainEventType = domainEvent.getClass();

        for (DomainEventSubscriber subscriber : subscribers) {
            if (subscriber.subscribedTo(domainEventType)) {
                Set<Object> events = getPublishedEvents(subscriber);
                if (events.add(domainEvent)) {
                    subscriber.handleEvent(domainEvent);
                }
            }
        }
    }

    private Set<Object> getPublishedEvents(DomainEventSubscriber subscriber) {
        Map<DomainEventSubscriber, Set<Object>> publishedEvents = publishedEventsHolder.get();
        return publishedEvents.computeIfAbsent(subscriber, s -> new HashSet<>());
    }

    public void addSubscribers(DomainEventSubscriber... subscribers) {
        this.addSubscribers(Arrays.asList(subscribers));
    }

    public void addSubscribers(List<DomainEventSubscriber> subscribers) {
        this.subscribers.addAll(subscribers);
    }
}
