package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.rental.RentalCar;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "RentalCar")
@Table(name = "RENTAL_CAR")
public class JpaRentalCar {

    @Id
    @Column(name = "CARID")
    private String carIdValue;

    @Column(name = "RATE_PER_DAY")
    private BigDecimal ratePerDay;

    @Transient
    private RentalCar rentalCar;

    public RentalCar getDomainObject() {
        if (rentalCar == null) {
            Amount rentalRate = new Amount(ratePerDay);
            rentalCar = new RentalCar(new CarId(new VIN(carIdValue)), rentalRate);
        }

        return rentalCar;
    }
}
