package com.link_intersystems.car.offers.ui;

import com.link_intersystems.swing.table.BeanListTableCellSupport;
import com.link_intersystems.swing.table.ListTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class CarOfferView {

    private JPanel viewPanel = new JPanel();

    private JPanel searchPanel = new JPanel();
    private JButton searchButton = new JButton();

    private JTable carOfferTable = new JTable();
    private JScrollPane carOfferTableScrollPane = new JScrollPane(carOfferTable);
    private Optional<CarSearchModel> carSearchModel = Optional.empty();

    public CarOfferView() {
        searchPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 4;
        searchPanel.add(searchButton, constraints);

        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(searchPanel, BorderLayout.NORTH);
        viewPanel.add(carOfferTableScrollPane, BorderLayout.CENTER);
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {

        ListTableModel<CarOfferModel> carOfferModelListTableModel = new ListTableModel<>();

        carOfferModelListTableModel.setListModel(carOfferListModel);
        carOfferModelListTableModel.setListTableCellRenderer(BeanListTableCellSupport.of(CarOfferModel.class));

        carOfferTable.setModel(carOfferModelListTableModel);
    }


    public void setCarSearchModel(CarSearchModel carSearchModel) {

    }

    public void setCarSearchRunnable(Runnable runnable) {
        AbstractAction searchAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runnable.run();
            }
        };
        searchAction.putValue(Action.NAME, "Search");
        searchButton.setAction(searchAction);
    }

    public Component getViewComponent() {
        return viewPanel;
    }
}
