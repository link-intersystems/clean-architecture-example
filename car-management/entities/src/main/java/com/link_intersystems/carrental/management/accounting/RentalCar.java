package com.link_intersystems.carrental.management.accounting;

import com.link_intersystems.carrental.money.Amount;

public class RentalCar {

    private String name;
    private Amount dailyRate;

    public RentalCar(String name, Amount dailyRate) {
        this.name = name;
        this.dailyRate = dailyRate;
    }

    public String getName() {
        return name;
    }

    public Amount getDailyRate() {

        return dailyRate;
    }
}
