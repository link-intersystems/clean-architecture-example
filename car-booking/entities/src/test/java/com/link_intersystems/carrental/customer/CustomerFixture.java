package com.link_intersystems.carrental.customer;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.carrental.time.ClockFactory;

import java.util.List;

public class CustomerFixture extends EntityFixture<Customer> {

    private ClockFactory clockFactory = new ClockFactory();

    @Override
    protected void init(List<Customer> entities) {
        entities.add(createMarySmith());
    }

    private Customer createMarySmith() {
        return new Customer(new CustomerId(1), "Mary", "Smith");
    }

    public Customer getById(Integer customerId) {
        return stream().filter(c -> c.getId().getValue() == customerId).findFirst().orElse(null);
    }
}
