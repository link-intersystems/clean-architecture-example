package com.link_intersystems.carrental.management.pickup.list;

import com.link_intersystems.carrental.management.rental.CarRental;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public class MockListPickupCarRepository implements ListPickupCarRepository {

    private List<CarRental> findAllCarRentals = new ArrayList<>();

    @Override
    public List<CarRental> findAll() {
        return new ArrayList<>(findAllCarRentals);
    }

    public void addCarRental(CarRental carRental) {
        findAllCarRentals.add(requireNonNull(carRental));
    }

}
