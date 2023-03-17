package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.ioc.context.ApplicationContext;
import com.link_intersystems.ioc.declaration.*;
import com.link_intersystems.ioc.declaration.BeanDeclarationLocation;
import com.link_intersystems.ioc.declaration.location.ResourceBeanDeclarationLocation;
import com.link_intersystems.ioc.declaration.locator.BeanConfigSupportBeanDeclarationLocator;

import java.net.URL;
import java.util.function.Predicate;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        BeanDeclarationRegistry beanDeclarationRegistry = getBeanDeclarationRegistry();

        Predicate<BeanDeclaration> excludeBeanDefinitions = getBeanDefinitionPredicate(args);
        beanDeclarationRegistry.setBeanDeclarationExcludeFilter(excludeBeanDefinitions);
        ApplicationContext applicationContext = new ApplicationContext(beanDeclarationRegistry);
        CarRentalMainFrame mainFrame = applicationContext.getBean(CarRentalMainFrame.class);
        openFrame(mainFrame);
    }

    BeanDeclarationRegistry getBeanDeclarationRegistry() {
        BeanConfigSupportBeanDeclarationLocator beanDeclarationLocator = new BeanConfigSupportBeanDeclarationLocator();
        BeanDeclarationRegistry beanDeclarationRegistry = new BeanDeclarationRegistry(beanDeclarationLocator);
        return beanDeclarationRegistry;
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

    private Predicate<BeanDeclaration> getBeanDefinitionPredicate(String[] args) {
        String repository = getRepository(args);

        Predicate<BeanDeclaration> excludeBeanDefinitions = bd -> {
            BeanDeclarationLocation declarationLocation = bd.getLocation();
            if (declarationLocation instanceof ResourceBeanDeclarationLocation) {
                ResourceBeanDeclarationLocation resourceBeanDeclarationLocation = (ResourceBeanDeclarationLocation) declarationLocation;
                URL resource = resourceBeanDeclarationLocation.getUrl();
                String path = resource.getPath();

                if (path.contains("/repository")) {
                    return !path.contains("/repository-" + repository);
                }
            }

            return false;
        };
        return excludeBeanDefinitions;
    }

    private String getRepository(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-r")) {
                int argValueIndex = i + 1;
                if (argValueIndex < args.length) {
                    return args[argValueIndex];
                }

            }
        }
        return "h2";
    }
}
