package com.link_intersystems.car.ui;

import com.link_intersystems.car.offers.ui.CarOfferView;
import com.link_intersystems.swing.DimensionExt;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.*;

public class MainFrame {

    private JFrame mainFrame = new JFrame();

    public MainFrame() {
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point centeredFrameLocation = new DimensionExt(mainFrame.getSize()).centerOn(screenSize);
        mainFrame.setLocation(centeredFrameLocation);
    }

    public void setCarOfferView(CarOfferView carOfferView) {
        Container contentPane = mainFrame.getContentPane();
        Component viewComponent = carOfferView.getViewComponent();
        contentPane.add(viewComponent, BorderLayout.CENTER);
    }

    public MessageDialog getMessageDialog() {
        return new MessageDialog() {
            @Override
            public void showException(Throwable ex) {
                StringBuilder sb = new StringBuilder();
                sb.append(ex.getClass().getName());
                sb.append(":\n\n");
                sb.append(ex.getMessage());
                JOptionPane.showMessageDialog(mainFrame, sb);
            }
        };
    }


    public void show() {
        mainFrame.setVisible(true);
    }
}
