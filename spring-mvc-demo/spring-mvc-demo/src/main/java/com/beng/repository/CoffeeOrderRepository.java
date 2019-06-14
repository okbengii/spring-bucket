package com.beng.repository;

import com.beng.model.CoffeeOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}