package com.okbeng.configuration;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.okbeng.mapper.*;com.gitee.sunchenbin.mybatis.actable.dao.*");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
}
