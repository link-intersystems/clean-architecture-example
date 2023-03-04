package com.link_intersystems.carrental;

import java.util.List;

public class DomainEventBusTemplate {

    public static interface DomainEventBusCallback<T, E extends Exception> {

        public T call() throws E;
    }

    private List<DomainEventSubscriber> subscribers;

    public DomainEventBusTemplate(List<DomainEventSubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public <T, E extends Exception> T execute(DomainEventBusCallback<T, E> callable) throws E {
        DomainEventBus domainEventBus = new DomainEventBus(subscribers);
        DomainEventBus.setCurrent(domainEventBus);

        try {
            return callable.call();
        } finally {
            DomainEventBus.removeCurrent();
        }
    }
}
