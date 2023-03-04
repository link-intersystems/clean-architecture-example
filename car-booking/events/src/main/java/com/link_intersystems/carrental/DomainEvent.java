package com.link_intersystems.carrental;

import java.time.LocalDateTime;

public interface DomainEvent {

    public LocalDateTime occuredOn();
}
