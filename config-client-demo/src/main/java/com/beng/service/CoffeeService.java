package com.beng.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService {

    @Value("${order.discount}")
    private String discount;

    public void test() {
        System.out.println("discount: " + discount);
    }
}
