package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.app.context.BeanSelector;
import com.link_intersystems.carrental.management.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import org.springframework.jdbc.core.JdbcTemplate;

public class PickupConfig {

    public PickupCarController getPickupCarController(MessageDialog messageDialog, PickupCarUseCase pickupCarUseCase) {
        return new PickupCarController(pickupCarUseCase, messageDialog);
    }

    public PickupCarUseCase getPickupCarUseCase(PickupCarRepository pickupCarRepository) {
        return new PickupCarInteractor(pickupCarRepository);
    }

    public PickupCarRepository getPickupCarRepository(BeanSelector<JdbcTemplate> jdbcTemplateBeanSelector) {
        return new H2PickupCarRepository(jdbcTemplateBeanSelector.select("getManagementJdbcTemplate"));
    }
}
