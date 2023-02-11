package com.link_intersystems.car;

import java.lang.reflect.Field;

public class CarWhitebox {

    public void setName(Car car, String title) {
        setField(car, "name", title);
    }

    public void setId(Car car, int id) {
        setField(car, "id", id);
    }

    public void setVehicleType(Car car, VehicleType vehicleType) {
        setField(car, "vehicleType", vehicleType);
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
