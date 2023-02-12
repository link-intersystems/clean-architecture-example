package com.link_intersystems;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityFixture<E> extends AbstractList<E> {

    private List<E> entities;

    protected abstract void init(List<E> entities);

    private List<E> getEntities() {
        if (entities == null) {
            entities = new ArrayList<>();
            init(entities);
        }
        return entities;
    }

    @Override
    public E get(int index) {
        return getEntities().get(index);
    }

    @Override
    public int size() {
        return getEntities().size();
    }
}
