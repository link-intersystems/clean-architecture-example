package com.link_intersystems.sakila.customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerFixture {

    private List<CustomerRecord> customerRecords = new ArrayList<>();

    public CustomerFixture() {
        customerRecords.add(createMarySmith());
    }

    private CustomerRecord createMarySmith() {
        CustomerRecord customerRecord = new CustomerRecord();
        customerRecord.id = 1;
        customerRecord.birthday = LocalDate.of(2010, 7, 19);
        return customerRecord;
    }

    public List<CustomerRecord> getCustomerRecords() {
        return Collections.unmodifiableList(customerRecords);
    }

    public CustomerRecord getById(Integer customerId) {
        return getCustomerRecords().stream().filter(lr -> lr.id == customerId).findFirst().orElse(null);
    }
}
