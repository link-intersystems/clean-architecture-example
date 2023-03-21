package com.link_intersystems.ioc.declaration.location;

import com.link_intersystems.ioc.declaration.BeanConfigDetector;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.DefaultBeanDeclaration;
import com.link_intersystems.ioc.declaration.config.NamePatternBeanConfigDetector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NamePatternBeanConfigDetectorTest {

    static class SomeConfig {

    }

    @Test
    void isBeanConfig() {
        BeanConfigDetector configDetector = new NamePatternBeanConfigDetector();

        BeanDeclaration beanDeclaration = new DefaultBeanDeclaration(SomeConfig.class);
        assertTrue(configDetector.isBeanConfig(beanDeclaration));
    }
}