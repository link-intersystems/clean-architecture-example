package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.declaration.location.UnknownBeanDeclarationLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeanDeclarationRegistryTest {

    private BeanDeclarationRegistry beanDeclarationRegistry;
    private BeanDeclaration beanDeclaration1;
    private BeanDeclaration beanDeclaration2;

    @BeforeEach
    void setUp() {
        beanDeclaration1 = new BeanDeclaration(TestBean1.class, UnknownBeanDeclarationLocation.INSTANCE);
        beanDeclaration2 = new BeanDeclaration(TestBean2.class, UnknownBeanDeclarationLocation.INSTANCE);

        BeanDeclarationLocator locator = () -> Arrays.asList(beanDeclaration1, beanDeclaration2);
        beanDeclarationRegistry = new BeanDeclarationRegistry(locator);
    }

    @Test
    void uniqueBean() {
        List<BeanDeclaration> beanDeclarations = beanDeclarationRegistry.getBeanDeclaration(TestBean2.class);

        assertEquals(1, beanDeclarations.size());
        assertEquals(beanDeclaration2, beanDeclarations.get(0));
    }

    @Test
    void multipleBeans() {
        List<BeanDeclaration> beanDeclarations = beanDeclarationRegistry.getBeanDeclaration(TestInterface.class);

        assertEquals(2, beanDeclarations.size());
        assertEquals(beanDeclaration1, beanDeclarations.get(0));
        assertEquals(beanDeclaration2, beanDeclarations.get(1));
    }

    @Test
    void filtered() {
        beanDeclarationRegistry.setBeanDeclarationExcludeFilter(beanDeclaration -> beanDeclaration.getBeanType().equals(TestBean2.class));
        List<BeanDeclaration> beanDeclarations = beanDeclarationRegistry.getBeanDeclaration(TestInterface.class);

        assertEquals(1, beanDeclarations.size());
        assertEquals(beanDeclaration1, beanDeclarations.get(0));
    }

}