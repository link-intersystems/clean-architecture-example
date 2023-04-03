package com.link_intersystems.carrental;

import java.util.Arrays;
import java.util.List;

public interface DomainEventDispatcher {

    default public void addSubscribers(DomainEventSubscriber... subscribers) {
        this.addSubscribers(Arrays.asList(subscribers));
    }

    void addSubscribers(List<DomainEventSubscriber> subscribers);
}
