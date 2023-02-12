package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RentalRates extends AbstractList<RentalRate> {

    private List<RentalRate> rentalRateList = new ArrayList<>();

    public RentalRates() {
    }

    public RentalRates(List<RentalRate> rentalRateList) {
        this.rentalRateList.addAll(rentalRateList);
    }

    public RentalRatesByCar groupByCar() {
        Map<CarId, List<RentalRate>> rentalsByCar = rentalRateList.stream().collect(Collectors.groupingBy(RentalRate::getCarId));
        Map<CarId, RentalRates> collect = rentalsByCar.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> new RentalRates(e.getValue())));
        return new RentalRatesByCar(collect);
    }


    public RentalRate getActiveForPeriod(Period period) {
        for (RentalRate rentalRate : rentalRateList) {
            if(rentalRate.isActive(period)){
                return rentalRate;
            }
        }
        return null;
    }

    @Override
    public RentalRate get(int index) {
        return rentalRateList.get(index);
    }

    @Override
    public int size() {
        return rentalRateList.size();
    }

}
