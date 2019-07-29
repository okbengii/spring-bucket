package com.beng.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
public class ElasticJobConfig {

    @Value("${elasticjob.zookeeper.list}")
    private String serverlists;
    @Value("${elasticjob.zookeeper.namespace}")
    private String namespace;

    /**
     * zookeeper config
     * 
     * @return
     */
    @Bean
    public ZookeeperConfiguration zkConfig() {
        return new ZookeeperConfiguration(serverlists, namespace);
    }

    /**
     * 初始化注册
     * 
     * @param config
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(ZookeeperConfiguration config) {
        return new ZookeeperRegistryCenter(config);
    }

    /**
     * job 数据库存储配置
     * 
     * @return
     */
    // @Bean
    // public JobEventConfiguration jobEventConfiguration() {
    // return new JobEventRdbConfiguration(null);
    // }

    @Bean
    public ElasticJobListener elasticJobListener() {
        return new MyElasticJobListener(100, 100);
    }
}
