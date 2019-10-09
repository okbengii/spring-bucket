package com.beng.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.beng.interceptor.LoginInterceptor;
import com.beng.model.User;
import com.beng.service.IService;

public class CxfDefaultClient {
    private static String address = "http://localhost:8888/services/user?wsdl";

    public static void invokeRemoteService() {
        try {
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            // 添加用户名密码拦截器
            jaxWsProxyFactoryBean.getOutInterceptors().add(new LoginInterceptor("root", "admin"));
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(IService.class);
            // 创建一个代理接口实现
            IService cs = (IService) jaxWsProxyFactoryBean.create();
            // 数据准备
            String LineId = "1";
            // 调用代理接口的方法调用并返回结果
            User result = (User) cs.getUser(LineId);
            System.out.println("==============返回结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        invokeRemoteService();
    }
}
