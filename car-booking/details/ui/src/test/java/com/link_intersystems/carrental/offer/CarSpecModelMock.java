package com.link_intersystems.carrental.offer;

public class CarSpecModelMock extends CarSpecModel{

    @Override
    public void setSeats(int seats) {
        super.setSeats(seats);
    }

    @Override
    public void setDoors(int doors) {
        super.setDoors(doors);
    }

    @Override
    public void setConsumption(double consumption) {
        super.setConsumption(consumption);
    }

    @Override
    public void setEnergyType(String energyType) {
        super.setEnergyType(energyType);
    }
}
