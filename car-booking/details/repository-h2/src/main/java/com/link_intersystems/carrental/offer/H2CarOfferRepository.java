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
import com.link_intersystems.money.Amount;
import com.link_intersystems.time.Period;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class H2CarOfferRepository implements CarOfferRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2CarOfferRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RentalCar> findRentalCars(CarCriteria criteria) {
        List<Car> cars = findCars();
        List<Car> filteredCars = applyCriteria(cars, criteria);
        List<String> filteredVins = filteredCars.stream().map(Car::getId).map(CarId::getValue).collect(Collectors.toList());

        StringBuilder queryBuilder = new StringBuilder("select * from rental_car where carid in (");
        String[] chars = new String[filteredVins.size()];
        Arrays.fill(chars, "?");
        queryBuilder.append(String.join(", ", chars));
        queryBuilder.append(")");

        return jdbcTemplate.query(queryBuilder.toString(), new RowMapper<RentalCar>() {
            @Override
            public RentalCar mapRow(ResultSet rs, int rowNum) throws SQLException {
                String carid = rs.getString("CARID");

                RentalRate rentalRate = new RentalRate(new Amount(rs.getBigDecimal("RATE_PER_DAY")));
                return new RentalCar(new CarId(new VIN(carid)), rentalRate);
            }
        }, filteredVins.toArray());
    }

    private List<Car> applyCriteria(List<Car> cars, CarCriteria carCriteria) {
        List<CarId> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());
        CarsById carsById = findCars(carIds);
        VehicleType vehicleTypeCriterion = carCriteria.getVehicleType();

        Predicate<Car> vehicleTypePredicate = vehicleTypeCriterion == null ? f -> true : fr -> vehicleTypeCriterion.equals(carsById.getCar(fr.getId()).getVehicleType());

        return cars.stream().filter(vehicleTypePredicate).collect(Collectors.toList());
    }

    private List<Car> findCars() {
        return jdbcTemplate.query("select * from car", new RowMapper<Car>() {
            @Override
            public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
                CarId id = new CarId(new VIN(rs.getString("VIN")));
                String name = rs.getString("NAME");
                VehicleType vehicleType = VehicleType.valueOf(rs.getString("VEHICLE_TYPE"));
                return new Car(id, name, vehicleType);
            }
        });
    }

    @Override
    public CarBookinsByCar findCarBookings(List<CarId> carIds, Period desiredPeriod) {
        StringBuilder queryBuilder = new StringBuilder("select * from car_booking where carid in (");
        String[] chars = new String[carIds.size()];
        Arrays.fill(chars, "?");
        queryBuilder.append(String.join(", ", chars));
        queryBuilder.append(")");

        List<CarBooking> carBookings = jdbcTemplate.query(queryBuilder.toString(), new RowMapper<CarBooking>() {
            @Override
            public CarBooking mapRow(ResultSet rs, int rowNum) throws SQLException {
                CustomerId customerId = new CustomerId(rs.getInt("CUSTOMER_ID"));
                CarId carId = new CarId(new VIN(rs.getString("CARID")));

                Timestamp pickupDateTime = rs.getTimestamp("PICKUP_DATETIME");
                Timestamp returnDateTime = rs.getTimestamp("RETURN_DATETIME");

                Period bookingPeriod = new Period(pickupDateTime.toLocalDateTime(), returnDateTime.toLocalDateTime());
                return new CarBooking(customerId, carId, bookingPeriod);
            }
        }, carIds.stream().map(CarId::getValue).toArray());


        return new CarBookinsByCar(carBookings.stream().filter(cb -> cb.getBookingPeriod().overlaps(desiredPeriod)).collect(Collectors.toList()));
    }

    @Override
    public CarsById findCars(List<CarId> carIds) {
        List<Car> cars = findCars();
        return new CarsById(cars.stream().filter(c -> carIds.contains(c.getId())).collect(Collectors.toList()));
    }
}
