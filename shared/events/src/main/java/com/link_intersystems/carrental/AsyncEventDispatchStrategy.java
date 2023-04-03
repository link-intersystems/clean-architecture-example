package com.link_intersystems.carrental;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.Objects.*;

public class AsyncEventDispatchStrategy implements EventDispatchStrategy {

    private SyncEventDispatchStrategy syncEventDispatchStrategy = new SyncEventDispatchStrategy();
    private Executor executor;

    public AsyncEventDispatchStrategy() {
        this(Executors.newSingleThreadExecutor());
    }

    public AsyncEventDispatchStrategy(Executor executor) {
        this.executor = requireNonNull(executor);
    }

    @Override
    public void dispatch(DomainEvent domainEvent, List<DomainEventSubscriber> domainEventSubscribers) {
        executor.execute(() -> syncEventDispatchStrategy.dispatch(domainEvent, domainEventSubscribers));
    }
}
