package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcPickupCarComponent extends PickupCarComponent {

    private final JdbcTemplate managementJdbcTemplate;

    public JdbcPickupCarComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate, DomainEventPublisher eventPublisher) {
        super(aopConfig, eventPublisher);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected PickupCarRepository getRepository() {
        return new JdbcPickupCarRepository(managementJdbcTemplate);
    }
}
