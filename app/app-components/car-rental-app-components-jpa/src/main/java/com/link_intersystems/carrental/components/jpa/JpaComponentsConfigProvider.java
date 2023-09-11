package com.link_intersystems.carrental.components.jpa;

import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.components.ComponentsConfigProvider;

import java.util.Properties;

public class JpaComponentsConfigProvider extends ComponentsConfigProvider {
    @Override
    public ComponentsConfig createComponentsConfig(Properties appProperties) {
        return new JpaComponentsConfig(appProperties);
    }
}
