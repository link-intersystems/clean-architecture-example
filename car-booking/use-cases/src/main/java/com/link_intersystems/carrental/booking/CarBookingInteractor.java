package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.Period;

import java.time.Clock;
import java.time.LocalDateTime;

import static java.util.Objects.*;

class CarBookingInteractor implements CarBookingUseCase {

    private CarBookingRepository repository;
    private DomainEventBus domainEventBus;

    public CarBookingInteractor(CarBookingRepository repository, DomainEventBus domainEventBus) {
        this.repository = requireNonNull(repository);
        this.domainEventBus = requireNonNull(domainEventBus);
    }

    @Override
    public CarBookingResponseModel bookCar(CarBookingRequestModel request) throws CarBookingException {
        Period bookingPeriod = getBookingPeriod(request);

        CarId carId = new CarId(new VIN(request.getCarId()));
        ensureCarAvailable(carId, bookingPeriod);

        CustomerId customerId = new CustomerId(request.getCustomerId());
        if (!repository.isCustomerExistent(customerId)) {
            throw new CarBookingException("customer does not exist");
        }

        CarBooking carBooking = new CarBooking(customerId, carId, bookingPeriod);

        repository.persist(carBooking);

        CarBookingResponseModel responseModel = new CarBookingResponseModel();
        responseModel.setBookingNumber(Integer.toString(carBooking.getBookingNumber().getValue()));

        CarBookedEvent carBookedEvent = new CarBookedEvent(carBooking.getBookingNumber().getValue(), carBooking.getCarId().getValue());
        domainEventBus.publish(carBookedEvent);
        return responseModel;
    }

    private void ensureCarAvailable(CarId carId, Period bookingPeriod) throws CarNotAvailableException {

        CarBooking carBooking = repository.findBooking(carId, bookingPeriod);

        if (carBooking != null) {
            LocalDateTime pickupDateTime = bookingPeriod.getBegin();
            LocalDateTime returnDateTime = bookingPeriod.getEnd();
            throw new CarNotAvailableException(carId.getValue(), pickupDateTime, returnDateTime);
        }
    }

    private Period getBookingPeriod(CarBookingRequestModel request) throws CarBookingException {
        LocalDateTime pickupDateTime = request.getPickUpDateTime();
        LocalDateTime returnDateTime = request.getReturnDateTime();
        Period bookingPeriod = new Period(pickupDateTime, returnDateTime);

        Clock clock = ClockProvider.getClock();
        if (bookingPeriod.isPast(clock)) {
            throw new CarBookingException("bookingPeriod is in the past.");
        }

        return bookingPeriod;
    }
}
