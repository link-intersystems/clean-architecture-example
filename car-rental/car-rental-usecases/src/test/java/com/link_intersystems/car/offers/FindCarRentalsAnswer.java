package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarId;
import com.link_intersystems.car.rental.CarRental;
import com.link_intersystems.car.rental.CarRentalFixture;
import com.link_intersystems.car.rental.CarRentals;
import com.link_intersystems.car.rental.RentalsByCar;
import com.link_intersystems.time.Period;
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
        Period period = invocationOnMock.getArgument(1, Period.class);

        List<CarRental> filteredCarRentals = carRentalFixture.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .filter(cr -> cr.getRentalPeriod().overlaps(period))
                .collect(Collectors.toList());
        return new CarRentals(filteredCarRentals).groupByCar();
    }

}