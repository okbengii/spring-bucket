package com.beng;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beng.service.ElasticJobService;

//@ImportResource(value = { "classpath:application-job-config.xml" })
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Resource
    private ElasticJobService elasticJobService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        elasticJobService.scanAddJob();
    }
}
