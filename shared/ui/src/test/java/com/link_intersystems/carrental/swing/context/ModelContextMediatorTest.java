package com.link_intersystems.carrental.swing.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelContextMediatorTest {

    private static class SomeController {
        private ListModel model;
        private boolean executed;

        public void setModel(ListModel model) {
            this.model = model;
        }

        public void execute() {
            executed = !executed;
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
    void whenModelChangedRun() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContextMediator.when(ListModel.class).changed().then(someController::execute);

        assertTrue(someController.executed);
        modelContext.unregisterModel(ListModel.class, defaultListModel);
        assertFalse(someController.executed);
    }

    @Test
    void whenModelAddedRun() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContextMediator.when(ListModel.class).added().then(someController::execute);

        assertTrue(someController.executed);
        modelContext.unregisterModel(ListModel.class, defaultListModel);
        assertTrue(someController.executed);
    }

    @Test
    void whenModelRemovedRun() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContextMediator.when(ListModel.class).removed().then(someController::execute);
        assertFalse(someController.executed);
        modelContext.unregisterModel(ListModel.class, defaultListModel);
        assertTrue(someController.executed);
    }

    @Test
    void whenModelAddedSetModel() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContextMediator.when(ListModel.class).added().then(someController::setModel);

        assertSame(defaultListModel, someController.model);
        modelContext.unregisterModel(ListModel.class, defaultListModel);
        assertEquals(defaultListModel, someController.model);
    }

    @Test
    void whenModelRemovedSetModel() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        someController.model = defaultListModel;
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContextMediator.when(ListModel.class).removed().then(someController::setModel);

        assertSame(defaultListModel, someController.model);
        modelContext.unregisterModel(ListModel.class, defaultListModel);
        assertNull(someController.model);
    }

    @Test
    void whenAddedAfterModelAvailable() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContextMediator.when(ListModel.class).changed().then(someController::setModel);

        assertSame(defaultListModel, someController.model);
        modelContext.unregisterModel(ListModel.class, defaultListModel);
        assertNull(someController.model);
    }

    @Test
    void whenAddedBeforeModelAvailable() {
        SomeController someController = new SomeController();
        modelContextMediator.when(ListModel.class).changed().then(someController::setModel);

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

        modelContextMediator.when(ListModel.class).added().then(someController::setModel).orDefault(defaultListModel);

        assertEquals(defaultListModel, someController.model);
    }

    @Test
    void disposeAll() {
        SomeController someController = new SomeController();
        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();

        WhenModel<ListModel> whenModel = modelContextMediator.when(ListModel.class);
        ModelAction<ListModel> addedModelAction = whenModel.added();
        addedModelAction.then(someController::setModel).orDefault(defaultListModel);

        whenModel.dispose();
        DefaultListModel defaultListModel2 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel2);

        assertEquals(defaultListModel, someController.model);
    }
}