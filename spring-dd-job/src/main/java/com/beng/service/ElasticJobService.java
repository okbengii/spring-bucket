package com.beng.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.beng.handler.ElasticJobHandler;

@Service
public class ElasticJobService {
    @Resource
    private ElasticJobHandler jobHandler;

    public void scanAddJob() {
        for (int i = 0; i < 10; ++i) {
            String jobName = "job_" + i;
            String cron;
            jobHandler.addJob(jobName, "0/5 * * * * ?", 1, String.valueOf(i));
        }

    }
}
