package com.okbeng.model;

import java.io.Serializable;
import java.util.Date;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

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
@Table(name = "t_coffee") // 对应数据库中的表名
public class Coffee implements Serializable {

    private static final long serialVersionUID = 5199200306752426433L;

    @Column(name = "id", type = MySqlTypeConstant.INT, length = 11, isKey = true, isAutoIncrement = true)
    private Long id;
    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 111)
    private String name;
    @Column(name = "price", type = MySqlTypeConstant.DOUBLE, length = 5, decimalLength = 2)
    private Double price;
    @Column(name = "create_time", type = MySqlTypeConstant.DATETIME)
    private Date createTime;
    @Column(name = "update_time", type = MySqlTypeConstant.DATETIME)
    private Date updateTime;
}
