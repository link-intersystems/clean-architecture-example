package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "CarRental")
@Table(name = "CAR_RENTAL")
public class JpaCarRental {

    @Id
    @Column(name = "BOOKING_NUMBER")
    private Integer bookingNumber;

    @Column(name = "DRIVER_FIRSTNAME")
    private String driverFirstname;

    @Column(name = "DRIVER_LASTNAME")
    private String driverLastname;

    @Column(name = "DRIVER_LICENCE")
    private String drivingLicenceNumber;

    @Column(name = "PICKUP_TIME")
    private LocalDateTime pickupTime;

    @Column(name = "RETURN_TIME")
    private LocalDateTime returnTime;

    @Column(name = "PICKUP_CAR_STATE_ODOMETER")
    private Integer pickupOdometer;

    @Column(name = "PICKUP_CAR_STATE_FUEL")
    private Integer pickupFuel;

    @Column(name = "RETURN_CAR_STATE_ODOMETER")
    private Integer returnOdometer;

    @Column(name = "RETURN_CAR_STATE_FUEL")
    private Integer returnFuel;

    @Transient
    private CarRental carRental;

    public JpaCarRental() {
    }

    public void update(CarRental carRental) {
        this.carRental = carRental;

        this.bookingNumber = this.carRental.getBookingNumber().getValue();
        this.driverFirstname = this.carRental.getDriver().getFirstname();
        this.driverLastname = this.carRental.getDriver().getLastname();
        this.drivingLicenceNumber = this.carRental.getDriver().getDrivingLicenceNumber();

        this.pickupTime = this.carRental.getPickupDateTime();
        this.returnTime = this.carRental.getReturnDateTime();

        this.pickupFuel = this.carRental.getPickupCarState().getFuelLevel().getPercent();
        this.pickupOdometer = this.carRental.getPickupCarState().getOdometer().getValue();

        CarState returnCarState = this.carRental.getReturnCarState();
        if (returnCarState != null) {
            this.returnFuel = returnCarState.getFuelLevel().getPercent();
            this.returnOdometer = returnCarState.getOdometer().getValue();
        }
    }

    public CarRental getDomainObject() {
        if (carRental != null) {
            return carRental;
        }
        BookingNumber bookingNumber = new BookingNumber(this.bookingNumber);
        Driver driver = new Driver(driverFirstname, driverLastname, drivingLicenceNumber);
        CarState pickupCarState = new CarState(FuelLevel.ofPercentage(pickupFuel), new Odometer(pickupOdometer));
        carRental = new CarRental(bookingNumber, driver, pickupCarState);
        carRental.setPickupDateTime(pickupTime);
        carRental.setReturnDateTime(returnTime);
        return carRental;
    }
}
