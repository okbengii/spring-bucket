package com.beng.service;

import java.util.List;

import com.beng.model.Coffee;
import com.beng.repository.CoffeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // 缓存用在更新/插入比较少的情况
    @Cacheable(cacheNames = "coffeeList") // 缓存结果
    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    @Cacheable(cacheNames = "coffee", key = "#name")
    public Coffee getCoffeeByName(String name) {
        return coffeeRepository.findCoffeeByName(name);
    }

    @CachePut(cacheNames="coffee",key = "#result.name")
    public Coffee updateCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }
    @CacheEvict(cacheNames = "coffee")
    public void delete(String name){
    };

    @CacheEvict(cacheNames = "coffeeList")
    public void reload() {

    }

    public List<Coffee> getCoffeeByName(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }
}