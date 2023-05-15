package com.link_intersystems.carrental.swing.context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.*;

public class ModelContextMediator {

    private static class ModelContextMediatorListenerAdapter<T> implements ModelContextListener<T> {

        private Consumer<T> onModelAdded;
        private Consumer<T> onModelRemoved;
        private T setModel;
        private T defaultModel;

        public ModelContextMediatorListenerAdapter(Consumer<T> modelSetter) {
            this(modelSetter, modelSetter);
        }

        public ModelContextMediatorListenerAdapter(Consumer<T> onModelAdded, Consumer<T> onModelRemoved) {
            this.onModelAdded = onModelAdded;
            this.onModelRemoved = onModelRemoved;
        }

        @Override
        public void modelAdded(T model) {
            if (onModelAdded != null) {
                onModelAdded.accept(model);
            }

            this.setModel = model;
        }

        @Override
        public void modelRemoved(T model) {
            if (onModelRemoved != null) {
                onModelRemoved.accept(defaultModel);
            }

            this.setModel = null;
        }

        public void setDefault(T defaultModel) {
            if (setModel == null && onModelAdded != null) {
                onModelAdded.accept(defaultModel);
            }
            this.defaultModel = defaultModel;
        }
    }

    private static class RumnableListenerAdapter<T> implements ModelContextListener<T> {

        private Runnable modelAddedRunnable;
        private Runnable modelRemovedRunnable;

        public RumnableListenerAdapter(Runnable modelAddedRunnable, Runnable modelRemovedRunnable) {
            this.modelAddedRunnable = modelAddedRunnable;
            this.modelRemovedRunnable = modelRemovedRunnable;
        }

        @Override
        public void modelAdded(T model) {
            if (modelAddedRunnable != null) {
                modelAddedRunnable.run();
            }
        }

        @Override
        public void modelRemoved(T model) {
            if (modelRemovedRunnable != null) {
                modelRemovedRunnable.run();
            }
        }
    }

    private static abstract class AbstractWhenModel<T> implements WhenModel<T> {

        private final List<ModelAction<?>> actions = new ArrayList<>();

        protected <T> ModelAction<T> add(ModelAction<T> modelAction) {
            actions.add(modelAction);
            return modelAction;
        }

        @Override
        public void dispose() {
            actions.forEach(ModelAction::dispose);
            actions.clear();
        }
    }

    private static abstract class AbstractModelAction<T> implements ModelAction<T> {

        private ModelContext modelContext;
        private ModelQualifier<? super T> modelQualifier;

        protected final List<ModelContextListener<T>> listeners = new ArrayList<>();

        public AbstractModelAction(ModelContext modelContext, ModelQualifier<? super T> modelQualifier) {
            this.modelContext = modelContext;
            this.modelQualifier = modelQualifier;
        }


        @Override
        public void dispose() {
            listeners.forEach(l -> modelContext.removeModelContextListener(modelQualifier, l));
            listeners.clear();
        }
    }

    private ModelContext modelContext;

    public ModelContextMediator(ModelContext modelContext) {
        this.modelContext = modelContext;
    }

    public <T> WhenModel<T> when(Class<? super T> modelType) {
        return when(modelType, null);
    }

    public <T> WhenModel<T> when(Class<? super T> modelType, String name) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(modelType, name);
        return when(modelQualifier);
    }

    public <T> AbstractWhenModel<T> when(ModelQualifier<? super T> modelQualifier) {
        return new AbstractWhenModel<T>() {
            @Override
            public ModelAction<T> added() {
                return add(new AbstractModelAction<T>(modelContext, modelQualifier) {

                    @Override
                    public ModelState then(Consumer<T> modelConsumer) {
                        ModelContextMediatorListenerAdapter<T> listenerAdapter = new ModelContextMediatorListenerAdapter<>(modelConsumer, null);
                        listeners.add(listenerAdapter);
                        modelContext.addModelContextListener(modelQualifier, listenerAdapter);
                        return (ModelState<T>) defaultModel -> listenerAdapter.setDefault(defaultModel);
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RumnableListenerAdapter<T> rumnableListenerAdapter = new RumnableListenerAdapter<>(requireNonNull(runnable), null);
                        listeners.add(rumnableListenerAdapter);
                        modelContext.addModelContextListener(modelQualifier, rumnableListenerAdapter);
                    }
                });
            }

            @Override
            public ModelAction<T> removed() {
                return add(new AbstractModelAction<T>(modelContext, modelQualifier) {
                    @Override
                    public ModelState then(Consumer<T> modelConsumer) {
                        ModelContextMediatorListenerAdapter<T> listenerAdapter = new ModelContextMediatorListenerAdapter<>(null, modelConsumer);
                        listeners.add(listenerAdapter);
                        modelContext.addModelContextListener(modelQualifier, listenerAdapter);
                        return (ModelState<T>) defaultModel -> listenerAdapter.setDefault(defaultModel);
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RumnableListenerAdapter<T> rumnableListenerAdapter = new RumnableListenerAdapter<>(null, requireNonNull(runnable));
                        listeners.add(rumnableListenerAdapter);
                        modelContext.addModelContextListener(modelQualifier, rumnableListenerAdapter);
                    }
                });
            }

            @Override
            public ModelAction<T> changed() {
                return add(new AbstractModelAction<T>(modelContext, modelQualifier) {
                    @Override
                    public ModelState then(Consumer<T> modelConsumer) {
                        ModelContextMediatorListenerAdapter<T> listenerAdapter = new ModelContextMediatorListenerAdapter<>(modelConsumer, modelConsumer);
                        listeners.add(listenerAdapter);
                        modelContext.addModelContextListener(modelQualifier, listenerAdapter);
                        return (ModelState<T>) defaultModel -> listenerAdapter.setDefault(defaultModel);
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RumnableListenerAdapter<T> rumnableListenerAdapter = new RumnableListenerAdapter<>(requireNonNull(runnable), requireNonNull(runnable));
                        listeners.add(rumnableListenerAdapter);
                        modelContext.addModelContextListener(modelQualifier, rumnableListenerAdapter);
                    }
                });
            }
        };
    }
}
