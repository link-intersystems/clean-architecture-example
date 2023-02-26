package com.link_intersystems.app.context;

import java.util.Stack;

class ExceptionUtils {

    static StringBuilder cyclicExceptionString(BeanRef beanRef, Stack<BeanRef> callStack) {
        StringBuilder sb = new StringBuilder("Cyclic bean dependency ");
        BeanRef requestingBeanRef = callStack.get(callStack.size() - 1);
        sb.append(requestingBeanRef.getType().getName());
        sb.append("\n");

        for (BeanRef ref : callStack) {
            int index = callStack.indexOf(ref);
            appendBeanRef(sb, ref, index);
        }

        appendBeanRef(sb, beanRef, callStack.size());

        sb.append("\n\t NOTE: You might want to use a Supplier<");
        sb.append(beanRef.getType().getSimpleName());
        sb.append("> instead.\n");
        return sb;
    }

    private static void appendBeanRef(StringBuilder sb, BeanRef ref, int indentation) {
        while (indentation-- > 0) {
            sb.append("\t");
        }
        sb.append(" > ");
        sb.append(ref);
        sb.append("\n");
    }
}
