package com.beng.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(String[] args) {
        String sql = "insert into t_coffee (name, price) values (?, ?)";
        jdbcTemplate.update(sql, new Object[] { args[0], args[1] });
    }

    public void query() {
        String sql = "select * from t_coffee";
        System.out.println(jdbcTemplate.queryForList(sql));
    }
}
