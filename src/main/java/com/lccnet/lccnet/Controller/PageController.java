package com.lccnet.lccnet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PageController {

    @Autowired
    private JdbcTemplate jdbcTemplate1;

    // private JdbcTemplate jdbcTemplate2;

    @GetMapping("/")
    public String home() {

        return "index"; // 對應 templates/index.html
    }

    @GetMapping("/product")
    public String products() {
        return "product"; // 對應 templates/products.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // 對應 templates/products.html
    }

    @GetMapping("/Register")
    public String Register() {
        return "Register"; // 對應 templates/products.html
    } 
    @GetMapping("/accountInfo")
    public String accountInfo() {
        return "accountInfo"; // 對應 templates/products.html
    }

    @ResponseBody
    @GetMapping(path = "/api/product/{name}", produces = "application/json")
    public String GetProduct(@PathVariable String name) {

        String info = "";

        // DataSourceConfig sdf = new DataSourceConfig();
        // DataSource das = sdf.primaryDataSource();
        // UserRepository dRepository = new UserRepository(jdbcTemplate1);
        // UserRepository dRepository = new UserRepository();
        UserRepository dRepository = new UserRepository(jdbcTemplate1);
        ProductInfo productInfo = dRepository.getInfoByName(name);
        try {
            ObjectMapper mapper = new ObjectMapper();
            info = mapper.writeValueAsString(productInfo); // 轉成json字串

            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("fail");
        }

        return info; // 對應 templates/products.html
    }

    @ResponseBody
    @GetMapping(path = "/api/ItemList/", produces = "application/json")
    public String GetItemList() {

        UserRepository dRepository = new UserRepository(jdbcTemplate1);
        IndexProductList indexProductList = dRepository.getIndexProductList();

        String strIndexProductList = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            strIndexProductList = mapper.writeValueAsString(indexProductList); // 轉成json字串
            System.out.println(strIndexProductList);
            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("fail");
        }
        System.out.println("id");
        return strIndexProductList; // 對應 templates/products.html
    }

    

}
