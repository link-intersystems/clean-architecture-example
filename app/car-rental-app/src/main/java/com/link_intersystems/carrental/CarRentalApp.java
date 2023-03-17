package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.ioc.context.ApplicationContext;
import com.link_intersystems.ioc.declaration.BeanDeclarationRegistry;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        BeanDeclarationRegistry beanDeclarationRegistry = getBeanDeclarationRegistry();
        ApplicationContext applicationContext = new ApplicationContext(beanDeclarationRegistry);
        CarRentalMainFrame mainFrame = applicationContext.getBean(CarRentalMainFrame.class);
        openFrame(mainFrame);
    }

    BeanDeclarationRegistry getBeanDeclarationRegistry() {
        return new BeanDeclarationRegistry();
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
