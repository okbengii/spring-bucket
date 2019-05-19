package com.okbeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.okbeng.mapper.CoffeeMapper;
import com.okbeng.model.Coffee;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@ComponentScan
@MapperScan("com.okbeng.mapper")
// 如果要是想 springboot 项目执行完成后，执行某些方法，可以去实现 ApplicationRunner 接口
public class Application implements ApplicationRunner {

    @Autowired
    private CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee c = Coffee.builder().name("espresso").price(20.0).build();
        int count = coffeeMapper.save(c);
        log.info("Save {} Coffee: {}", count, c);

        c = Coffee.builder().name("latte").price(25.0).build();
        count = coffeeMapper.save(c);
        log.info("Save {} Coffee: {}", count, c);

        c = coffeeMapper.findById(c.getId());
        log.info("Find Coffee: {}", c);
    }
}
