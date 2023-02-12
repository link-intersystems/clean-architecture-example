package com.link_intersystems.car;

import java.lang.reflect.Field;

public class CarWhitebox {

    public void setName(Car car, String title) {
        setField(car, "name", title);
    }

    public void setId(Car car, int id) {
        setField(car, "id", new CarId(id));
    }

    public void setVehicleType(Car car, VehicleType vehicleType) {
        setField(car, "vehicleType", vehicleType);
    }

    public void setSpecs(Car car, Specs specs) {
        setField(car, "specs", specs);
    }

    private void setField(Car car, String fieldName, Object value) {
        try {
            Field field = Car.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(car, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
