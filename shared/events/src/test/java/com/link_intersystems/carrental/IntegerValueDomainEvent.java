package com.link_intersystems.carrental;

import java.util.Objects;

class IntegerValueDomainEvent extends DomainEvent {

    private Integer value;

    public IntegerValueDomainEvent(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IntegerValueDomainEvent that = (IntegerValueDomainEvent) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
