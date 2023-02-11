package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarId;
import com.link_intersystems.rental.CarRental;
import com.link_intersystems.rental.CarRentalFixture;
import com.link_intersystems.rental.CarRentals;
import com.link_intersystems.rental.RentalsByCar;
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
        CarRentals carRentals = carRentalFixture.getCarRentals();
        List<CarRental> filteredCarRentals = carRentals.stream().filter(cr -> carIds.contains(cr.getCarId())).collect(Collectors.toList());
        return new CarRentals(filteredCarRentals).groupByCar();
    }

}