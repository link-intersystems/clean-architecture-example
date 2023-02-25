package com.link_intersystems.carrental.offer;

public class CarSpecModel {
    private int seats;
    private int doors;
    private double consumption;
    private String energyType;

    public int getSeats() {
        return seats;
    }

    void setSeats(int seats) {
        this.seats = seats;
    }

    public int getDoors() {
        return doors;
    }

    void setDoors(int doors) {
        this.doors = doors;
    }

    void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getConsumption() {
        return consumption;
    }

    public String getEnergyType() {
        return energyType;
    }

    void setEnergyType(String energyType) {
        this.energyType = energyType;
    }
}
