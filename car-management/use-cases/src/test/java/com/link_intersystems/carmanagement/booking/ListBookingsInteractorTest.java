package com.link_intersystems.carmanagement.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListBookingsInteractorTest {

    private ListBookingsInteractor interactor;
    private ListBookingsRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ListBookingsRepositoryMock();
        interactor = new ListBookingsInteractor(repository);
    }

    @Test
    void listBookings() {
        ListBookingsRequestModel requestModel = new ListBookingsRequestModel();

        ListBookingsResponseModel responseModel = interactor.listBookings(requestModel);

        assertNotNull(responseModel);
    }
}