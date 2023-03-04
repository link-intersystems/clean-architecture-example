package com.link_intersystems.carrental;

import java.util.ArrayList;
import java.util.List;

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

    private boolean publishing = false;

    public DomainEventBus(List<DomainEventSubscriber> subscribers) {
        this.subscribers.addAll(subscribers);
    }

    private <T> void publishEvent(T domainEvent) {
        if (publishing) {
            return;
        }

        try {
            publishing = true;

            Class<?> domainEventType = domainEvent.getClass();

            for (DomainEventSubscriber subscriber : subscribers) {
                if (subscriber.subscribedTo(domainEventType)) {
                    subscriber.handleEvent(domainEvent);
                }
            }

        } finally {
            publishing = false;
        }
    }

}
