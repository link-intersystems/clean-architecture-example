package com.link_intersystems.carrental;

import com.link_intersystems.carrental.booking.CarBooking;
import com.link_intersystems.carrental.booking.DBUnitCarBookingRepository;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.rental.RentalRate;
import com.link_intersystems.dbunit.stream.producer.support.DefaultDataSetProducerSupport;
import com.link_intersystems.dbunit.table.Row;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.time.Period;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.stream.IDataSetProducer;

import java.time.LocalDateTime;
import java.util.List;

public class DataSetRegistry {

    private final DomainEntityDataSetConsumer domainEntityDataSetConsumer;

    public DataSetRegistry() {
        domainEntityDataSetConsumer = new DomainEntityDataSetConsumer();
        domainEntityDataSetConsumer.addDomainEntityMapper("rentalcar", this::mapRentalCar);
        domainEntityDataSetConsumer.addDomainEntityMapper("car", this::mapCar);
        domainEntityDataSetConsumer.addDomainEntityMapper("carbooking", this::mapCarBooking);
        domainEntityDataSetConsumer.addDomainEntityMapper("customer", this::mapCustomer);

        loadDataSet("/com/link_intersystems/carrental/Car.xml");
        loadDataSet("/com/link_intersystems/carrental/customer/Customer.xml");
        loadDataSet("/com/link_intersystems/carrental/offer/CarBooking.xml");
        loadDataSet("/com/link_intersystems/carrental/rental/RentalCar.xml");
    }

    public <T> List<T> getDomainEntities(String name) {
        return domainEntityDataSetConsumer.getDomainEntities(name);
    }

    private void loadDataSet(String dataSet) {
        DefaultDataSetProducerSupport producerSupport = new DefaultDataSetProducerSupport();
        producerSupport.setFlatXmlProducer(DBUnitCarBookingRepository.class.getResourceAsStream(dataSet));
        IDataSetProducer dataSetProducer = producerSupport.getDataSetProducer();
        try {
            dataSetProducer.setConsumer(domainEntityDataSetConsumer);
            dataSetProducer.produce();
        } catch (DataSetException e) {
            throw new RuntimeException(e);
        }
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

    private Customer mapCustomer(ITableMetaData tabelMetaData, Object[] values) {
        try {
            Row row = new Row(tabelMetaData, values);
            CustomerId customerId = new CustomerId(Integer.valueOf((String) row.getValue("id")));
            String firstname = (String) row.getValue("firstname");
            String lastname = (String) row.getValue("lastname");
            return new Customer(customerId, firstname, lastname);
        } catch (DataSetException e) {
            return null;
        }
    }
}
