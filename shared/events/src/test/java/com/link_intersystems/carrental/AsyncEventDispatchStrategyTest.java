package com.link_intersystems.carrental;

class AsyncEventDispatchStrategyTest extends AbstractEventDispatchStrategyTest {

    @Override
    protected EventDispatchStrategy createEventDispatchStrategy() {
        return new AsyncEventDispatchStrategy(r -> r.run());
    }
}