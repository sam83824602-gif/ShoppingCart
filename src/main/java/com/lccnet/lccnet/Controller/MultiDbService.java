package com.lccnet.lccnet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class MultiDbService {
    @Autowired
    @Qualifier("productJdbcTemplate")
    private JdbcTemplate jdbcTemplateProduct;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate jdbcTemplateOrder;

    public JdbcTemplate getJdbcTemplateProduct() {
        return jdbcTemplateProduct;
    }

    public JdbcTemplate getJdbcTemplateOrder() {
        return jdbcTemplateOrder;
    }

    public void doSomething() {
        System.out.println(jdbcTemplateProduct);
        System.out.println(jdbcTemplateOrder);

    }

}
