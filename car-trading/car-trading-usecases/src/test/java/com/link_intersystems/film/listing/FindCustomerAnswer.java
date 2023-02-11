package com.link_intersystems.film.listing;

import com.link_intersystems.person.customer.Customer;
import com.link_intersystems.person.customer.CustomerFixture;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FindCustomerAnswer implements Answer<Customer> {

    private CustomerFixture customerFixture;

    public FindCustomerAnswer(CustomerFixture customerFixture) {
        this.customerFixture = customerFixture;
    }

    @Override
    public Customer answer(InvocationOnMock invocationOnMock) throws Throwable {
        Integer customerId = invocationOnMock.getArgument(0, Integer.class);

        return customerFixture.getById(customerId);
    }
}
