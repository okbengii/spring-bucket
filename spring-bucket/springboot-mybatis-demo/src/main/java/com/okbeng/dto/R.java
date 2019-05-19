package com.okbeng.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果
 * 
 * @author apple
 */
public class R {

    public int code;

    public String message;

    public Map<String, Object> result = new HashMap<>();

    public R(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static R ok() {
        return new R(200, "请求成功");
    }

    public static R error(int code, String message) {
        return new R(code, message);
    }

    public R put(Object obj) {
        this.result.put("result", obj);
        return this;
    }
}
