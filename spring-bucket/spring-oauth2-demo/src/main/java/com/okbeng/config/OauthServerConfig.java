package com.okbeng.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.alibaba.druid.pool.DruidDataSource;
import com.okbeng.service.UserService;

/**
 * @desc 认证服务器： 进行 oauth 认证
 * @author apple
 */
@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DruidDataSource druidDataSource;
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientDetailsService clientDetails;

    // 声明TokenStore实现
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(druidDataSource);
    }

    // 声明 ClientDetails实现
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(druidDataSource);
    }

    /**
     * 配置客户端的一些信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 从数据库中读取配置信息
        // clients.jdbc(druidDataSource);
        // 自己在内存中配置
        clients.inMemory().withClient("abc").authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("select", "read").authorities("client").secret("123456");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        // token 存放位置，这里存放到了数据库中，一般是使用redis
        endpoints.tokenStore(tokenStore());
        endpoints.userDetailsService(userService);
        endpoints.setClientDetailsService(clientDetails);
        // 配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 设置token的过期时间
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
        endpoints.tokenServices(tokenServices);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }
}
