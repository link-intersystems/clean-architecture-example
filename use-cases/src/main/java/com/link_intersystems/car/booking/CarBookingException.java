package com.link_intersystems.car.booking;

public class CarBookingException extends Exception {

    public CarBookingException() {
    }

    public CarBookingException(String message) {
        super(message);
    }

    public CarBookingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarBookingException(Throwable cause) {
        super(cause);
    }

    public CarBookingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
