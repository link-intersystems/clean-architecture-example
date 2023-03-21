package com.link_intersystems.ioc.declaration;

import java.util.List;

public class AmbiguousBeanException extends RuntimeException {
    private List<BeanDeclaration> options;

    public AmbiguousBeanException(String msg, List<BeanDeclaration> options) {
        super(msg);
        this.options = options;
    }

    public List<BeanDeclaration> getOptions() {
        return options;
    }
}
