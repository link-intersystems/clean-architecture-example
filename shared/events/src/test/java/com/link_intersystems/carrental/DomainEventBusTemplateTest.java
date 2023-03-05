package com.link_intersystems.carrental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventBusTemplateTest {

    private CaptureEventSubscriber captureEventSubscriber;
    private List<DomainEventSubscriber> subscribers;
    private DomainEventBusTemplate domainEventBusTemplate;

    @BeforeEach
    void setUp() {
        captureEventSubscriber = new CaptureEventSubscriber();
        subscribers = new ArrayList<>();
        subscribers.add(captureEventSubscriber);

        domainEventBusTemplate = new DomainEventBusTemplate(subscribers);
    }

    @Test
    void publish() {
        LocalDateTime beginOfExecution = LocalDateTime.now();
        domainEventBusTemplate.execute(new DomainEventBusTemplate.DomainEventBusCallback<String, RuntimeException>() {

            @Override
            public String call() {
                StringValueDomainEvent stringValueDomainEvent = new StringValueDomainEvent("");
                DomainEventBus.publish(stringValueDomainEvent);
                return null;
            }
        });
        LocalDateTime endOfExecution = LocalDateTime.now();

        List<Object> capturedEvents = captureEventSubscriber.getCapturedEvents();
        assertEquals(1, capturedEvents.size());
        StringValueDomainEvent stringValueDomainEvent = (StringValueDomainEvent) capturedEvents.get(0);
        assertTrue(stringValueDomainEvent.getOccuredOn().compareTo(beginOfExecution) >= 0);
        assertTrue(stringValueDomainEvent.getOccuredOn().compareTo(endOfExecution) <= 0);
    }

    @Test
    void doNotPublishTheSameEventTwice() {
        subscribers.add(new AbstractDomainEventSubscriber<StringValueDomainEvent>() {
            @Override
            protected void doHandleEvent(StringValueDomainEvent domainEvent) {
                DomainEventBus.publish(domainEvent);
            }
        });
        domainEventBusTemplate = new DomainEventBusTemplate(subscribers);

        domainEventBusTemplate.execute(new DomainEventBusTemplate.DomainEventBusCallback<String, RuntimeException>() {

            @Override
            public String call() {
                StringValueDomainEvent stringValueDomainEvent = new StringValueDomainEvent("");
                DomainEventBus.publish(stringValueDomainEvent);
                return null;
            }
        });

        List<Object> capturedEvents = captureEventSubscriber.getCapturedEvents();
        assertEquals(1, capturedEvents.size());
    }
}