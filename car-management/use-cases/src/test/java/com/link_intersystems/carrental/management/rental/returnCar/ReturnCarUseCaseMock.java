package com.link_intersystems.carrental.management.rental.returnCar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnCarUseCaseMock implements ReturnCarUseCase {

    private List<ReturnCarRequestModel> returnCarRequestModels = new ArrayList<>();

    @Override
    public void returnCar(ReturnCarRequestModel returnCarRequestModel) {
        returnCarRequestModels.add(returnCarRequestModel);

    }

    public ReturnCarUseCase verifyReturnCar(int times) {
        return returnCarRequestModel -> {
            int frequency = Collections.frequency(returnCarRequestModels, returnCarRequestModel);
            assertEquals(times, frequency, "returnCar invoked");
        };
    }
}