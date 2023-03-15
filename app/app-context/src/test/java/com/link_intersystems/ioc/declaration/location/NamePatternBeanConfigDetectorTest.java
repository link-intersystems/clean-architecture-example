package com.link_intersystems.ioc.declaration.location;

import com.link_intersystems.ioc.declaration.BeanConfigDetector;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.config.NamePatternBeanConfigDetector;
import com.link_intersystems.ioc.declaration.location.UnknownBeanDeclarationLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NamePatternBeanConfigDetectorTest {

    static class SomeConfig {

    }

    @Test
    void isBeanConfig() {
        BeanConfigDetector configDetector = new NamePatternBeanConfigDetector();

        BeanDeclaration beanDeclaration = new BeanDeclaration(SomeConfig.class, UnknownBeanDeclarationLocation.INSTANCE);
        assertTrue(configDetector.isBeanConfig(beanDeclaration));
    }
}