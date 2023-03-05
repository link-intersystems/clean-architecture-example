package com.link_intersystems.carrental;

import java.util.*;

public class DomainEventBus {

    private static final ThreadLocal<DomainEventBus> DOMAIN_EVENT_BUS_HOLDER = new ThreadLocal<>();

    static void setCurrent(DomainEventBus domainEventBus) {
        DOMAIN_EVENT_BUS_HOLDER.set(domainEventBus);
    }

    static void removeCurrent() {
        DOMAIN_EVENT_BUS_HOLDER.remove();
    }

    static DomainEventBus getCurrent() {
        return DOMAIN_EVENT_BUS_HOLDER.get();
    }

    public static <T extends DomainEvent> void publish(T domainEvent) {
        DomainEventBus current = getCurrent();
        if (current != null) {
            current.publishEvent(domainEvent);
        }
    }

    private List<DomainEventSubscriber> subscribers = new ArrayList<>();

    private ThreadLocal<Map<DomainEventSubscriber, Set<Object>>> publishedEventsHolder = new ThreadLocal<>() {
        @Override
        protected Map<DomainEventSubscriber, Set<Object>> initialValue() {
            return new IdentityHashMap<>();
        }
    };


    private boolean publishing = false;

    public DomainEventBus(List<DomainEventSubscriber> subscribers) {
        this.subscribers.addAll(subscribers);
    }

    private <T> void publishEvent(T domainEvent) {
        try {
            Class<?> domainEventType = domainEvent.getClass();

            for (DomainEventSubscriber subscriber : subscribers) {
                    if (subscriber.subscribedTo(domainEventType)) {
                        Set<Object> events = getPublishedEvents(subscriber);
                        if (events.add(domainEvent)) {
                            subscriber.handleEvent(domainEvent);
                        }
                    }
            }

        } finally {
            publishedEventsHolder.remove();
        }
    }

    private Set<Object> getPublishedEvents(DomainEventSubscriber subscriber) {
        Map<DomainEventSubscriber, Set<Object>> publishedEvents = publishedEventsHolder.get();
        return publishedEvents.computeIfAbsent(subscriber, s -> new HashSet<>());
    }

}
