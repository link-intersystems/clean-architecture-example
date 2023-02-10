package com.link_intersystems.film.listing;

import com.link_intersystems.person.customer.db.CustomerTable;
import com.link_intersystems.person.customer.db.CustomerRecord;
import com.link_intersystems.person.customer.Customer;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FindLenderAnswer implements Answer<Customer> {

    private CustomerTable customerTable;

    public FindLenderAnswer(CustomerTable customerTable) {
        this.customerTable = customerTable;
    }

    @Override
    public Customer answer(InvocationOnMock invocationOnMock) throws Throwable {
        Integer customerId = invocationOnMock.getArgument(0, Integer.class);

        CustomerRecord customerRecord = customerTable.getById(customerId);

        return map(customerRecord);
    }

    private Customer map(CustomerRecord record) {
        return new Customer(record.id, record.birthday);
    }
}
