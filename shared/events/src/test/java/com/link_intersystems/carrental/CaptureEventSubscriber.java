package com.link_intersystems.carrental;

import java.util.ArrayList;
import java.util.List;

class CaptureEventSubscriber extends SingleDomainEventSubscriber<DomainEvent> {

    private List<DomainEvent> capturedEvents = new ArrayList();

    protected void doHandleEvent(DomainEvent domainEvent) {
        capturedEvents.add(domainEvent);
    }

    public List<DomainEvent> getCapturedEvents() {
        return capturedEvents;
    }
}
