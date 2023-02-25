package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.VehicleType;
import com.link_intersystems.carrental.booking.CarBooking;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.rental.RentalRate;
import com.link_intersystems.dbunit.stream.producer.support.DefaultDataSetProducerSupport;
import com.link_intersystems.dbunit.table.Row;
import com.link_intersystems.money.Amount;
import com.link_intersystems.time.Period;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.stream.IDataSetProducer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DBUnitCarOfferRepository implements CarOfferRepository {

    private final DomainEntityDataSetConsumer domainEntityDataSetConsumer;

    public DBUnitCarOfferRepository() {
        domainEntityDataSetConsumer = new DomainEntityDataSetConsumer();
        domainEntityDataSetConsumer.addDomainEntityMapper("rentalcar", this::mapRentalCar);
        domainEntityDataSetConsumer.addDomainEntityMapper("car", this::mapCar);
        domainEntityDataSetConsumer.addDomainEntityMapper("carbooking", this::mapCarBooking);

        loadDataSet("/com/link_intersystems/carrental/Car.xml");
        loadDataSet("/com/link_intersystems/carrental/offer/CarBooking.xml");
        loadDataSet("/com/link_intersystems/carrental/rental/RentalCar.xml");
    }

    private void loadDataSet(String dataSet) {
        DefaultDataSetProducerSupport producerSupport = new DefaultDataSetProducerSupport();
        producerSupport.setFlatXmlProducer(DBUnitCarOfferRepository.class.getResourceAsStream(dataSet));
        IDataSetProducer dataSetProducer = producerSupport.getDataSetProducer();
        try {
            dataSetProducer.setConsumer(domainEntityDataSetConsumer);
            dataSetProducer.produce();
        } catch (DataSetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RentalCar> findRentalCars(CarCriteria criteria) {
        List<RentalCar> rentalcar = domainEntityDataSetConsumer.getDomainEntities("rentalcar");
        return applyCriteria(rentalcar, criteria);
    }

    private List<RentalCar> applyCriteria(List<RentalCar> rentalCars, CarCriteria carCriteria) {
        List<CarId> carIds = rentalCars.stream().map(RentalCar::getCarId).collect(Collectors.toList());
        CarsById carsById = findCars(carIds);
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<RentalCar> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(carsById.getCar(fr.getCarId()).getVehicleType());

        return rentalCars.stream()
                .filter(vehicleTypePredicate)
                .collect(Collectors.toList());
    }

    @Override
    public CarBookinsByCar findCarBookings(List<CarId> carIds, Period desiredPeriod) {
        List<CarBooking> carBookingList = domainEntityDataSetConsumer.getDomainEntities("carbooking");
        List<CarBooking> carBookings = carBookingList.stream()
                .filter(cr -> carIds.contains(cr.getCarId()))
                .filter(cr -> cr.getBookingPeriod().overlaps(desiredPeriod))
                .collect(Collectors.toList());
        return new CarBookinsByCar(carBookings);
    }

    @Override
    public CarsById findCars(List<CarId> carIds) {
        List<Car> cars = domainEntityDataSetConsumer.getDomainEntities("car");
        List<Car> filteredCars = cars.stream().filter(c -> carIds.contains(c.getId())).collect(Collectors.toList());
        return new CarsById(filteredCars);
    }

    private RentalCar mapRentalCar(ITableMetaData tabelMetaData, Object[] values) {
        try {
            Row row = new Row(tabelMetaData, values);
            String rentalRateAmountValue = (String) row.getValue("rentalRateAmount");
            RentalRate rentalRate = new RentalRate(new Amount(rentalRateAmountValue));
            CarId carId = new CarId(new VIN((String) row.getValue("carid")));
            RentalCar rentalCar = new RentalCar(carId, rentalRate);
            return rentalCar;
        } catch (DataSetException e) {
            return null;
        }
    }

    private Car mapCar(ITableMetaData tabelMetaData, Object[] values) {
        try {
            Row row = new Row(tabelMetaData, values);

            String vin = (String) row.getValue("vin");
            String name = (String) row.getValue("name");
            VehicleType vehicleType = VehicleType.valueOf((String) row.getValue("vehicleType"));

            return new Car(new CarId(new VIN(vin)), name, vehicleType);
        } catch (DataSetException e) {
            return null;
        }
    }

    private CarBooking mapCarBooking(ITableMetaData tabelMetaData, Object[] values) {
        try {
            Row row = new Row(tabelMetaData, values);
            CustomerId customerId = new CustomerId(Integer.valueOf((String) row.getValue("customerId")));
            CarId carId = new CarId(new VIN((String) row.getValue("carid")));
            String pickupDateTime = (String) row.getValue("pickupDateTime");
            String returnDateTime = (String) row.getValue("returnDateTime");
            Period period = new Period(LocalDateTime.parse(pickupDateTime), LocalDateTime.parse(returnDateTime));
            return new CarBooking(customerId, carId, period);
        } catch (DataSetException e) {
            return null;
        }
    }
}

