package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarFixture;
import com.link_intersystems.car.CarId;
import com.link_intersystems.rental.CarRentalFixture;
import com.link_intersystems.rental.RentalPeriod;
import com.link_intersystems.rental.RentalsByCar;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockCarOffersRepository implements CarOffersRepository {

    private CarOffersRepository repository = mock(CarOffersRepository.class);

    public MockCarOffersRepository(CarFixture carFixture, CarRentalFixture carRentalFixture) {
        FindCarOffersAnswer findCarOffersAnswer = new FindCarOffersAnswer(carFixture);
        when(repository.findCars(any(CarCriteria.class))).thenAnswer(findCarOffersAnswer);

        FindCarRentalsAnswer findCarRentalsAnswer = new FindCarRentalsAnswer(carRentalFixture);
        when(repository.findCarRentals(any(List.class), any(RentalPeriod.class))).thenAnswer(findCarRentalsAnswer);
    }

    @Override
    public List<Car> findCars(CarCriteria criteria) {
        return repository.findCars(criteria);
    }

    @Override
    public RentalsByCar findCarRentals(List<CarId> carIds, RentalPeriod desiredRentalPeriod) {
        return repository.findCarRentals(carIds, desiredRentalPeriod);
    }
}
