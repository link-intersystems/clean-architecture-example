package com.link_intersystems.ioc.aop;

import com.link_intersystems.ioc.context.BeanFactory;
import com.link_intersystems.ioc.declaration.BeanDeclaration;

public interface BeanPostProcessor {

    public Object processBean(BeanFactory beanFactory, BeanDeclaration beanDeclaration, Object bean);
}
