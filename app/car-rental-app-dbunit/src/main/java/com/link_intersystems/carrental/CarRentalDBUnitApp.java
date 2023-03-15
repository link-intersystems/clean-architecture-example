package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.DBUnitMainFrame;
import com.link_intersystems.ioc.context.ApplicationContext;

public class CarRentalDBUnitApp {

    public static void main(String[] args) {
        CarRentalDBUnitApp carRentalDBUnitApp = new CarRentalDBUnitApp();
        carRentalDBUnitApp.run(args);
    }

    void run(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext();
        DBUnitMainFrame DBUnitMainFrame = applicationContext.getBean(DBUnitMainFrame.class);
        openFrame(DBUnitMainFrame);
    }

    protected void openFrame(DBUnitMainFrame DBUnitMainFrame) {
        DBUnitMainFrame.show();
    }

}
