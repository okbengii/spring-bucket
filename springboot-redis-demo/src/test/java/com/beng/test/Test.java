package com.beng.test;

import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

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

    private static SerializeConfig mapping = new SerializeConfig();
    private static String dateFormat;
    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
    }

    @org.junit.Test
    public void testJSon() {
        JSONObject json1 = new JSONObject();
        json1.put("date", new Date());
        System.out.println(json1.toJSONString());
        System.out.println(JSONObject.toJSONString(json1));
        System.out.println(toJSON(json1));
    }

    public static String toJSON(Object jsonText) {
        return JSON.toJSONString(jsonText, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 自定义时间格式
     * 
     * @param jsonText
     * @return
     */
    public static String toJSON(String dateFormat, String jsonText) {
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        return JSON.toJSONString(jsonText, mapping);
    }
}
