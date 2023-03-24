package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.ioc.declaration.BeanDeclarationRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class CarRentalAppIoCTest {

    @Test
    void applicationContext(@TempDir File tempDir) {
        CarRentalAppIoC carRentalAppIoC = new CarRentalAppIoC() {

            @Override
            BeanDeclarationRegistry getBeanDeclarationRegistry() {
                BeanDeclarationRegistry beanDeclarationRegistry = super.getBeanDeclarationRegistry();
                beanDeclarationRegistry.setBeanDeclarationExcludeFilter(bd -> bd.getBeanType().getSimpleName().startsWith("DBUnit"));
                return beanDeclarationRegistry;
            }

            @Override
            protected void openFrame(CarRentalMainFrame mainFrame) {
            }
        };


        carRentalAppIoC.run(new String[]{"-db", tempDir.getAbsolutePath()});
    }
}