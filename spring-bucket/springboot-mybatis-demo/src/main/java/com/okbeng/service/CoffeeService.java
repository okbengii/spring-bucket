package com.okbeng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okbeng.mapper.CoffeeMapper;
import com.okbeng.model.Coffee;

/**
 * @author apple
 */
@Service
public class CoffeeService {

    @Autowired
    CoffeeMapper coffeeMapper;

    public Coffee getCoffeeByName(String name) {
        return coffeeMapper.findByName(name);
    }
}
