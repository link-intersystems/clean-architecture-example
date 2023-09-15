package com.link_intersystems.carrental.management.rental;

import java.util.List;

interface ListPickupCarRepository {
    List<CarRental> findAll();
}
