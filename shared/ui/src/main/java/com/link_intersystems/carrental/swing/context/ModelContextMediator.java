package com.link_intersystems.carrental.swing.context;

import java.util.function.Consumer;

public class ModelContextMediator {

    private ModelContext modelContext;

    public ModelContextMediator(ModelContext modelContext) {
        this.modelContext = modelContext;
    }

    public <T> ModelContextCondition<T> whenAvailable(Class<? super T> modelType) {
        return whenAvailable(modelType, null);
    }

    public <T> ModelContextCondition<T> whenAvailable(Class<? super T> modelType, String name) {
        class ModelContextMediatorListenerAdapter implements ModelContextListener<T> {

            private Consumer<T> modelSetter;
            private T setModel;
            private T defaultModel;

            public ModelContextMediatorListenerAdapter(Consumer<T> modelSetter) {
                this.modelSetter = modelSetter;
            }

            @Override
            public void modelAdded(T model) {
                modelSetter.accept(model);
                this.setModel = model;
            }

            @Override
            public void modelRemoved(T model) {
                modelSetter.accept(defaultModel);
                this.setModel = null;
            }

            public void setDefault(T defaultModel) {
                if (setModel == null) {
                    modelSetter.accept(defaultModel);
                }
                this.defaultModel = defaultModel;
            }
        }

        return new ModelContextCondition<T>() {

            private ModelContextMediatorListenerAdapter listenerAdapter;

            @Override
            public SetModelAction thenSet(Consumer<T> modelSetter) {
                listenerAdapter = new ModelContextMediatorListenerAdapter(modelSetter);
                modelContext.addModelContextListener(modelType, name, listenerAdapter);
                return (SetModelAction<T>) defaultModel -> listenerAdapter.setDefault(defaultModel);
            }

            @Override
            public void dispose() {
                if (listenerAdapter != null) {
                    modelContext.removeModelContextListener(modelType, name, listenerAdapter);
                }
                listenerAdapter = null;
            }
        };
    }
}
