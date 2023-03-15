package com.link_intersystems.ioc.declaration;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public class BeanConfigSupportBeanDeclarationLocator implements BeanDeclarationLocator {

    private BeanConfigDetector beanConfigDetector;
    private BeanDeclarationLocator sourceLocator;

    private List<BeanDeclaration> beanDeclarations;

    public BeanConfigSupportBeanDeclarationLocator() {
        this(new MetaInfBeanDeclarationLocator(), defaultBeanConfigDetector());
    }

    private static BeanConfigDetector defaultBeanConfigDetector() {
        return new CompositeBeanConfigDetector(new AnnotationBeanConfigDetector(), new NamePatternBeanConfigDetector());
    }

    public BeanConfigSupportBeanDeclarationLocator(BeanConfigDetector beanConfigDetector) {
        this(new MetaInfBeanDeclarationLocator(), beanConfigDetector);

    }

    public BeanConfigSupportBeanDeclarationLocator(BeanDeclarationLocator sourceLocator) {
        this(sourceLocator, defaultBeanConfigDetector());

    }

    public BeanConfigSupportBeanDeclarationLocator(BeanDeclarationLocator sourceLocator, BeanConfigDetector beanConfigDetector) {
        this.sourceLocator = requireNonNull(sourceLocator);
        this.beanConfigDetector = requireNonNull(beanConfigDetector);
    }


    @Override
    public List<BeanDeclaration> getBeanDeclarations() {
        if (beanDeclarations == null) {
            beanDeclarations = sourceLocator.getBeanDeclarations();
            for (BeanDeclaration beanDeclaration : new ArrayList<>(beanDeclarations)) {
                if (beanConfigDetector.isBeanConfig(beanDeclaration)) {
                    BeanDeclarationLocator beanConfigBeanDeclarationLocator = new BeanConfigBeanDeclarationLocator(beanDeclaration);
                    List<BeanDeclaration> beanConfigBeanDeclarations = beanConfigBeanDeclarationLocator.getBeanDeclarations();
                    beanDeclarations.addAll(beanConfigBeanDeclarations);
                }
            }
        }

        return beanDeclarations;
    }
}
