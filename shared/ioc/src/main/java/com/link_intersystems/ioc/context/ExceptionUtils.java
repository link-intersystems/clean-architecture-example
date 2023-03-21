package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.api.LazyBeanSetter;
import com.link_intersystems.ioc.declaration.BeanDeclaration;

import java.util.Stack;

class ExceptionUtils {

    static StringBuilder cyclicExceptionString(BeanDeclaration beanDeclaration, Stack<BeanDeclaration> callStack) {
        StringBuilder sb = new StringBuilder("Cyclic bean dependency ");
        BeanDeclaration requestingBeanId = callStack.get(callStack.size() - 1);
        sb.append(requestingBeanId.getBeanType().getName());
        sb.append("\n");

        for (BeanDeclaration actBeanDeclaration : callStack) {
            int index = callStack.indexOf(actBeanDeclaration);
            appendBeanRef(sb, beanDeclaration, index);
        }

        appendBeanRef(sb, beanDeclaration, callStack.size());

        sb.append("\n\t NOTE: You might want to use a Supplier<");
        Class<?> beanType = beanDeclaration.getBeanType();
        sb.append(beanType.getSimpleName());
        sb.append("> or ");
        sb.append(LazyBeanSetter.class.getSimpleName());
        sb.append("<");
        sb.append(beanType.getSimpleName());
        sb.append("> instead.\n");
        return sb;
    }

    private static void appendBeanRef(StringBuilder sb, BeanDeclaration beanDeclaration, int indentation) {
        while (indentation-- > 0) {
            sb.append("\t");
        }
        sb.append(" > ");
        sb.append(beanDeclaration);
        sb.append("\n");
    }

}
