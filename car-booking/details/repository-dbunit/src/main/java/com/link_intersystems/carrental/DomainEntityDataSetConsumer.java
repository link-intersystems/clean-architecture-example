package com.link_intersystems.carrental;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.stream.DefaultConsumer;

import java.util.*;
import java.util.function.BiFunction;

public class DomainEntityDataSetConsumer extends DefaultConsumer {

    private Map<String, List<Object>> domainEntitiesByName = new HashMap<>();

    private Map<String, BiFunction<ITableMetaData, Object[], Object>> domainEntityMappers = new HashMap<>();

    private ITableMetaData metaData;

    public void addDomainEntityMapper(String tableName, BiFunction<ITableMetaData, Object[], Object> domainEntityMapper) {
        domainEntityMappers.put(tableName, domainEntityMapper);
    }

    @Override
    public void startTable(ITableMetaData metaData) throws DataSetException {
        this.metaData = metaData;
    }

    @Override
    public void row(Object[] values) throws DataSetException {
        String tableName = metaData.getTableName();
        BiFunction<ITableMetaData, Object[], Object> domainEntityMapper = domainEntityMappers.get(tableName);
        if (domainEntityMapper != null) {
            Object domainEntity = domainEntityMapper.apply(metaData, values);
            if (domainEntity != null) {
                List<Object> domainEntities = domainEntitiesByName.computeIfAbsent(tableName, k -> new ArrayList<>());
                domainEntities.add(domainEntity);
            }
        }
        super.row(values);
    }

    public <T> List<T> getDomainEntities(String name) {
        return (List<T>) domainEntitiesByName.getOrDefault(name, Collections.emptyList());
    }
}
