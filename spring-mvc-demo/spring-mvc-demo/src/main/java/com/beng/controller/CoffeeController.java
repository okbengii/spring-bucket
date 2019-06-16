package com.beng.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.beng.controller.exception.FormValidationException;
import com.beng.controller.request.NewCoffeeRequest;
import com.beng.model.Coffee;
import com.beng.service.CoffeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    /**
     * 可以返回xml; produces 定义返回结果 accept
     * 
     * @param name
     * @return
     */
    @GetMapping(path = "/get/{name}", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public Coffee getCoffeeByName(@PathVariable(value = "name") String name) {
        return coffeeService.getCoffeeByName(name);
    }

    @PostMapping("/update")
    @ResponseBody
    public Coffee updateCoffeeByName(@RequestBody Coffee coffee) {
        return coffeeService.updateCoffee(coffee);
    }

    /**
     * 1. BindingResult 获取校验失败的结果
     * 
     * 2. 抛出自定义异常 FormValidationException
     * 
     * @param newCoffee
     * @param result
     * @return
     */
    @PostMapping(path = "/savev2", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffee(@Valid NewCoffeeRequest newCoffee, BindingResult result) {
        if (result.hasErrors()) {
            // 这里先简单处理一下，后续讲到异常处理时会改
            log.warn("Binding Errors: {}", result);
            throw new FormValidationException(result);
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    /**
     * 1. consumes 表示接受的请求必须是表单形式 application/x-www-form-urlencoded；同时直接用
     * 2. NewCoffeeRequest映射，其实使用了 @RequestParams
     * 3. ValidationException 抛出该异常，并通过 @ExceptionHandler 捕获该异常 
     * @param newCoffee
     * @return
     */
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest newCoffee, BindingResult result) {
        if (result.hasErrors()) {
            // 这里先简单处理一下，后续讲到异常处理时会改
            log.warn("Binding Errors: {}", result);
            throw new ValidationException(result.toString());
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @GetMapping("/delete/{name}")
    @ResponseBody
    public String deleteCoffeeByName(@PathVariable(value = "name") String name) {
        coffeeService.delete(name);
        return "success";
    }

    @GetMapping("/reload")
    @ResponseBody
    public String delete() {
        coffeeService.reload();
        return "success";
    }

    @PostMapping(path = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String str;
                while ((str = reader.readLine()) != null) {
                    log.info("file content: {}", str);
                }
            } catch (IOException e) {
                log.error("exception", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }
}