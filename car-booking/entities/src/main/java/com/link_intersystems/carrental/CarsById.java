package com.link_intersystems.carrental;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarsById extends AbstractMap<CarId, Car> {

    private Map<CarId, Car> carIdCarMap;

    public CarsById(List<Car> cars) {
        carIdCarMap = cars.stream().collect(Collectors.toMap(Car::getId, Function.identity()));
    }

    @Override
    public Set<Entry<CarId, Car>> entrySet() {
        return carIdCarMap.entrySet();
    }

    public Car getCar(CarId carId) {
        return get(carId);
    }
}
