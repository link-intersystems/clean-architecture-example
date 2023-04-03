package com.link_intersystems.carrental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventBusTest {

    private CaptureEventSubscriber captureEventSubscriber;
    private DomainEventBus domainEventBus;

    @BeforeEach
    void setUp() {
        captureEventSubscriber = new CaptureEventSubscriber();

        domainEventBus = new DomainEventBus((domainEvent, domainEventSubscribers) -> {
            domainEventSubscribers.forEach(s -> s.handleEvent(domainEvent));
        });
    }

    @Test
    void publish() {
        domainEventBus.addSubscribers(captureEventSubscriber);

        StringValueDomainEvent stringValueDomainEvent = new StringValueDomainEvent("");
        domainEventBus.publish(stringValueDomainEvent);

        List<DomainEvent> capturedEvents = captureEventSubscriber.getCapturedEvents();
        assertEquals(1, capturedEvents.size());
        DomainEvent publishedEvent = capturedEvents.get(0);
        assertEquals(stringValueDomainEvent, publishedEvent);
    }
}