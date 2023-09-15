package com.link_intersystems.carrental;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import static java.util.Objects.*;

public class AsyncEventDispatchStrategy implements EventDispatchStrategy {

    private static final int DEFAULT_DELAY = 0;

    private final int delay;
    private SyncEventDispatchStrategy syncEventDispatchStrategy = new SyncEventDispatchStrategy();
    private Executor executor;

    private BiConsumer<DomainEvent, List<DomainEventSubscriber>> dispatchConsumer;

    public AsyncEventDispatchStrategy() {
        this(0);
    }

    public AsyncEventDispatchStrategy(int delay) {
        this(Executors.newSingleThreadExecutor(), delay);
    }

    public AsyncEventDispatchStrategy(Executor executor) {
        this(executor, DEFAULT_DELAY);
    }

    public AsyncEventDispatchStrategy(Executor executor, int delay) {
        this.delay = delay;
        this.executor = requireNonNull(executor);

        if (delay > 0) {
            dispatchConsumer = (de, s) -> executor.execute(() -> {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                syncEventDispatchStrategy.dispatch(de, s);
            });
        } else {
            dispatchConsumer = (de, s) -> executor.execute(() -> syncEventDispatchStrategy.dispatch(de, s));
        }
    }

    @Override
    public void dispatch(DomainEvent domainEvent, List<DomainEventSubscriber> domainEventSubscribers) {
        dispatchConsumer.accept(domainEvent, domainEventSubscribers);
    }
}
