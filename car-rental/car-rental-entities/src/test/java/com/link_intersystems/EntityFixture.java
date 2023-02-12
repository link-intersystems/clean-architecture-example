package com.link_intersystems;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityFixture<E> extends AbstractList<E> {

    private List<E> entities = new ArrayList<>();


    public EntityFixture() {
        init(entities);
    }

    protected abstract void init(List<E> entities);

    @Override
    public E get(int index) {
        return entities.get(index);
    }

    @Override
    public int size() {
        return entities.size();
    }
}
