package com.link_intersystems.carrental;

import java.util.List;

public interface EventDispatchStrategy {
    public abstract void dispatch(DomainEvent domainEvent, List<DomainEventSubscriber> domainEventSubscribers);
}
