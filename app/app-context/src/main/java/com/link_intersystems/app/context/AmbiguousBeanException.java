package com.link_intersystems.app.context;

import java.util.List;

public class AmbiguousBeanException extends RuntimeException {
    private List<BeanDefinition> options;

    public AmbiguousBeanException(String msg, List<BeanDefinition> options) {
        super(msg);
        this.options = options;
    }

    public List<BeanDefinition> getOptions() {
        return options;
    }
}
