package com.link_intersystems.carrental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDomainEventBusTest {

    private CaptureEventSubscriber captureEventSubscriber;
    private List<DomainEventSubscriber> subscribers;
    private DefaultDomainEventBus domainEventBus;

    @BeforeEach
    void setUp() {
        captureEventSubscriber = new CaptureEventSubscriber();
        subscribers = new ArrayList<>();
        subscribers.add(captureEventSubscriber);

        domainEventBus = new DefaultDomainEventBus();
        domainEventBus.addSubscribers(captureEventSubscriber);
    }

    @Test
    void publish() {
        LocalDateTime beginOfExecution = LocalDateTime.now();

        StringValueDomainEvent stringValueDomainEvent = new StringValueDomainEvent("");
        domainEventBus.publish(stringValueDomainEvent);
        LocalDateTime endOfExecution = LocalDateTime.now();

        List<DomainEvent> capturedEvents = captureEventSubscriber.getCapturedEvents();
        assertEquals(1, capturedEvents.size());
        DomainEvent publishedEvent = capturedEvents.get(0);
        assertTrue(publishedEvent.getOccuredOn().compareTo(beginOfExecution) >= 0);
        assertTrue(publishedEvent.getOccuredOn().compareTo(endOfExecution) <= 0);
    }

    @Test
    void doNotPublishTheSameEventTwice() {
        subscribers.add(new SingleDomainEventSubscriber<StringValueDomainEvent>() {
            @Override
            protected void doHandleEvent(StringValueDomainEvent domainEvent) {
                domainEventBus.publish(domainEvent);
            }
        });

        StringValueDomainEvent stringValueDomainEvent = new StringValueDomainEvent("");
        domainEventBus.publish(stringValueDomainEvent);

        List<DomainEvent> capturedEvents = captureEventSubscriber.getCapturedEvents();
        assertEquals(1, capturedEvents.size());
    }
}