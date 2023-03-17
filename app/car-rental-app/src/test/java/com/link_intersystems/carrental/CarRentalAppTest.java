package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.ioc.declaration.BeanDeclarationRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class CarRentalAppTest {

    @Test
    void applicationContext(@TempDir File tempDir) {
        CarRentalApp carRentalApp = new CarRentalApp() {

            @Override
            BeanDeclarationRegistry getBeanDeclarationRegistry() {
                BeanDeclarationRegistry beanDeclarationRegistry = super.getBeanDeclarationRegistry();
                beanDeclarationRegistry.setBeanAmbiguityResolver((clazz, name, options) -> {
                    return options.stream().filter(bd -> !bd.getBeanType().getName().toLowerCase().contains("dbunit")).findFirst().orElse(null);
                });
                return beanDeclarationRegistry;
            }

            @Override
            protected void openFrame(CarRentalMainFrame mainFrame) {
            }
        };


        carRentalApp.run(new String[]{"-db", tempDir.getAbsolutePath()});
    }
}