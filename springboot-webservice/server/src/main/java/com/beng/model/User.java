package com.beng.model;

import lombok.Builder;
import lombok.Data;

/**
 * description: 用户
 * 
 * @date 2019年10月9日
 */
@Data
@Builder
public class User {

    private long id;

    private String username;

    private int age;

}
