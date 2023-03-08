package com.link_intersystems.app.context;

import java.util.List;
import java.util.Stack;

class ExceptionUtils {

    static StringBuilder cyclicExceptionString(BeanDeclaration beanDeclaration, Stack<BeanDeclaration> callStack) {
        StringBuilder sb = new StringBuilder("Cyclic bean dependency ");
        BeanDeclaration requestingBeanDeclaration = callStack.get(callStack.size() - 1);
        sb.append(requestingBeanDeclaration.getType().getName());
        sb.append("\n");

        for (BeanDeclaration ref : callStack) {
            int index = callStack.indexOf(ref);
            appendBeanRef(sb, ref, index);
        }

        appendBeanRef(sb, beanDeclaration, callStack.size());

        sb.append("\n\t NOTE: You might want to use a Supplier<");
        sb.append(beanDeclaration.getType().getSimpleName());
        sb.append("> or ");
        sb.append(LazyBeanSetter.class.getSimpleName());
        sb.append("<");
        sb.append(beanDeclaration.getType().getSimpleName());
        sb.append("> instead.\n");
        return sb;
    }

    private static void appendBeanRef(StringBuilder sb, BeanDeclaration ref, int indentation) {
        while (indentation-- > 0) {
            sb.append("\t");
        }
        sb.append(" > ");
        sb.append(ref);
        sb.append("\n");
    }

    public static String ambiguousBean(BeanDeclaration beanDeclaration, List<BeanDefinition> matchingBeanDefinitions) {
        StringBuilder sb = new StringBuilder("Bean ");
        sb.append(beanDeclaration.getType().getName());
        sb.append(" is ambiguous. :\n");

        for (BeanDefinition beanDefinition : matchingBeanDefinitions) {
            appendBeanRef(sb, beanDefinition.getBeanDeclaration(), 1);
        }

        sb.append("\n\t NOTE: You might want to use a BeanSelector<");
        sb.append(beanDeclaration.getType().getSimpleName());
        sb.append("> instead.\n");

        return sb.toString();
    }
}
