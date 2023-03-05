package com.link_intersystems.carrental;

import java.util.ArrayList;
import java.util.List;

class CaptureEventSubscriber extends AbstractDomainEventSubscriber<Object> {

    private List<Object> capturedEvents = new ArrayList();

    protected void doHandleEvent(Object domainEvent) {
        capturedEvents.add(domainEvent);
    }

    public List<Object> getCapturedEvents() {
        return capturedEvents;
    }
}
