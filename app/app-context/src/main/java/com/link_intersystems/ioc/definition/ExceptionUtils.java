package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.api.LazyBeanSetter;

import java.util.Stack;

class ExceptionUtils {

    static StringBuilder cyclicExceptionString(BeanId beanId, Stack<BeanId> callStack) {
        StringBuilder sb = new StringBuilder("Cyclic bean dependency ");
        BeanId requestingBeanId = callStack.get(callStack.size() - 1);
        sb.append(requestingBeanId.getType().getName());
        sb.append("\n");

        for (BeanId actId : callStack) {
            int index = callStack.indexOf(actId);
            appendBeanRef(sb, actId, index);
        }

        appendBeanRef(sb, beanId, callStack.size());

        sb.append("\n\t NOTE: You might want to use a Supplier<");
        sb.append(beanId.getType().getSimpleName());
        sb.append("> or ");
        sb.append(LazyBeanSetter.class.getSimpleName());
        sb.append("<");
        sb.append(beanId.getType().getSimpleName());
        sb.append("> instead.\n");
        return sb;
    }

    private static void appendBeanRef(StringBuilder sb, BeanId beanId, int indentation) {
        while (indentation-- > 0) {
            sb.append("\t");
        }
        sb.append(" > ");
        sb.append(beanId);
        sb.append("\n");
    }

}
