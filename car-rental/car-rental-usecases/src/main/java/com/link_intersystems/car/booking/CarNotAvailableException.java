package com.link_intersystems.car.booking;

import java.time.LocalDateTime;

public class CarNotAvailableException extends Exception {
    private final int carId;
    private final LocalDateTime pickUpDateTime;
    private final LocalDateTime returnDateTime;

    public CarNotAvailableException(int carId, LocalDateTime pickUpDateTime, LocalDateTime returnDateTime) {
        this.carId = carId;
        this.pickUpDateTime = pickUpDateTime;
        this.returnDateTime = returnDateTime;
    }

    public int getCarId() {
        return carId;
    }

    public LocalDateTime getPickUpDateTime() {
        return pickUpDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Car ");
        sb.append(getCarId());
        sb.append(" is not available from ");
        sb.append(getPickUpDateTime());
        sb.append(" to ");
        sb.append(getReturnDateTime());
        return sb.toString();
    }


}
