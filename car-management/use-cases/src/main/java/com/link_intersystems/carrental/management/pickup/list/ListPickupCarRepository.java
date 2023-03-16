package com.link_intersystems.carrental.management.pickup.list;

import com.link_intersystems.carrental.management.pickup.CarPickup;

import java.util.List;

public interface ListPickupCarRepository {
    List<CarPickup> findAll();
}
