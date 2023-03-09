package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventBusTemplate;
import com.link_intersystems.carrental.DomainEventSubscriber;

import java.util.List;

public class CarBookingUseCaseConfig {

    public CarBookingUseCase getCarBookingUseCase(CarBookingRepository carBookingRepository, List<DomainEventSubscriber> subscibers) {
        CarBookingInteractor carBookingInteractor = new CarBookingInteractor(carBookingRepository);
        DomainEventBusTemplate domainEventBusTemplate = new DomainEventBusTemplate(subscibers);
        return request -> domainEventBusTemplate.execute(() -> carBookingInteractor.bookCar(request));
    }

}
