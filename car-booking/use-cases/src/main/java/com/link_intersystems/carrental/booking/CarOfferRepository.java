package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.CarsById;
import com.link_intersystems.carrental.VehicleType;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.time.Period;

import java.util.List;

interface CarOfferRepository {

    List<RentalCar> findRentalCars(CarCriteria criteria);

    CarBookinsByCar findCarBookings(List<CarId> carIds, Period desiredPeriod);

    CarsById findCars(List<CarId> carIds);

    public static class CarCriteria {

        private VehicleType vehicleType;

        public VehicleType getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
        }
    }
}
