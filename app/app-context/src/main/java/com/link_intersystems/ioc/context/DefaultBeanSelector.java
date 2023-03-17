package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.api.BeanSelector;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.QualifierBeanAmbiguityResolver;
import com.link_intersystems.ioc.definition.BeanDefinition;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

class DefaultBeanSelector<T> implements BeanSelector<T> {

    private Class<T> requestedType;
    private List<BeanDeclaration> beanDeclarations;
    private Function<BeanDeclaration, BeanDefinition<Object>> beanGetter;


    public DefaultBeanSelector(Class<T> requestedType, List<BeanDeclaration> beanDeclarations, Function<BeanDeclaration, BeanDefinition<Object>> beanGetter) {
        this.requestedType = requestedType;
        this.beanDeclarations = beanDeclarations;
        this.beanGetter = beanGetter;
    }

    @Override
    public T select(String beanName) {
        QualifierBeanAmbiguityResolver ambiguityResolver = new QualifierBeanAmbiguityResolver(beanName);
        BeanDeclaration beanDeclaration = ambiguityResolver.selectBean(requestedType, beanDeclarations);
        if (beanDeclaration != null) {
            BeanDefinition<Object> beanDefinition = beanGetter.apply(beanDeclaration);
            return (T) beanDefinition.getBean();
        }

        StringBuilder sb = new StringBuilder("No bean ");
        sb.append(requestedType.getName());
        sb.append(" named ");
        sb.append(beanName);
        sb.append(" available. Available beans are:\n");

        if (beanDeclarations.isEmpty()) {
            sb.append("\t o ");
            sb.append(" <NO BEAN DEFINITIONS>");
        } else {
            Iterator<BeanDeclaration> iterator = beanDeclarations.iterator();
            while (iterator.hasNext()) {
                BeanDeclaration option = iterator.next();
                sb.append("\t o ");
                sb.append(option);
                if (iterator.hasNext()) {
                    sb.append("\n");
                }
            }
        }
        throw new RuntimeException(sb.toString());
    }
}
