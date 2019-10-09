package com.beng.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beng.interceptor.AuthInterceptor;
import com.beng.service.IService;
import com.beng.service.impl.UserServiceImpl;

/**
 * description: 配置类
 * 
 * @date 2019年10月9日
 */
@Configuration
public class CxfConfig {

    // 默认servlet路径 /*,如果覆写则按照自己定义的来,注册一个 CXFServlet
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    // 把实现类交给spring管理
    @Bean
    public IService appService() {
        return new UserServiceImpl();
    }

    // 终端路径
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), appService());
        endpoint.getInInterceptors().add(new AuthInterceptor());// 添加校验拦截器
        // 发布
        endpoint.publish("/user");
        return endpoint;
    }
}
