package com.link_intersystems.carrental.booking;

import java.time.LocalDateTime;

public class CarNotAvailableException extends CarBookingException {
    private final String carId;
    private final LocalDateTime pickupDateTime;
    private final LocalDateTime returnDateTime;

    public CarNotAvailableException(String carId, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        this.carId = carId;
        this.pickupDateTime = pickupDateTime;
        this.returnDateTime = returnDateTime;
    }

    public String getCarId() {
        return carId;
    }

    public LocalDateTime getPickUpDateTime() {
        return pickupDateTime;
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
