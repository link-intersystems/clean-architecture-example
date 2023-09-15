package com.link_intersystems.swing.selection;

import com.link_intersystems.events.swing.ListDataEventMethod;
import com.link_intersystems.events.swing.ListSelectionEventMethod;
import com.link_intersystems.swing.list.ListModelSelection;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static com.link_intersystems.events.swing.ListDataEventMethod.*;
import static java.util.Objects.*;

public class ListSelectionProvider<E> implements SelectionProvider<E> {

    private SelectionProviderSupport<E> selectionProviderSupport;
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private ListModel<E> listModel = new DefaultListModel<>();
    private ListSelectionListener listSelectionListener = ListSelectionEventMethod.VALUE_CHANGED.listener(this::onListSelectionChanged);
    private ListDataListener listDataListener = new ListDataEventMethod(CONTENTS_CHANGED_NAME, INTERVAL_ADDED_NAME, INTERVAL_REMOVED_NAME).listener(this::updateSelection);

    public ListSelectionProvider(Object selectionEventSource) {
        selectionProviderSupport = new SelectionProviderSupport<>(selectionEventSource);
    }

    public void setListModel(ListModel<E> listModel) {
        requireNonNull(listModel);

        this.listModel.removeListDataListener(listDataListener);

        this.listModel = listModel;

        this.listModel.addListDataListener(listDataListener);
    }


    public void setListSelectionModel(ListSelectionModel listSelectionModel) {
        requireNonNull(listSelectionModel);

        this.listSelectionModel.removeListSelectionListener(listSelectionListener);

        this.listSelectionModel = listSelectionModel;

        this.listSelectionModel.addListSelectionListener(listSelectionListener);
    }


    private void onListSelectionChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        updateSelection();
    }

    private void updateSelection() {
        Selection<E> newSelection = new ListModelSelection<>(listModel, listSelectionModel);
        selectionProviderSupport.setSelection(newSelection);
    }


    @Override
    public void addSelectionChangedListener(SelectionListener<E> listener) {
        selectionProviderSupport.addSelectionChangedListener(listener);
    }

    @Override
    public void removeSelectionChangedListener(SelectionListener<E> listener) {
        selectionProviderSupport.removeSelectionChangedListener(listener);
    }

    @Override
    public Selection<E> getSelection() {
        return selectionProviderSupport.getSelection();
    }
}
