package com.link_intersystems.car;

public class Specs {

    private Seats seats;
    private Doors doors;
    private Consumption consumption;

    public Specs(Seats seats, Doors doors, Consumption consumption) {
        this.seats = seats;
        this.doors = doors;
        this.consumption = consumption;
    }

    public Seats getSeats() {
        return seats;
    }

    public Doors getDoors() {
        return doors;
    }

    public Consumption getConsumption() {
        return consumption;
    }
}
