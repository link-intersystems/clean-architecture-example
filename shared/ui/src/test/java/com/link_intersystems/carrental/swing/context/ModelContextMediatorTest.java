package com.link_intersystems.carrental.swing.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelContextMediatorTest {

    private static class SomeController {
        private ListModel model;

        public void setModel(ListModel model) {
            this.model = model;
        }
    }


    private ModelContext modelContext;
    private ModelContextMediator modelContextMediator;

    @BeforeEach
    void setUp() {
        modelContext = new ModelContext();
        modelContextMediator = new ModelContextMediator(modelContext);
    }


    @Test
    void whenAvailableAfterModelAvailable() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContextMediator.whenAvailable(ListModel.class).thenSet(someController::setModel);

        assertSame(defaultListModel, someController.model);
        modelContext.unregisterModel(ListModel.class, defaultListModel);
        assertNull(someController.model);
    }

    @Test
    void whenAvailableBeforeModelAvailable() {
        SomeController someController = new SomeController();
        modelContextMediator.whenAvailable(ListModel.class).thenSet(someController::setModel);

        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        assertSame(defaultListModel, someController.model);
        modelContext.unregisterModel(ListModel.class, defaultListModel);

        assertNull(someController.model);
    }

    @Test
    void setDefault() {
        SomeController someController = new SomeController();
        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();

        modelContextMediator.whenAvailable(ListModel.class).thenSet(someController::setModel).orSetDefault(defaultListModel);

        assertEquals(defaultListModel, someController.model);
    }

    @Test
    void disposeCondition() {
        SomeController someController = new SomeController();
        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();

        ModelContextCondition<ListModel> contextCondition = modelContextMediator.whenAvailable(ListModel.class);
        contextCondition.thenSet(someController::setModel).orSetDefault(defaultListModel);

        contextCondition.dispose();
        DefaultListModel defaultListModel2 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel2);

        assertEquals(defaultListModel, someController.model);
    }
}