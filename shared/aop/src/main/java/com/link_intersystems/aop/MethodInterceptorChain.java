package com.link_intersystems.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class MethodInterceptorChain implements MethodInterceptor {

    private List<MethodInterceptor> methodInterceptors = new ArrayList<>();

    public void addMethodInterceptor(MethodInterceptor methodInterceptor) {
        methodInterceptors.add(methodInterceptor);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        ChainElement chainElement = new ChainElement(0);
        return chainElement.invoke(methodInvocation);
    }

    class ChainElement {
        private int index;

        public ChainElement(int index) {
            this.index = index;
        }

        ChainElement next() {
            if (index < methodInterceptors.size() - 1) {
                return new ChainElement(index + 1);
            }
            return null;
        }

        MethodInterceptor getMethodInterceptor() {
            if (index < methodInterceptors.size()) {
                return methodInterceptors.get(index);
            } else {
                return methodInvocation -> methodInvocation.proceed();
            }
        }

        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            MethodInterceptor methodInterceptor = getMethodInterceptor();
            return methodInterceptor.invoke(new MethodInvocation() {
                @Override
                public Method getMethod() {
                    return methodInvocation.getMethod();
                }

                @Override
                public Object[] getArgs() {
                    return methodInvocation.getArgs();
                }

                @Override
                public Object proceed() throws Throwable {
                    ChainElement nextElement = next();
                    if (nextElement == null) {
                        return methodInvocation.proceed();
                    } else {
                        return nextElement.invoke(methodInvocation);
                    }
                }
            });
        }
    }

}
