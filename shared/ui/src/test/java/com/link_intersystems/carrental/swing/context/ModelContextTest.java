package com.link_intersystems.carrental.swing.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelContextTest {

    private ModelContextListenerMock<ListModel<String>> listenerMock;
    private ModelContext modelContext;

    @BeforeEach
    void setUp() {
        modelContext = new ModelContext();
        listenerMock = new ModelContextListenerMock<>();

    }

    @Test
    void registerUnamedModelTwice() {
        modelContext.registerModel(ListModel.class, new DefaultListModel());
        assertThrows(IllegalArgumentException.class, () -> modelContext.registerModel(ListModel.class, new DefaultListModel()));
    }

    @Test
    void registerNamedModelTwice() {
        modelContext.registerModel(ListModel.class, "model1", new DefaultListModel());
        assertThrows(IllegalArgumentException.class, () -> modelContext.registerModel(ListModel.class, "model1", new DefaultListModel()));
    }

    @Test
    void registerModelsWithDifferentQualifiers() {
        modelContext.registerModel(ListModel.class, new DefaultListModel());
        modelContext.registerModel(ListModel.class, "model1", new DefaultListModel());
    }

    @Test
    void registerModel() {
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        ListModel listModel = modelContext.get(ListModel.class);
        assertSame(defaultListModel, listModel);
    }

    @Test
    void registerNamedModel() {
        modelContext.registerModel(ListModel.class, new DefaultListModel());
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model1", defaultListModel);

        ListModel listModel = modelContext.get(ListModel.class, "model1");
        assertSame(defaultListModel, listModel);
    }

    @Test
    void unregisterModel() {
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContext.unregisterModel(ListModel.class, defaultListModel);

        assertNull(modelContext.get(ListModel.class));
    }

    @Test
    void unregisterNamedModel() {
        DefaultListModel defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model1", defaultListModel);

        modelContext.unregisterModel(ListModel.class, "model1", defaultListModel);

        assertNull(modelContext.get(ListModel.class, "model1"));
    }


    @Test
    void addListenerAfterModelAdded() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContext.addModelContextListener(ListModel.class, listenerMock);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertSame(defaultListModel, lastAddedModel);
    }

    @Test
    void addListenerBeforeModelAdded() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        modelContext.addModelContextListener(ListModel.class, listenerMock);

        modelContext.registerModel(ListModel.class, defaultListModel);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertSame(defaultListModel, lastAddedModel);
    }

    @Test
    void addListenerAfterModelRemoved() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);
        modelContext.unregisterModel(ListModel.class, defaultListModel);

        modelContext.addModelContextListener(ListModel.class, listenerMock);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertNull(lastAddedModel);
    }

    @Test
    void addListenerBeforeModelRemoved() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);
        modelContext.addModelContextListener(ListModel.class, listenerMock);

        modelContext.unregisterModel(ListModel.class, defaultListModel);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertNull(lastAddedModel);
    }

    @Test
    void removeListenerAfterModelAdded() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        modelContext.addModelContextListener(ListModel.class, listenerMock);
        modelContext.registerModel(ListModel.class, defaultListModel);

        modelContext.removeModelContextListener(ListModel.class, listenerMock);

        modelContext.unregisterModel(ListModel.class, defaultListModel);
        modelContext.registerModel(ListModel.class, new DefaultListModel());

        assertEquals(Collections.singletonList(defaultListModel), listenerMock.getModels());
    }

    @Test
    void removeListenerBeforeModelAdded() {
        modelContext.addModelContextListener(ListModel.class, listenerMock);

        modelContext.removeModelContextListener(ListModel.class, listenerMock);

        DefaultListModel<String> defaultListModel = new DefaultListModel();
        modelContext.registerModel(ListModel.class, defaultListModel);
        assertEquals(Collections.emptyList(), listenerMock.getModels());
    }

    @Test
    void unspecificModelListenerAddedBeforeModels() {
        modelContext.addModelContextListener(ListModel.class, listenerMock);
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model2", defaultListModel2);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Collections.emptyList(), models);
    }

    @Test
    void specificModelListenerAddedBeforeModels() {
        modelContext.addModelContextListener(ListModel.class, "model2", listenerMock);
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model2", defaultListModel2);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Arrays.asList( defaultListModel2), models);
    }

    @Test
    void unspecificModelListenerAddedAfterModels() {
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model2", defaultListModel2);

        modelContext.addModelContextListener(ListModel.class, listenerMock);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Collections.emptyList(), models);
    }

    @Test
    void specificModelListenerAddedAfterModels() {
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        modelContext.registerModel(ListModel.class, "model2", defaultListModel2);

        modelContext.addModelContextListener(ListModel.class, "model2", listenerMock);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Arrays.asList( defaultListModel2), models);
    }


}