package com.link_intersystems.carrental.management;

import com.link_intersystems.jdbc.test.db.h2.H2Database;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@CarManagementDBExtension
public class AbstractJpaManagementRepositoryTest extends AbstractManagementRepositoryTest {
    protected EntityManager entityManager;

    @BeforeEach
    void createEntityManager(H2Database database) {
        entityManager = new CarManagementJpaConfig(database).newEntityManager();
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void closeEntityManager() {
    }

    protected <T, I extends T> T testProxy(Class<T> type, I implementation) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    method.setAccessible(true);
                    return method.invoke(implementation, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                } finally {
                    entityManager.flush();
                }
            }
        });
    }
}
