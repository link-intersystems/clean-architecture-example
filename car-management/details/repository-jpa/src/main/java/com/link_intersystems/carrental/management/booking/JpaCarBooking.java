package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import jakarta.persistence.*;

@Entity(name = "CarBooking")
@Table(name = "CAR_BOOKING")
public class JpaCarBooking {

    @Id
    @Column(name = "BOOKING_NUMBER")
    private Integer bookingNumber;

    @Column(name = "VIN")
    private String vin;

    @Column(name = "RENTAL_STATE")
    private String rentalState;

    @Column(name = "CUSTOMER_FIRSTNAME")
    private String customerFirstname;

    @Column(name = "CUSTOMER_LASTNAME")
    private String customerLastname;

    @Transient
    private CarBooking carBooking;

    public JpaCarBooking() {
    }

    public void update(CarBooking carBooking) {
        this.carBooking = carBooking;

        this.vin = carBooking.getVin().getValue();
        this.bookingNumber = carBooking.getBookingNumber().getValue();
        RentalState rentalState = carBooking.getRentalState();
        this.rentalState = rentalState == null ? null : rentalState.name();
        this.customerFirstname = carBooking.getCustomer().getFirstname();
        this.customerLastname = carBooking.getCustomer().getLastname();
    }

    public CarBooking getDomainObject() {
        BookingNumber bookingNumber = new BookingNumber(this.bookingNumber);
        VIN vin = new VIN(this.vin);
        Customer customer = new Customer(customerFirstname, customerLastname);
        return new CarBooking(bookingNumber, vin, customer);
    }

}
