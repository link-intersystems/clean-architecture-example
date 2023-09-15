package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.management.AbstractJpaManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JpaListPickupCarRepositoryIntTest extends AbstractJpaManagementRepositoryTest {

    private ListPickupCarRepository repository;

    @BeforeEach
    void setUp() {
        repository = testProxy(ListPickupCarRepository.class, new JpaListPickupCarRepository(entityManager));
    }

    @Test
    void findAll() {
        List<CarRental> all = repository.findAll();

        assertEquals(1, all.size());
    }
}