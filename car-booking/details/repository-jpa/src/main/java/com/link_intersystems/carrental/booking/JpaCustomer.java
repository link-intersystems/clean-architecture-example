package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import jakarta.persistence.*;

@Entity(name = "Customer")
@Table(name = "CUSTOMER")
public class JpaCustomer {

    @Id
    private Integer id;

    @Column(name = "FIRSTNAME")
    private String fistname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Transient
    private Customer customer;

    public Customer getDomainObject() {
        if (customer == null) {
            customer = new Customer(new CustomerId(id), fistname, lastname);
        }

        return customer;
    }
}
