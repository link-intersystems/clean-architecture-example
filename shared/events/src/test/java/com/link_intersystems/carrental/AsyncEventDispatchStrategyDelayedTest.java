package com.link_intersystems.carrental;

class AsyncEventDispatchStrategyDelayedTest extends AbstractEventDispatchStrategyTest {

    @Override
    protected EventDispatchStrategy createEventDispatchStrategy() {
        return new AsyncEventDispatchStrategy(r -> r.run(), 100);
    }
}