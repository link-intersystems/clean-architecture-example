package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.booking.JpaCarBooking;
import com.link_intersystems.carrental.booking.JpaCustomer;
import com.link_intersystems.carrental.offer.JpaCar;
import com.link_intersystems.carrental.offer.JpaRentalCar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import javax.sql.DataSource;
import java.net.URL;
import java.util.*;

public class JpaEntityManagerFactory {

    public EntityManagerFactory getEntityManagerFactory(DataSource dataSource) {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(
                getClass().getSimpleName(), dataSource);
        Map<String, Object> configuration = new HashMap<>();
        EntityManagerFactoryBuilderImpl entityManagerFactoryBuilder = new EntityManagerFactoryBuilderImpl(
                new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration);
        entityManagerFactoryBuilder.withDataSource(dataSource);
        return entityManagerFactoryBuilder
                .build();
    }

    protected PersistenceUnitInfo getPersistenceUnitInfo(String name, DataSource dataSource) {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return name;
            }

            @Override
            public String getPersistenceProviderClassName() {
                return HibernatePersistenceProvider.class.getName();
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return dataSource;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Arrays.asList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                return Arrays.asList();
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                List<String> managedClassNames = new ArrayList<>();
                managedClassNames.add(JpaRentalCar.class.getName());
                managedClassNames.add(JpaCar.class.getName());
                managedClassNames.add(JpaCarBooking.class.getName());
                managedClassNames.add(JpaCustomer.class.getName());
                return managedClassNames;
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return true;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return SharedCacheMode.UNSPECIFIED;
            }

            @Override
            public ValidationMode getValidationMode() {
                return ValidationMode.AUTO;
            }

            @Override
            public Properties getProperties() {
                Properties properties = new Properties();
                properties.put(AvailableSettings.DEFAULT_SCHEMA, "BOOKING");
                return properties;
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return "2.1";
            }

            @Override
            public ClassLoader getClassLoader() {
                return Thread.currentThread().getContextClassLoader();
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

}