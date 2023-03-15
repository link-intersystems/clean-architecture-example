package com.link_intersystems.carrental;

import com.link_intersystems.app.context.ApplicationContext;
import com.link_intersystems.app.context.BeanDeclaration;
import com.link_intersystems.app.context.BeanDefinition;
import com.link_intersystems.app.context.BeanDefinitionRegitry;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;

import java.net.URL;
import java.util.function.Predicate;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        BeanDefinitionRegitry beanDefinitionRegitry = new BeanDefinitionRegitry();

        Predicate<BeanDefinition> excludeBeanDefinitions = getBeanDefinitionPredicate(args);
        beanDefinitionRegitry.setBeanDefinitionExcludeFilter(excludeBeanDefinitions);
        ApplicationContext applicationContext = new ApplicationContext(beanDefinitionRegitry);
        CarRentalMainFrame mainFrame = applicationContext.getBean(CarRentalMainFrame.class);
        openFrame(mainFrame);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

    private Predicate<BeanDefinition> getBeanDefinitionPredicate(String[] args) {
        String repository = getRepository(args);

        Predicate<BeanDefinition> excludeBeanDefinitions = bd -> {
            BeanDeclaration beanDeclaration = bd.getBeanDeclaration();
            URL resource = beanDeclaration.getResource();
            String path = resource.getPath();

            if (path.contains("/repository")) {
                return !path.contains("/repository-" + repository);
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
