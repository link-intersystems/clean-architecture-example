package com.link_intersystems.carrental;

import com.link_intersystems.app.context.ApplicationContext;
import com.link_intersystems.app.context.BeanDefinitionRegitry;
import com.link_intersystems.carrental.ui.MainFrame;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        BeanDefinitionRegitry beanDefinitionRegitry = new BeanDefinitionRegitry();
        ApplicationContext applicationContext = new ApplicationContext(beanDefinitionRegitry);
        MainFrame mainFrame = applicationContext.getBean(MainFrame.class);
        openFrame(mainFrame);
    }

    protected void openFrame(MainFrame mainFrame) {
        mainFrame.show();
    }

}
