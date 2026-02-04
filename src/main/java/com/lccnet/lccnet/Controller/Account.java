package com.lccnet.lccnet.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private String userID = "";
    private String password = "";
    private String passwordCheck = "";
    private String email = "";
    private String lastName = "";
    private String firstName = "";

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String eamil) {
        this.email = eamil;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLast_name(String lastName) {
        this.lastName = lastName;
    }

    public Boolean emailExist(JdbcTemplate jdbcTemplate) {
        Boolean bIsExist = false;

        String sql = "SELECT COUNT(*) FROM account WHERE email = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, this.email);
        if (count > 0)
            bIsExist = true;

        return bIsExist;
    }

    public Boolean accountExist(JdbcTemplate jdbcTemplate) {
        Boolean bIsExist = false;

        String sql = "SELECT COUNT(*) FROM account WHERE userID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, this.getUserID());
        if (count == 1)
            bIsExist = true;

        return bIsExist;
    }

    public Boolean passWordEquals() {
        return this.password.equals(this.passwordCheck);
    }

    public Boolean passWordCheck(JdbcTemplate jdbcTemplate) {
        Boolean bIsEquals = false;
        String sql = "SELECT  password FROM account WHERE userID = ?";
        Account sqlAccount = jdbcTemplate.queryForObject(sql, (rs, row) -> {
            Account account = new Account();
            String password = rs.getString("password");
            account.setPassword(password);
            return account;
        }, this.userID);
        bIsEquals = this.password.equals(sqlAccount.getPassword());
        return bIsEquals;
    }

    public void createCartTabel(String url, String user, String password) throws Exception {
        String sql = "CREATE TABLE " + userID +
                "(productName VARCHAR(50), " +
                " number INTEGER, " +
                " PRIMARY KEY ( productName ))";
                
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        System.out.println("user:" + userID + "資料表建立成功....！");

    }
}
