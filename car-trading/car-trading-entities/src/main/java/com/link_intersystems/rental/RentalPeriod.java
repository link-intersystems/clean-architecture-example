package com.link_intersystems.rental;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class RentalPeriod {

    private LocalDateTime pickUpDate;
    private LocalDateTime returnDate;

    public RentalPeriod(LocalDateTime pickUpDate, LocalDateTime returnDate) {
        this.pickUpDate = requireNonNull(pickUpDate);
        this.returnDate = requireNonNull(returnDate);
    }

    public LocalDateTime getPickUpDate() {
        return pickUpDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public boolean overlaps(RentalPeriod rentalPeriod) {
        if (includes(rentalPeriod)) return true;

        return rentalPeriod.includes(this);
    }

    private boolean includes(RentalPeriod rentalPeriod) {
        LocalDateTime thatPickUpDate = rentalPeriod.getPickUpDate();

        if (pickUpDate.isBefore(thatPickUpDate) && returnDate.isAfter(thatPickUpDate)) {
            return true;
        }

        LocalDateTime thatReturnDate = rentalPeriod.getReturnDate();
        return pickUpDate.isBefore(thatReturnDate) && returnDate.isAfter(thatReturnDate);
    }

    @Override
    public String toString() {
        return "RentalPeriod{" +
                "pickUpDate=" + pickUpDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
