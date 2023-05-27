package com.link_intersystems.carrental.swing.context;

import java.text.MessageFormat;
import java.util.*;

import static java.util.Objects.*;

public class ModelContext {

    private static class ModelContextListenerRegistration<T> {

        private ModelQualifier<? super T> modelQualifier;
        private ModelContextListener<T> modelContextListener;

        public ModelContextListenerRegistration(ModelQualifier<? super T> modelQualifier, ModelContextListener<T> modelContextListener) {
            this.modelQualifier = requireNonNull(modelQualifier);
            this.modelContextListener = requireNonNull(modelContextListener);
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

    private Map<ModelQualifier<?>, Object> modelsByQualifier = new HashMap<>();

    private List<ModelContextListenerRegistration<?>> modelContextListenerRegistrations = new ArrayList<>();

    public <T, M extends T> void registerModel(Class<T> registrationType, M model) {
        registerModel(registrationType, null, model);
    }

    public <T> void registerModel(Class<? super T> registrationType, String name, T model) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(registrationType, name);

        if (modelsByQualifier.put(modelQualifier, model) == null) {
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

    public <T> void unregisterModel(Class<? super T> registrationType) {
        T model = (T) get(registrationType);
        unregisterModel(registrationType, null, model);
    }

    public <T> void unregisterModel(Class<? super T> registrationType, T model) {
        unregisterModel(registrationType, null, model);
    }

    public <T> void unregisterModel(Class<? super T> registrationType, String name, T model) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(registrationType, name);

        if (modelsByQualifier.remove(modelQualifier) != null) {
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
        return get(new ModelQualifier<T>(modelType, name));
    }

    public <T> T get(ModelQualifier<T> modelQualifier) {
        return (T) modelsByQualifier.get(requireNonNull(modelQualifier));
    }

    public <T> void addModelContextListener(Class<? super T> modelType, ModelContextListener<T> modelContextListener) {
        addModelContextListener(modelType, null, modelContextListener);
    }

    public <T> void addModelContextListener(Class<? super T> modelType, String name, ModelContextListener<T> modelContextListener) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(modelType, name);

        addModelContextListener(modelQualifier, modelContextListener);
    }

    public <T> void addModelContextListener(ModelQualifier<? super T> modelQualifier, ModelContextListener<T> modelContextListener) {
        ModelContextListenerRegistration<T> listenerRegistration = new ModelContextListenerRegistration<>(modelQualifier, modelContextListener);
        modelContextListenerRegistrations.add(listenerRegistration);

        if (listenerRegistration.accept(modelQualifier)) {
            T model = (T) get(modelQualifier);
            if (model != null) {
                modelContextListener.modelAdded(model);
            }
        }
    }

    public <T> void removeModelContextListener(Class<? super T> modelType, ModelContextListener<T> modelContextListener) {
        removeModelContextListener(modelType, null, modelContextListener);
    }

    public <T> void removeModelContextListener(Class<? super T> modelType, String name, ModelContextListener<T> modelContextListener) {
        ModelQualifier<? super T> modelQualifier = new ModelQualifier<>(modelType, name);
        removeModelContextListener(modelQualifier, modelContextListener);
    }

    public <T> void removeModelContextListener(ModelQualifier<? super T> modelQualifier, ModelContextListener<T> modelContextListener) {
        ModelContextListenerRegistration<T> listenerRegistration = new ModelContextListenerRegistration<>(modelQualifier, modelContextListener);
        modelContextListenerRegistrations.remove(listenerRegistration);
    }

}
