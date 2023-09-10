package com.link_intersystems.tx;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.aop.MethodInvocation;

public class TransactionMethodInterceptor implements MethodInterceptor {

    private final TransactionTemplate transactionTemplate;

    public TransactionMethodInterceptor(TransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Exception {
        return transactionTemplate.doInTransaction(() -> methodInvocation.proceed());
    }
}
