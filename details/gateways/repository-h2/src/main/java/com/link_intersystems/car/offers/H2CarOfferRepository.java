package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VIN;
import com.link_intersystems.car.VehicleType;
import com.link_intersystems.car.booking.CarBooking;
import com.link_intersystems.car.booking.CarBookinsByCar;
import com.link_intersystems.car.rental.RentalCar;
import com.link_intersystems.car.rental.RentalRate;
import com.link_intersystems.money.Amount;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.sql.io.InputStreamScriptResource;
import com.link_intersystems.sql.io.SqlScript;
import com.link_intersystems.time.Period;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class H2CarOfferRepository implements CarOffersRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2CarOfferRepository() {
        StringBuilder urlBuilder = new StringBuilder("jdbc:h2:file:./car-rental");
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(urlBuilder.toString());
        jdbcDataSource.setUser("");
        jdbcDataSource.setPassword("");

        if (!new File("./car-rental.mv.db").exists()) {


            try (Connection connection = jdbcDataSource.getConnection()) {
                InputStream resourceAsStream = H2CarOfferRepository.class.getResourceAsStream("init.sql");
                SqlScript sqlScript = new SqlScript(new InputStreamScriptResource(resourceAsStream));
                sqlScript.execute(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        jdbcTemplate = new JdbcTemplate(jdbcDataSource);
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
    public CarBookinsByCar findCarBookins(List<CarId> carIds, Period desiredPeriod) {
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
