package com.link_intersystems.car.offers;

import com.link_intersystems.car.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FindCarOffersAnswer implements Answer<List<Car>> {

    private CarFixture carFixture;

    public FindCarOffersAnswer(CarFixture carFixture) {
        this.carFixture = carFixture;
    }

    @Override
    public List<Car> answer(InvocationOnMock invocationOnMock) throws Throwable {
        CarCriteria carCriteria = invocationOnMock.getArgument(0, CarCriteria.class);

        List<Car> cars = carFixture.getFilms();
        List<Car> selectedCars = applyCriteria(cars, carCriteria);

        return selectedCars;
    }

    private List<Car> applyCriteria(List<Car> cars, CarCriteria carCriteria) {
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<Car> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(fr.getCategory());

        return cars.stream()
                .filter(vehicleTypePredicate)
                .collect(Collectors.toList());
    }
}
