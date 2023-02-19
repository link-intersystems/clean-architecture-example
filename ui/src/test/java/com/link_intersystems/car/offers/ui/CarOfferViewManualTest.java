package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.CarOffersUseCase;

import javax.swing.*;
import java.awt.*;

public class CarOfferViewManualTest {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 600);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CarOffersUseCase carOfferUseCase = new MockCarOffersUseCase();
        CarOfferComponent carOfferComponent = new CarOfferComponent(carOfferUseCase);

        jFrame.getContentPane().add(carOfferComponent.getCarOfferView().getViewComponent(), BorderLayout.CENTER);

        jFrame.setVisible(true);
    }
}
