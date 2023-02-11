package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarId;
import com.link_intersystems.rental.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.stream.Collectors;

public class FindCarRentalsAnswer implements Answer<RentalsByCar> {

    private final CarRentalFixture carRentalFixture;

    public FindCarRentalsAnswer(CarRentalFixture carRentalFixture) {
        this.carRentalFixture = carRentalFixture;
    }

    @Override
    public RentalsByCar answer(InvocationOnMock invocationOnMock) throws Throwable {
        List<CarId> carIds = invocationOnMock.getArgument(0, List.class);
        RentalPeriod rentalPeriod = invocationOnMock.getArgument(1, RentalPeriod.class);

        CarRentals carRentals = carRentalFixture.getCarRentals();
        List<CarRental> filteredCarRentals = carRentals.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .filter(cr -> cr.getRentalPeriod().overlaps(rentalPeriod))
                .collect(Collectors.toList());
        return new CarRentals(filteredCarRentals).groupByCar();
    }

}