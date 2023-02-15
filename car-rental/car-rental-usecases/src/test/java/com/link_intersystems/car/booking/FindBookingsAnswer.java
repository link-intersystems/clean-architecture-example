package com.link_intersystems.car.booking;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

class FindBookingsAnswer implements Answer<CarBooking> {
    @Override
    public CarBooking answer(InvocationOnMock invocationOnMock) throws Throwable {
        return null;
    }
}
