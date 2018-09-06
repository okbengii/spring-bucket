package com.okbeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BaseController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/user")
    public String getUserById() {
        return restTemplate.getForObject("http://provider8081/api/user", String.class);
    }

}
