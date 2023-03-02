package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.DataSetRegistry;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.time.Period;

import java.util.List;
import java.util.Objects;

public class DBUnitCarBookingRepository implements CarBookingRepository {

    private final DataSetRegistry dataSetRegistry;

    public DBUnitCarBookingRepository(DataSetRegistry dataSetRegistry) {
        this.dataSetRegistry = Objects.requireNonNull(dataSetRegistry);
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        List<CarBooking> carBookingList = dataSetRegistry.getDomainEntities("carbooking");
        return carBookingList.stream()
                .filter(cr -> carId.equals(cr.getCarId()))
                .filter(cr -> cr.getBookingPeriod().overlaps(bookingPeriod))
                .findFirst().orElse(null);
    }

    @Override
    public void persist(CarBooking carBooking) {
        throw new RuntimeException("Sorry, this repository is read-only.");
    }

    @Override
    public boolean isCustomerExistent(CustomerId customerId) {
        List<Customer> customers = dataSetRegistry.getDomainEntities("customer");
        return customers.stream().anyMatch(c -> c.getId().equals(customerId));
    }
}

