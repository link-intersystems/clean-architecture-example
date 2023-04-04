package com.link_intersystems.carrental.management.rental.pickup.list;

import com.link_intersystems.carrental.management.rental.CarRental;

import java.util.List;

interface ListPickupCarRepository {
    List<CarRental> findAll();
}
