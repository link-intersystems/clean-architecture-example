package com.link_intersystems.carrental;

import java.util.*;

public class SyncEventDispatchStrategy implements EventDispatchStrategy {

    private ThreadLocal<Map<DomainEventSubscriber, Set<Object>>> publishedEventsHolder = ThreadLocal.withInitial(IdentityHashMap::new);

    @Override
    public void dispatch(DomainEvent domainEvent, List<DomainEventSubscriber> domainEventSubscribers) {
        for (DomainEventSubscriber subscriber : domainEventSubscribers) {
            Set<Object> events = getPublishedEvents(subscriber);
            if (events.add(domainEvent)) {
                subscriber.handleEvent(domainEvent);
            }
        }
    }

    private Set<Object> getPublishedEvents(DomainEventSubscriber subscriber) {
        Map<DomainEventSubscriber, Set<Object>> publishedEvents = publishedEventsHolder.get();
        return publishedEvents.computeIfAbsent(subscriber, s -> new HashSet<>());
    }
}
