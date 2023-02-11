package com.link_intersystems.car.offers;

import com.link_intersystems.car.VehicleType;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FindVehicleTypeAnswer implements Answer<VehicleType> {
    @Override
    public VehicleType answer(InvocationOnMock invocationOnMock) throws Throwable {
        String vehicleTypeName = invocationOnMock.getArgument(0, String.class);
        return VehicleType.valueOf(vehicleTypeName);
    }
}
