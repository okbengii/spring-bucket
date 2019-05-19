package com.okbeng.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.okbeng.model.Coffee;

/**
 * 使用注解的方式编写 sql
 * 
 * @author apple
 */
@Mapper
public interface CoffeeMapper {

    @Insert("insert into t_coffee (name, price, create_time, update_time)" + "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    @Results({ @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            // map-underscore-to-camel-case = true 可以实现一样的效果
            // @Result(column = "update_time", property = "updateTime"),
    })
    Coffee findById(@Param("id") Long id);

    @Select("select * from t_coffee where name = #{name}")
    @Results({ @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            // map-underscore-to-camel-case = true 可以实现一样的效果
            @Result(column = "update_time", property = "updateTime"), })
    Coffee findByName(@Param("name") String name);

}
