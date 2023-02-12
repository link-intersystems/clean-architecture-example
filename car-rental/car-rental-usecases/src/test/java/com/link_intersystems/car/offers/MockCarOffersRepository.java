package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarFixture;
import com.link_intersystems.car.CarId;
import com.link_intersystems.rental.CarRentalFixture;
import com.link_intersystems.rental.RentalRateFixture;
import com.link_intersystems.rental.RentalRatesByCar;
import com.link_intersystems.rental.RentalsByCar;
import com.link_intersystems.time.Period;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockCarOffersRepository implements CarOffersRepository {

    private CarOffersRepository repository = mock(CarOffersRepository.class);

    public MockCarOffersRepository(CarFixture carFixture, CarRentalFixture carRentalFixture, RentalRateFixture rentalRateFixture) {
        FindCarOffersAnswer findCarOffersAnswer = new FindCarOffersAnswer(carFixture);
        when(repository.findCars(any(CarCriteria.class))).thenAnswer(findCarOffersAnswer);

        FindCarRentalsAnswer findCarRentalsAnswer = new FindCarRentalsAnswer(carRentalFixture);
        when(repository.findCarRentals(any(List.class), any(Period.class))).thenAnswer(findCarRentalsAnswer);


        FindRentalRatesAnswer findRentalRatesAnswer = new FindRentalRatesAnswer(rentalRateFixture);
        when(repository.findRentalRates(any(List.class))).thenAnswer(findRentalRatesAnswer);
    }

    @Override
    public List<Car> findCars(CarCriteria criteria) {
        return repository.findCars(criteria);
    }

    @Override
    public RentalsByCar findCarRentals(List<CarId> carIds, Period desiredPeriod) {
        return repository.findCarRentals(carIds, desiredPeriod);
    }

    @Override
    public RentalRatesByCar findRentalRates(List<CarId> carIds) {
        return repository.findRentalRates(carIds);
    }
}
