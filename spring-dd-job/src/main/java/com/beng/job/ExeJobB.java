package com.beng.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 定时任务 A
 * 
 * @author apple
 */
public class ExeJobB implements SimpleJob {

    @Override
    public void execute(ShardingContext arg0) {
        System.out.println("JOB B is execute .");
    }

}
