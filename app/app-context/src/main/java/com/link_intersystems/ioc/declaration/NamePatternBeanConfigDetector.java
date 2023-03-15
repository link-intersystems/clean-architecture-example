package com.link_intersystems.ioc.declaration;

import java.util.regex.Pattern;

public class NamePatternBeanConfigDetector implements BeanConfigDetector {

    public static final String DEFAULT_NAME_PATTERN = "^([A-Za-z0-9_]+)Config$";

    private Pattern beanConfigNamePattern;

    public NamePatternBeanConfigDetector() {
        this(Pattern.compile(DEFAULT_NAME_PATTERN));
    }

    public NamePatternBeanConfigDetector(Pattern beanConfigNamePattern) {
        this.beanConfigNamePattern = beanConfigNamePattern;
    }

    @Override
    public boolean isBeanConfig(BeanDeclaration beanDeclaration) {
        String beanName = beanDeclaration.getBeanType().getSimpleName();
        return beanConfigNamePattern.matcher(beanName).matches();
    }
}
