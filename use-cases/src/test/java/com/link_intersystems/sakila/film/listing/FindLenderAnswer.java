package com.link_intersystems.sakila.film.listing;

import com.link_intersystems.sakila.customer.CustomerFixture;
import com.link_intersystems.sakila.customer.CustomerRecord;
import com.link_intersystems.sakilla.lender.Lender;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FindLenderAnswer implements Answer<Lender> {

    private CustomerFixture customerFixture;

    public FindLenderAnswer(CustomerFixture customerFixture) {
        this.customerFixture = customerFixture;
    }

    @Override
    public Lender answer(InvocationOnMock invocationOnMock) throws Throwable {
        Integer customerId = invocationOnMock.getArgument(0, Integer.class);

        CustomerRecord customerRecord = customerFixture.getById(customerId);

        return map(customerRecord);
    }

    private Lender map(CustomerRecord record) {
        return new Lender(record.id, record.birthday);
    }
}
