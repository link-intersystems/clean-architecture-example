package com.link_intersystems.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import javax.sql.DataSource;
import java.util.*;


public class JpaConfig {

    private final DataSource dataSource;
    private EntityManagerFactory entityManagerFactory;


    public JpaConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EntityManagerFactory getEntityManagerFactory(String schema,  Class<?> ... managedClasses) {
        if (entityManagerFactory != null) {
            return entityManagerFactory;
        }

        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(
                schema, dataSource, schema, Arrays.asList(managedClasses).stream().map(Class::getName).toList());

        Map<String, Object> configuration = new HashMap<>();
        EntityManagerFactoryBuilderImpl entityManagerFactoryBuilder = new EntityManagerFactoryBuilderImpl(
                new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration);
        entityManagerFactoryBuilder.withDataSource(dataSource);
        entityManagerFactory = entityManagerFactoryBuilder
                .build();
        return entityManagerFactory;
    }

    protected PersistenceUnitInfo getPersistenceUnitInfo(String name, DataSource dataSource, String schema, List<String> managedClasses) {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DEFAULT_SCHEMA, schema);
        DefaultPersistenceUnitInfo persistenceUnitInfo = new DefaultPersistenceUnitInfo(name, dataSource);
        managedClasses.forEach(persistenceUnitInfo::addManagedClassName);
        persistenceUnitInfo.setProperties(properties);
        return persistenceUnitInfo;
    }

}