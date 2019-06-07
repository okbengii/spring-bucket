package com.okbeng.model;

import java.io.Serializable;
import java.util.Date;

import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 咖啡
 * 
 * @author apple
 */

@Data
@AllArgsConstructor // 构造方法
@NoArgsConstructor // 无参构造方法
@Builder // Builder 方法
@Document
public class Coffee implements Serializable {

    private static final long serialVersionUID = 5199200306752426433L;

    @Id
    private String id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
