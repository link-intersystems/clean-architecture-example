package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.api.BeanSelector;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.definition.BeanDefinition;

import java.util.Iterator;
import java.util.List;

class DefaultBeanSelector<T> implements BeanSelector<T> {

    private Class<T> requestedType;
    private List<BeanDefinition<T>> beanDefinitions;


    public DefaultBeanSelector(Class<T> requestedType, List<BeanDefinition<T>> beanDefinitions) {
        this.requestedType = requestedType;
        this.beanDefinitions = beanDefinitions;
    }

    @Override
    public T select(String beanName) {
        for (BeanDefinition<T> beanDefinition : beanDefinitions) {
            BeanDeclaration declaration = beanDefinition.getDeclaration();
            if (beanName.equals(declaration.getBeanName())) {
                return beanDefinition.getBean();
            }
        }

        StringBuilder sb = new StringBuilder("No bean ");
        sb.append(requestedType.getName());
        sb.append(" named ");
        sb.append(beanName);
        sb.append(" available. Available beans are:\n");

        if (beanDefinitions.isEmpty()) {
            sb.append("\t o ");
            sb.append(" <NO BEAN DEFINITIONS>");
        } else {
            Iterator<BeanDefinition<T>> iterator = beanDefinitions.iterator();
            while (iterator.hasNext()) {
                BeanDefinition<T> option = iterator.next();
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
