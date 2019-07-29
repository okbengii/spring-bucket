package com.beng.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.beng.configure.MyElasticJobListener;
import com.beng.job.MyJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Component
public class ElasticJobHandler {
    @Resource
    private ZookeeperRegistryCenter registryCenter;
    // @Resource
    // private JobEventConfiguration jobEventConfiguration;
    @Resource
    private MyElasticJobListener elasticJobListener;

    private static LiteJobConfiguration.Builder simpleJobConfigBuilder(String jobName,
            Class<? extends SimpleJob> jobClass, int shardingTotalCount, String cron, String id) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).jobParameter(id).build(),
                jobClass.getCanonicalName()));
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName
     *            任务名
     * @param cron
     *            表达式
     * @param shardingTotalCount
     *            分片数
     */
    public void addJob(String jobName, String cron, Integer shardingTotalCount, String id) {
        LiteJobConfiguration jobConfig = simpleJobConfigBuilder(jobName, MyJob.class, shardingTotalCount, cron, id)
                .overwrite(true).build();
        new JobScheduler(registryCenter, jobConfig, new ElasticJobListener[] {}).init();
    }
}
