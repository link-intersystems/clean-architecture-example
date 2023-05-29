package com.link_intersystems.carrental.ui;


import java.awt.*;
import java.util.List;

public interface View {
    String getTitle();

    Component createComponent(ApplicationModel applicationModel);

    List<String> getRequiredRoles();
}
