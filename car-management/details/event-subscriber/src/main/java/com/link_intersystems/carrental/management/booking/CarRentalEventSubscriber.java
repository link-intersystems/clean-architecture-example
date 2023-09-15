package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.management.rental.pickup.CarRentalDomainEvent;

public class CarRentalEventSubscriber implements DomainEventSubscriber {

    private UpdateCarBookingRentalUseCase useCase;

    public CarRentalEventSubscriber(UpdateCarBookingRentalUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public boolean subscribedTo(Class<?> domainEventType) {
        return CarRentalDomainEvent.class.equals(domainEventType);
    }

    @Override
    public void handleEvent(Object domainEvent) {
        CarRentalDomainEvent carRentalDomainEvent = (CarRentalDomainEvent) domainEvent;

        int eventType = carRentalDomainEvent.getEventType();
        if (eventType == CarRentalDomainEvent.PICKED_UP) {
            useCase.updateCarRental(new UpdateCarBookingRentalRequestModel(carRentalDomainEvent.getBookingNumber(), RentalState.PICKED_UP));
        }
    }
}
