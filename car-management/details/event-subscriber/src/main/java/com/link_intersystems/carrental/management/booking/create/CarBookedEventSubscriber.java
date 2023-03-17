package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.booking.CarBookedEvent;

public class CarBookedEventSubscriber implements DomainEventSubscriber {

    private CreateCarBookingUseCase createCarBookingUseCase;

    public CarBookedEventSubscriber(CreateCarBookingUseCase createCarBookingUseCase) {
        this.createCarBookingUseCase = createCarBookingUseCase;
    }

    @Override
    public boolean subscribedTo(Class<?> domainEventType) {
        return CarBookedEvent.class.isAssignableFrom(domainEventType);
    }

    @Override
    public void handleEvent(Object domainEvent) {
        CarBookedEvent carBookedEvent = (CarBookedEvent) domainEvent;

        CreateCarBookingRequestModel requestModel = new CreateCarBookingRequestModel();
        requestModel.setBookingNumber(carBookedEvent.getBookingNumber());
        requestModel.setVin(carBookedEvent.getVin());
        createCarBookingUseCase.createCarBooking(requestModel);
    }
}
