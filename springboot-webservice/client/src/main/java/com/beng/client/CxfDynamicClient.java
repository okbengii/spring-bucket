package com.beng.client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.BeanUtils;

import com.beng.interceptor.LoginInterceptor;
import com.beng.model.User;

/**
 * description: 动态调用方式
 * 
 * @date 2019年10月9日
 */
public class CxfDynamicClient {

    private static String address = "http://localhost:8888/services/user?wsdl";

    public static void invokeRemoteService() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(address);
        // 需要密码的情况需要加上用户名和密码
        client.getOutInterceptors().add(new LoginInterceptor("root", "admin"));
        Object[] objects = new Object[0];
        try {
            System.out.println("======client" + client);
            objects = client.invoke("getUserNameById", "1");
            User user = new User();
            BeanUtils.copyProperties((objects[0]), user);
            System.out.println("返回数据:" + user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        invokeRemoteService();
    }
}
