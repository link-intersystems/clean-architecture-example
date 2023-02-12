package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarId;
import com.link_intersystems.rental.RentalRate;
import com.link_intersystems.rental.RentalRateFixture;
import com.link_intersystems.rental.RentalRates;
import com.link_intersystems.rental.RentalRatesByCar;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.stream.Collectors;

public class FindRentalRatesAnswer implements Answer<RentalRatesByCar> {

    private final RentalRateFixture rentalRateFixture;

    public FindRentalRatesAnswer(RentalRateFixture rentalRateFixture) {
        this.rentalRateFixture = rentalRateFixture;
    }

    @Override
    public RentalRatesByCar answer(InvocationOnMock invocationOnMock) throws Throwable {
        List<CarId> carIds = invocationOnMock.getArgument(0, List.class);

        List<RentalRate> filteredCarRentals = rentalRateFixture.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .collect(Collectors.toList());

        return new RentalRates(filteredCarRentals).groupByCar();
    }

}