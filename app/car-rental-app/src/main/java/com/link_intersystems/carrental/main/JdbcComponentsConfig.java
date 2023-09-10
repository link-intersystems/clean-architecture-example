package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.JdbcBookingComponent;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.create.JdbcCreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.JdbcListCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingComponent;
import com.link_intersystems.carrental.management.rental.pickup.JdbcPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.JdbcGetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.JdbcListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.JdbcReturnCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarComponent;
import com.link_intersystems.carrental.offer.CarOfferComponent;
import com.link_intersystems.carrental.offer.JdbcCarOfferComponent;

public class JdbcComponentsConfig implements ComponentsConfig {

    private AOPConfig aopConfig;
    private DataSourceConfig dataSourceConfig;

    public JdbcComponentsConfig(AOPConfig aopConfig, DataSourceConfig dataSourceConfig) {
        this.aopConfig = aopConfig;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public CarOfferComponent getCarOfferComponent() {
        return new JdbcCarOfferComponent(aopConfig, dataSourceConfig.getBookingJdbcTemplate());
    }

    @Override
    public CarBookingComponent getCarBookingComponent() {
        return new JdbcBookingComponent(aopConfig, dataSourceConfig.getBookingJdbcTemplate());
    }

    @Override
    public ListCarBookingComponent getListCarBookingComponent() {
        return new JdbcListCarBookingComponent(aopConfig, dataSourceConfig.getManagementJdbcTemplate());
    }

    @Override
    public PickupCarComponent getPickupCarComponent() {
        return new JdbcPickupCarComponent(aopConfig, dataSourceConfig.getManagementJdbcTemplate());
    }

    @Override
    public ListPickupCarComponent getListPickupCarComponent() {
        return new JdbcListPickupCarComponent(aopConfig, dataSourceConfig.getManagementJdbcTemplate());
    }

    @Override
    public ReturnCarComponent getReturnCarComponent() {
        return new JdbcReturnCarComponent(aopConfig, dataSourceConfig.getManagementJdbcTemplate());
    }

    @Override
    public GetPickupCarComponent getGetPickupCarComponent() {
        return new JdbcGetPickupCarComponent(aopConfig, dataSourceConfig.getManagementJdbcTemplate());
    }

    @Override
    public CreateCarBookingComponent getCreateCarBookingComponent() {
        return new JdbcCreateCarBookingComponent(aopConfig, dataSourceConfig.getManagementJdbcTemplate());
    }
}
