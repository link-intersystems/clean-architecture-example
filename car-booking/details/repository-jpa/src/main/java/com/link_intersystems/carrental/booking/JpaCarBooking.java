package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.time.LocalDateTime;

@Entity(name = "CarBooking")
@Table(name = "CAR_BOOKING")
public class JpaCarBooking {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(name = "sequence-generator", type = SequenceStyleGenerator.class, parameters = {@Parameter(name = "sequence_name", value = "CAR_BOOKING_SEQ"), @Parameter(name = "increment_size", value = "1")})
    @Column(name = "BOOKING_NUMBER")
    public Integer bookingNumberValue;

    @Column(name = "CARID")
    public String carIdValue;

    @Column(name = "CUSTOMER_ID")
    public Integer customerIdValue;

    @Column(name = "PICKUP_DATETIME")
    public LocalDateTime pickupDateTime;

    @Column(name = "RETURN_DATETIME")
    public LocalDateTime returnDateTime;

    @Transient
    private CarBooking carBooking;

    JpaCarBooking() {
    }

    JpaCarBooking(CarBooking carBooking) {
        this.bookingNumberValue = carBooking.getBookingNumber() == null ? null : carBooking.getBookingNumber().getValue();

        this.carIdValue = carBooking.getCarId().getValue();
        this.customerIdValue = carBooking.getCustomerId().getValue();
        this.pickupDateTime = carBooking.getBookingPeriod().getBegin();
        this.returnDateTime = carBooking.getBookingPeriod().getEnd();

        this.carBooking = carBooking;
    }

    public CarBooking getDomainObject() {
        if (carBooking == null) {
            CustomerId customerId = new CustomerId(customerIdValue);
            CarId carId = new CarId(new VIN(carIdValue));
            Period bookingPeriod = new Period(pickupDateTime, returnDateTime);
            carBooking = new CarBooking(customerId, carId, bookingPeriod);
        }

        return carBooking;
    }

    public void updateDomainObject() {
        BookingNumber bookingNumber = new BookingNumber(bookingNumberValue);
        getDomainObject().setBookingNumber(bookingNumber);
    }
}
