package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcManagementRentalComponent extends ManagementRentalComponent {


    private JdbcTemplate managementJdbcTemplate;

    public JdbcManagementRentalComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate, DomainEventPublisher eventPublisher) {
        super(aopConfig, eventPublisher);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected GetPickupCarRepository getGetPickupCarRepository() {
        return new JdbcGetPickupCarRepository(managementJdbcTemplate);
    }

    @Override
    protected ListPickupCarRepository getListPickupCarRepository() {
        return new JdbcListPickupCarRepository(managementJdbcTemplate);
    }

    @Override
    protected PickupCarRepository getPickupCarRepository() {
        return new JdbcPickupCarRepository(managementJdbcTemplate);
    }

    @Override
    protected ReturnCarRepository getReturnCarRepository() {
        return new JdbcReturnCarRepository(managementJdbcTemplate);
    }
}
