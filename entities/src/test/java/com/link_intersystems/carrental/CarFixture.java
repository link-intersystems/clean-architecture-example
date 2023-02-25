package com.link_intersystems.carrental;

import com.link_intersystems.EntityFixture;

import java.util.List;

public class CarFixture extends EntityFixture<Car> {

    private Car mercedesBenzE200;
    private Car audiA6;
    private Car smartFortwo;
    private Car volvoXC90;
    private Car fiat500;
    private Car bmw530;

    @Override
    protected void init(List<Car> entities) {
        entities.add(getAudiA6());
        entities.add(getBmw530());
        entities.add(getVolvoXC90());
        entities.add(getFiat500());
        entities.add(getSmartFortwo());
        entities.add(getMercedesBenzE200());
    }

    private Car createSmartFortwo() {
        Car car = new Car(new CarId(new VIN("WMEEJ8AA3FK792135")), "Smart Fortwo", VehicleType.MICRO);
        Specs specs = new Specs(new Seats(2), new Doors(2), new Consumption(FuelType.PETROL, 5.0));
        car.setSpecs(specs);
        return car;
    }

    private Car createFiat500() {
        Car car = new Car(new CarId(new VIN("3C3CFFBR3CTR12014")), "Fiat 500", VehicleType.MICRO);
        Specs specs = new Specs(new Seats(5), new Doors(3), new Consumption(FuelType.PETROL, 4.6));
        car.setSpecs(specs);
        return car;
    }

    private Car createVolvoXC90() {
        Car car = new Car(new CarId(new VIN("YV4952CYXE1702329")), "Volvo XC90", VehicleType.SUV);
        Specs specs = new Specs(new Seats(5), new Doors(4), new Consumption(FuelType.PETROL, 11.1));
        car.setSpecs(specs);
        return car;
    }

    private Car createBMW530() {
        Car car = new Car(new CarId(new VIN("WBAFR1C54BCC47391")), "BMW 530", VehicleType.SEDAN);
        Specs specs = new Specs(new Seats(5), new Doors(4), new Consumption(FuelType.PETROL, 5.4));
        car.setSpecs(specs);
        return car;
    }

    private Car createAudiA6() {
        Car car = new Car(new CarId(new VIN("WAUDV74F98N394362")), "Audi A6", VehicleType.SEDAN);
        Specs specs = new Specs(new Seats(5), new Doors(4), new Consumption(FuelType.PETROL, 8.0));
        car.setSpecs(specs);
        return car;
    }


    private Car createMercedesBenzE200() {
        Car car = new Car(new CarId(new VIN("WDDHF5GB7CA482637")), "Mercedes Benz E 200", VehicleType.SEDAN);
        Specs specs = new Specs(new Seats(5), new Doors(4), new Consumption(FuelType.PETROL, 8.1));
        car.setSpecs(specs);
        return car;
    }

    public Car getMercedesBenzE200() {
        if (mercedesBenzE200 == null) {
            mercedesBenzE200 = createMercedesBenzE200();
        }
        return mercedesBenzE200;
    }

    public Car getAudiA6() {
        if (audiA6 == null) {
            audiA6 = createAudiA6();
        }
        return audiA6;
    }

    public Car getBmw530() {
        if (bmw530 == null) {
            bmw530 = createBMW530();
        }
        return bmw530;
    }

    public Car getFiat500() {
        if (fiat500 == null) {
            fiat500 = createFiat500();
        }
        return fiat500;
    }

    public Car getVolvoXC90() {
        if (volvoXC90 == null) {
            volvoXC90 = createVolvoXC90();
        }
        return volvoXC90;
    }

    public Car getSmartFortwo() {
        if (smartFortwo == null) {
            smartFortwo = createSmartFortwo();
        }
        return smartFortwo;
    }
}
