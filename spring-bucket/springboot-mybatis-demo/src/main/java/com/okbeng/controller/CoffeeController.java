package com.okbeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.okbeng.dto.R;
import com.okbeng.service.CoffeeService;

/**
 * 根据咖啡名称获取
 * 
 * @author apple
 */
@Controller
@RequestMapping("/api/v1")
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    @RequestMapping("/coffee")
    @ResponseBody
    public R getCoffeeByName(@RequestParam("name") String name) {
        return R.ok().put(coffeeService.getCoffeeByName(name));
    }
}
