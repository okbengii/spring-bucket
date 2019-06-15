package com.beng.controller;
import java.util.List;

import javax.persistence.PostLoad;
import javax.validation.constraints.PositiveOrZero;

import com.beng.model.Coffee;
import com.beng.service.CoffeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/list")
    @ResponseBody
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping("/get/{name}")
    @ResponseBody
    public Coffee getCoffeeByName(@PathVariable(value="name") String name){
        return coffeeService.getCoffeeByName(name);
    }

    @PostMapping("/update")
    @ResponseBody
    public Coffee updateCoffeeByName(@RequestBody Coffee coffee){
        return coffeeService.updateCoffee(coffee);
    }

    @GetMapping("/delete/{name}")
    @ResponseBody
    public String deleteCoffeeByName(@PathVariable(value="name") String name){
         coffeeService.delete(name);
         return "success";
    }

    @GetMapping("/reload")
    @ResponseBody
    public String delete(){
        coffeeService.reload();
        return "success";
    }
}