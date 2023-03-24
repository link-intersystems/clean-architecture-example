package com.link_intersystems.carrental;

import com.link_intersystems.ioc.aop.BeanPostProcessor;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.jdbc.tx.LocalTransactionManager;
import com.link_intersystems.jdbc.tx.TransactionAwareDataSource;
import com.link_intersystems.jdbc.tx.TransactionManager;
import com.link_intersystems.jdbc.tx.TransactionProxy;

import javax.sql.DataSource;

public class TransactionConfig {

    public TransactionManager transactionManager(DataSource dataSource) {
        return new LocalTransactionManager(dataSource);
    }

    public BeanPostProcessor transactionBeanPostProcessor() {
        return (beanFactory, beanDeclaration, bean) -> {
            if (isUseCase(beanDeclaration)) {
                TransactionManager transactionManager = beanFactory.getBean(TransactionManager.class);
                return TransactionProxy.create(bean, transactionManager);
            }

            if (DataSource.class.isAssignableFrom(beanDeclaration.getBeanType())) {
                return new TransactionAwareDataSource((DataSource) bean);
            }

            return bean;
        };
    }

    private boolean isUseCase(BeanDeclaration beanDeclaration) {
        String simpleTypeName = beanDeclaration.getBeanType().getSimpleName();
        return simpleTypeName.endsWith("Interactor") || simpleTypeName.endsWith("UseCase");
    }
}
