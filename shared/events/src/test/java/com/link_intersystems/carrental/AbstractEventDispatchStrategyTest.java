package com.link_intersystems.carrental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractEventDispatchStrategyTest {

    protected CaptureEventSubscriber captureEventSubscriber;
    protected List<DomainEventSubscriber> subscribers;
    protected EventDispatchStrategy eventDispatchStrategy;

    @BeforeEach
    void setUp() {
        captureEventSubscriber = new CaptureEventSubscriber();
        subscribers = new ArrayList<>();
        subscribers.add(captureEventSubscriber);

        eventDispatchStrategy = createEventDispatchStrategy();
    }

    protected abstract EventDispatchStrategy createEventDispatchStrategy();

    @Test
    void dispatchEvent() {
        LocalDateTime beginOfExecution = LocalDateTime.now();

        StringValueDomainEvent stringValueDomainEvent = new StringValueDomainEvent("");
        eventDispatchStrategy.dispatch(stringValueDomainEvent, subscribers);
        LocalDateTime endOfExecution = LocalDateTime.now();

        List<DomainEvent> capturedEvents = captureEventSubscriber.getCapturedEvents();
        assertEquals(1, capturedEvents.size());
        DomainEvent publishedEvent = capturedEvents.get(0);
        assertTrue(publishedEvent.getOccuredOn().compareTo(beginOfExecution) >= 0);
        assertTrue(publishedEvent.getOccuredOn().compareTo(endOfExecution) <= 0);
    }
}
