package com.link_intersystems.carrental.swing.context;

import com.link_intersystems.util.AmbiguousObjectException;

import java.text.MessageFormat;
import java.util.*;

public class ModelContext {


    private static class NamedModel<T> {

        private final ModelQualifier<T> modelQualifier;
        private final T model;

        public <M extends T> NamedModel(ModelQualifier<T> modelQualifier, M model) {
            this.modelQualifier = modelQualifier;
            this.model = model;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NamedModel<?> that = (NamedModel<?>) o;
            return Objects.equals(modelQualifier, that.modelQualifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(modelQualifier);
        }

        public boolean accept(ModelQualifier<?> modelQualifier) {
            return this.modelQualifier.equals(modelQualifier);
        }
    }

    private static class ModelContextListenerRegistration<T> {

        private ModelQualifier<? super T> modelQualifier;
        private ModelContextListener<T> modelContextListener;

        public ModelContextListenerRegistration(ModelQualifier<? super T> modelQualifier, ModelContextListener<T> modelContextListener) {
            this.modelQualifier = modelQualifier;
            this.modelContextListener = modelContextListener;
        }

        public <T> ModelContextListener<T> cast(ModelQualifier<? super T> modelQualifier) {
            if (accept(modelQualifier)) {
                return (ModelContextListener<T>) modelContextListener;
            }
            return null;
        }

        public <T> boolean accept(ModelQualifier<? super T> modelQualifier) {
            return this.modelQualifier.equals(modelQualifier);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ModelContextListenerRegistration<?> that = (ModelContextListenerRegistration<?>) o;
            return Objects.equals(modelQualifier, that.modelQualifier) && Objects.equals(modelContextListener, that.modelContextListener);
        }

        @Override
        public int hashCode() {
            return Objects.hash(modelQualifier, modelContextListener);
        }
    }

    private Set<NamedModel> models = new LinkedHashSet<>();

    private List<ModelContextListenerRegistration<?>> modelContextListenerRegistrations = new ArrayList<>();


    public <T, M extends T> void registerModel(Class<T> registrationType, M model) {
        registerModel(registrationType, null, model);
    }

    public <T> void registerModel(Class<? super T> registrationType, String name, T model) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(registrationType, name);

        NamedModel namedModel = new NamedModel(modelQualifier, model);

        if (models.add(namedModel)) {
            for (ModelContextListenerRegistration<?> listenerRegistration : modelContextListenerRegistrations) {
                ModelContextListener<T> modelContextListener = listenerRegistration.cast(modelQualifier);
                if (modelContextListener != null) {
                    modelContextListener.modelAdded(model);
                }
            }
            return;
        }

        String msg = MessageFormat.format("A model {0} is already registered.", modelQualifier);
        throw new IllegalArgumentException(msg);
    }

    public <T> void unregisterModel(Class<? super T> registrationType, T model) {
        unregisterModel(registrationType, null, model);
    }


    public <T> void unregisterModel(Class<? super T> registrationType, String name, T model) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(registrationType, name);
        NamedModel namedModel = new NamedModel(modelQualifier, model);

        if (models.remove(namedModel)) {
            for (ModelContextListenerRegistration<?> listenerRegistration : modelContextListenerRegistrations) {
                ModelContextListener<T> modelContextListener = listenerRegistration.cast(modelQualifier);
                if (modelContextListener != null) {
                    modelContextListener.modelRemoved(model);
                }
            }
        }
    }


    public <T> T get(Class<T> modelType) {
        return get(modelType, null);
    }

    public <T> T get(Class<T> modelType, String name) {
        ModelQualifier<T> modelQualifier = new ModelQualifier<>(modelType, name);

        return get(modelQualifier);
    }

    private <T> T get(ModelQualifier<T> modelQualifier) {
        List<T> models = findModels(modelQualifier);
        if (models.isEmpty()) {
            return null;
        }

        if (models.size() == 1) {
            return models.get(0);
        }

        String msg = MessageFormat.format("Multiple models {0} are registered.", modelQualifier);
        throw new AmbiguousObjectException(models, msg);
    }

    private <T, R extends T> List<R> findModels(ModelQualifier<T> modelQualifier) {
        List<R> matchingModels = new ArrayList<>();

        for (NamedModel namedModel : models) {
            if (namedModel.accept(modelQualifier)) {
                matchingModels.add((R) namedModel.model);
            }
        }

        return matchingModels;
    }

    public <T> void addModelContextListener(Class<? super T> modelType, ModelContextListener<T> modelContextListener) {
        addModelContextListener(modelType, null, modelContextListener);
    }

    public <T> void addModelContextListener(Class<? super T> modelType, String name, ModelContextListener<T> modelContextListener) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(modelType, name);
        ModelContextListenerRegistration<T> listenerRegistration = new ModelContextListenerRegistration<>(modelQualifier, modelContextListener);
        modelContextListenerRegistrations.add(listenerRegistration);

        List<T> models = findModels(modelQualifier);
        if (!models.isEmpty() && listenerRegistration.accept(modelQualifier)) {
            for (T model : models) {
                modelContextListener.modelAdded(model);
            }
        }
    }

    public <T> void removeModelContextListener(Class<? super T> modelType, ModelContextListener<T> modelContextListener) {
        removeModelContextListener(modelType, null, modelContextListener);
    }

    public <T> void removeModelContextListener(Class<? super T> modelType, String name, ModelContextListener<T> modelContextListener) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(modelType, name);
        ModelContextListenerRegistration<T> listenerRegistration = new ModelContextListenerRegistration<>(modelQualifier, modelContextListener);
        modelContextListenerRegistrations.remove(listenerRegistration);
    }

}
