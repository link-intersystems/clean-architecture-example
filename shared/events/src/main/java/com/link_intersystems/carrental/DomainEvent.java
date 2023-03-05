package com.link_intersystems.carrental;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class DomainEvent {

    private LocalDateTime occuredOn = LocalDateTime.now();

    public LocalDateTime getOccuredOn() {
        return occuredOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEvent that = (DomainEvent) o;
        return Objects.equals(occuredOn, that.occuredOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(occuredOn);
    }

    @Override
    public String toString() {
        return "DomainEvent{" + "occuredOn=" + occuredOn + '}';
    }
}
