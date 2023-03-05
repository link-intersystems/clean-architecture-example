package com.link_intersystems.carrental;

import java.util.Objects;

class StringValueDomainEvent extends DomainEvent {

    private String value;

    public StringValueDomainEvent(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringValueDomainEvent that = (StringValueDomainEvent) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
