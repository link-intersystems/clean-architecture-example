package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.booking.CarBooking;
import com.link_intersystems.carrental.booking.CarBookinsByCar;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;
import com.link_intersystems.jdbc.JdbcTemplate;
import com.link_intersystems.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class JdbcCarOfferRepository implements CarOfferRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCarOfferRepository(JdbcTemplate carRentalJdbcTemplate) {
        this.jdbcTemplate = carRentalJdbcTemplate;
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

        return jdbcTemplate.query(queryBuilder.toString(), filteredVins.toArray(), new RowMapper<RentalCar>() {
            @Override
            public RentalCar mapRow(ResultSet rs) throws SQLException {
                String carid = rs.getString("CARID");

                Amount rentalRate = new Amount(rs.getBigDecimal("RATE_PER_DAY"));
                return new RentalCar(new CarId(new VIN(carid)), rentalRate);
            }
        });
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
            public Car mapRow(ResultSet rs) throws SQLException {
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

        List<CarBooking> carBookings = jdbcTemplate.query(queryBuilder.toString(), carIds.stream().map(CarId::getValue).toArray(), new RowMapper<CarBooking>() {
            @Override
            public CarBooking mapRow(ResultSet rs) throws SQLException {
                CustomerId customerId = new CustomerId(rs.getInt("CUSTOMER_ID"));
                CarId carId = new CarId(new VIN(rs.getString("CARID")));

                Timestamp pickupDateTime = rs.getTimestamp("PICKUP_DATETIME");
                Timestamp returnDateTime = rs.getTimestamp("RETURN_DATETIME");

                Period bookingPeriod = new Period(pickupDateTime.toLocalDateTime(), returnDateTime.toLocalDateTime());
                return new CarBooking(customerId, carId, bookingPeriod);
            }
        });


        return new CarBookinsByCar(carBookings.stream().filter(cb -> cb.getBookingPeriod().overlaps(desiredPeriod)).collect(Collectors.toList()));
    }

    @Override
    public CarsById findCars(List<CarId> carIds) {
        List<Car> cars = findCars();
        return new CarsById(cars.stream().filter(c -> carIds.contains(c.getId())).collect(Collectors.toList()));
    }
}
