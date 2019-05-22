### 使用 Oauth2.0 验证登录

- Oauth2.0
- Spring Security


#### pom 依赖

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.security.oauth</groupId>
	<artifactId>spring-security-oauth2</artifactId>
	<version>2.0.14.RELEASE</version>
</dependency>
```

这里封装了几个接口：

- {[/oauth/token],methods=[GET]}
- {[/oauth/token],methods=[POST]} 
- {[/oauth/authorize]}
- {[/oauth/check_token]}
- {[/oauth/confirm_access]}


#### oauth2:

##### 配置资源管理器
	
	ResourceServerConfig
	```
	@Configuration
	@EnableResourceServer
	public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    		@Override
    		public void configure(HttpSecurity http) throws Exception {
    			// 这里使用通配符拦截所有的请求都要 Oauth 认证 
        		http.authorizeRequests().antMatchers("/**").authenticated().anyRequest().authenticated();
	   	}
	}
	```

##### 认证服务器

	OauthServerConfig
	
	```
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
	        // withClient --> clientId
	        // authorizedGrantTypes --> 验证类型
	        // secret --> oauth 验证服务器密码
	        clients.inMemory().withClient("abc").authorizedGrantTypes("client_credentials", "refresh_token", "password")
	                .scopes("select", "read").authorities("client").secret("123456");
	    }
	    @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints.authenticationManager(authenticationManager);
	        endpoints.tokenStore(tokenStore());
	        endpoints.userDetailsService(userService);
	        endpoints.setClientDetailsService(clientDetails);
	        // 配置TokenServices参数
	        DefaultTokenServices tokenServices = new DefaultTokenServices();
	        tokenServices.setTokenStore(endpoints.getTokenStore());
	        tokenServices.setSupportRefreshToken(true);
	        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
	        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
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
	```
	
oauth2根据使用场景不同，分成了4种模式

- 授权码模式（authorization code）
- 简化模式（implicit）
- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）
	
	
	
scheme.sql 存储的是 Oauth2.0 用到的表






