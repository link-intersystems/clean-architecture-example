package com.link_intersystems.carrental.ui;

import java.util.Arrays;

public class MainUIConfig {

    public MainFrame getMainFrame(View... views) {
        MainFrame mainFrame = new MainFrame();

        mainFrame.setViews(Arrays.asList(views));

        return mainFrame;
    }


}
