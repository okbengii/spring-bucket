package com.beng.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * description: 用户
 * 
 * @date 2019年10月9日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    private long id;

    private String username;

    private int age;

}
