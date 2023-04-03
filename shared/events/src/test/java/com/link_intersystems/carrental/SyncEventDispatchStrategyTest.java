package com.link_intersystems.carrental;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SyncEventDispatchStrategyTest extends AbstractEventDispatchStrategyTest {

    @Override
    protected EventDispatchStrategy createEventDispatchStrategy() {
        return new SyncEventDispatchStrategy();
    }


    @Test
    void doNotPublishTheSameEventTwice() {
        subscribers.add(new SingleDomainEventSubscriber<StringValueDomainEvent>() {
            @Override
            protected void doHandleEvent(StringValueDomainEvent domainEvent) {
                eventDispatchStrategy.dispatch(domainEvent, subscribers);
            }
        });

        StringValueDomainEvent stringValueDomainEvent = new StringValueDomainEvent("");
        eventDispatchStrategy.dispatch(stringValueDomainEvent, subscribers);

        List<DomainEvent> capturedEvents = captureEventSubscriber.getCapturedEvents();
        assertEquals(1, capturedEvents.size());
    }
}