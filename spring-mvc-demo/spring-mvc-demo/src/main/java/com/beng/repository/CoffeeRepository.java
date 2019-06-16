package com.beng.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.beng.model.Coffee;

/**
 * Coffee Repository
 */
@RepositoryRestResource(collectionResourceRel = "coffee", path = "/coffee")
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> list);

    @Query(value = "select id,name,price,create_time,update_time from t_coffee c where c.name = ?1", nativeQuery = true)
    Coffee findCoffeeByName(@Param(value = "name") String name);
}