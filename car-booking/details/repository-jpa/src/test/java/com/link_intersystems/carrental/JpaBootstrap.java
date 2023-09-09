package com.link_intersystems.carrental;

import com.link_intersystems.carrental.booking.JpaCarBooking;
import com.link_intersystems.carrental.booking.JpaCustomer;
import com.link_intersystems.carrental.offer.JpaCar;
import com.link_intersystems.carrental.offer.JpaRentalCar;
import com.link_intersystems.jdbc.test.db.h2.H2Database;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.service.ServiceRegistry;

import javax.sql.DataSource;


public class JpaBootstrap {

    private final DataSource dataSource;

    public JpaBootstrap(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EntityManagerFactory create() {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
        connectionProvider.setDataSource(dataSource);
        registryBuilder.applySetting(AvailableSettings.CONNECTION_PROVIDER, connectionProvider);
        ServiceRegistry standardRegistry = registryBuilder.build();

        MetadataSources sources = new MetadataSources(standardRegistry);

        sources.addAnnotatedClass(JpaCarBooking.class);
        sources.addAnnotatedClass(JpaCustomer.class);
        sources.addAnnotatedClass(JpaCar.class);
        sources.addAnnotatedClass(JpaRentalCar.class);

        MetadataBuilder metadataBuilder = sources.getMetadataBuilder();

        metadataBuilder.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);

        metadataBuilder.applyImplicitSchemaName("BOOKING");

        Metadata metadata = metadataBuilder.build();

        SessionFactoryBuilder sessionFactoryBuilder = metadata.getSessionFactoryBuilder();

        SessionFactory sessionFactory = sessionFactoryBuilder.build();
        return sessionFactory;
    }
}
