package com.link_intersystems.ioc.declaration.config;

import com.link_intersystems.ioc.declaration.BeanConfigDetector;
import com.link_intersystems.ioc.declaration.BeanDeclaration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeBeanConfigDetector implements BeanConfigDetector {

    private List<BeanConfigDetector> beanConfigDetectors;

    public CompositeBeanConfigDetector(BeanConfigDetector... beanConfigDetectors) {
        this(Arrays.asList(beanConfigDetectors));
    }

    public CompositeBeanConfigDetector(List<BeanConfigDetector> beanConfigDetectors) {
        this.beanConfigDetectors = new ArrayList<>(beanConfigDetectors);
    }

    @Override
    public boolean isBeanConfig(BeanDeclaration beanDeclaration) {
        for (BeanConfigDetector beanConfigDetector : beanConfigDetectors) {
            if (beanConfigDetector.isBeanConfig(beanDeclaration)) {
                return true;
            }
        }
        return false;
    }
}
