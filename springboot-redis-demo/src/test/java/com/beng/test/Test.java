package com.beng.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

/**
 * @author apple
 */
public class Test {

    @org.junit.Test
    public void test() {

        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                String url = "http://localhost:8080/hello";
                HttpGet get = new HttpGet(url);
                CloseableHttpClient client = HttpClientBuilder.create().build();
                CloseableHttpResponse response = client.execute(get);
                response.close();
            }
        };

        int runnerCount = 10;
        // Rnner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = runner;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        try {
            // 开发并发执行数组里定义的内容
            mttr.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
