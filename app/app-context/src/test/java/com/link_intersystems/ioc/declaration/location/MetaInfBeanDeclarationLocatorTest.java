package com.link_intersystems.ioc.declaration.location;

import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanDeclarationLocator;
import com.link_intersystems.ioc.declaration.locator.MetaInfBeanDeclarationLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetaInfBeanDeclarationLocatorTest {

    private BeanDeclarationLocator declarationLocator;

    @BeforeEach
    void setUp() {
        declarationLocator = new MetaInfBeanDeclarationLocator("com/link_intersystems/ioc/declaration/test-beans.config");
    }

    @Test
    void getBeanDeclarations() {
        List<BeanDeclaration> beanDeclarations = declarationLocator.getBeanDeclarations();

        assertEquals(2, beanDeclarations.size());

        BeanDeclaration beanDeclaration1 = beanDeclarations.get(0);
        assertEquals(TestBean1.class, beanDeclaration1.getBeanType());
        assertNotNull(beanDeclaration1.getLocation());

        BeanDeclaration beanDeclaration2 = beanDeclarations.get(1);
        assertEquals(TestBean2.class, beanDeclaration2.getBeanType());
        assertNotNull(beanDeclaration2.getLocation());
    }
}