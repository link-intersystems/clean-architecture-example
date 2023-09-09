package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.CarsById;
import com.link_intersystems.carrental.VehicleType;
import com.link_intersystems.carrental.booking.CarBooking;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.booking.JpaCarBooking;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class JpaCarOfferRepository implements CarOfferRepository {

    private final EntityManager entityManager;

    public JpaCarOfferRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<RentalCar> findRentalCars(CarCriteria criteria) {
        List<Car> cars = findCars();
        List<Car> filteredCars = applyCriteria(cars, criteria);
        List<String> filteredVins = filteredCars.stream().map(Car::getId).map(CarId::getValue).collect(Collectors.toList());

        TypedQuery<JpaRentalCar> query = entityManager.createQuery("FROM RentalCar rc where rc.carIdValue in (:carIdValues)", JpaRentalCar.class);
        query.setParameter("carIdValues", filteredVins);
        List<JpaRentalCar> jpaRentalCars = query.getResultList();

        return jpaRentalCars.stream().map(JpaRentalCar::getDomainObject).toList();
    }

    private List<Car> applyCriteria(List<Car> cars, CarCriteria carCriteria) {
        List<CarId> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());
        CarsById carsById = findCars(carIds);
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<Car> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(carsById.getCar(fr.getId()).getVehicleType());

        return cars.stream().filter(vehicleTypePredicate).toList();
    }

    private List<Car> findCars() {
        return entityManager.createQuery("from Car", JpaCar.class).getResultList().stream().map(JpaCar::getDomainObject).toList();
    }

    @Override
    public CarBookinsByCar findCarBookings(List<CarId> carIds, Period desiredPeriod) {
        TypedQuery<JpaCarBooking> query = entityManager.createQuery("FROM CarBooking cb where cb.carIdValue in (:carIdValues)", JpaCarBooking.class);
        List<String> carIdValues = carIds.stream().map(CarId::getValue).toList();
        query.setParameter("carIdValues", carIdValues);

        List<JpaCarBooking> jpaCarBookings = query.getResultList();

        List<CarBooking> carBookings = jpaCarBookings.stream().map(JpaCarBooking::getDomainObject).toList();

        return new CarBookinsByCar(carBookings.stream().filter(cb -> cb.getBookingPeriod().overlaps(desiredPeriod)).collect(Collectors.toList()));
    }

    @Override
    public CarsById findCars(List<CarId> carIds) {
        List<Car> cars = findCars();
        return new CarsById(cars.stream().filter(c -> carIds.contains(c.getId())).collect(Collectors.toList()));
    }
}
