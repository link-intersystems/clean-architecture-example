package com.link_intersystems.carrental;

import com.link_intersystems.ioc.ApplicationContext;
import com.link_intersystems.ioc.BeanDefinitionRegitry;
import com.link_intersystems.carrental.ui.DBUnitMainFrame;

public class CarRentalDBUnitApp {

    public static void main(String[] args) {
        CarRentalDBUnitApp carRentalDBUnitApp = new CarRentalDBUnitApp();
        carRentalDBUnitApp.run(args);
    }

    void run(String[] args) {
        BeanDefinitionRegitry beanDefinitionRegitry = new BeanDefinitionRegitry();
        ApplicationContext applicationContext = new ApplicationContext(beanDefinitionRegitry);
        DBUnitMainFrame DBUnitMainFrame = applicationContext.getBean(DBUnitMainFrame.class);
        openFrame(DBUnitMainFrame);
    }

    protected void openFrame(DBUnitMainFrame DBUnitMainFrame) {
        DBUnitMainFrame.show();
    }

}
