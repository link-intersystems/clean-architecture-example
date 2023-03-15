package com.link_intersystems.ioc;

import java.io.IOException;
import java.util.List;

public interface BeanDefinitionLocator {
    List<BeanDefinition> getBeanDefinitions() throws IOException, ClassNotFoundException;
}
