package com.lccnet.lccnet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AccountController {

   
    @Autowired
    @Qualifier("productJdbcTemplate")
    private JdbcTemplate jdbcTemplateProduct;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate jdbcTemplateOrder;

    @ResponseBody
    @PostMapping("/accountRegister")
    public ResponseEntity<String> receiveJson(@RequestBody String jsonString) throws Exception {

        try {

            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(jsonString, Account.class);

            System.out.println(account.getEmail());
            System.out.println(account.getFirstName());
            System.out.println(account.getLastName());
            System.out.println(account.getPassword());
            System.out.println(account.getPasswordCheck());
            System.out.println(account.getUserID());

            UserRepository dRepository = new UserRepository(jdbcTemplateProduct);

            if (account.emailExist(jdbcTemplateProduct)) {
                throw new RuntimeException("email 已經被使用!!");
            }

            if (account.accountExist(jdbcTemplateProduct)) {
                throw new RuntimeException("帳號已經被使用");
            }

            if (!account.passWordEquals()) {
                throw new RuntimeException("密碼不相同");
            }

            dRepository.writeAccount(account);
            account.createCartTabel("jdbc:mariadb://localhost/cart", "root", "root");
            System.out.println("Success");
        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.ok("註冊成功");
    }

    @ResponseBody
    @PostMapping("/accountLogin")
    public ResponseEntity<String> accountLogin(@RequestBody String jsonString) throws Exception {

        try {

            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(jsonString, Account.class);

            System.out.println(account.getPassword());
            System.out.println(account.getUserID());

            if (!account.accountExist(jdbcTemplateProduct)) {
                throw new RuntimeException("帳號不存在");
            }

            if (!account.passWordCheck(jdbcTemplateProduct)) {
                throw new RuntimeException("密碼錯誤");
            }

            System.out.println("登入成功");
        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.ok("登入成功");
    }

    @ResponseBody
    @PostMapping("/getAccountInfo")
    public ResponseEntity<String> getAccountInfo(@RequestBody String jsonString) throws Exception {

        String strAccount = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account accountInput = mapper.readValue(jsonString, Account.class);

            String sql = "SELECT userID, password, email,first_name,last_name FROM account WHERE userID = ?";

            Account account_out = jdbcTemplateProduct.queryForObject(sql, (rs, row) -> {
                Account account_tmp = new Account();

                String userID = rs.getString("userID");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");

                account_tmp.setUserID(userID);
                account_tmp.setEmail(email);
                account_tmp.setFirstName(first_name);
                account_tmp.setLastName(last_name);
                account_tmp.setPassword(password);

                return account_tmp;
            }, accountInput.getUserID());

            strAccount = mapper.writeValueAsString(account_out);
        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.ok(strAccount);
    }

    @ResponseBody
    @PutMapping("/putPassword")
    public ResponseEntity<String> putPassword(@RequestBody String jsonString) throws Exception {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(jsonString, Account.class);

            if (!account.passWordEquals()) {
                throw new RuntimeException("密碼不相同");
            }

            String sql = "UPDATE account SET password = ? WHERE userID = ?";
            // 返回受影響的列數

            jdbcTemplateProduct.update(sql, account.getPassword(), account.getUserID());

        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.ok("密碼修改成功");
    }

    @ResponseBody
    @PutMapping("/putAccountInfo")
    public ResponseEntity<String> putAccountInfo(@RequestBody String jsonString) throws Exception {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(jsonString, Account.class);
            System.out.println(account);

            String sql = "UPDATE account SET first_name = ? ,last_name = ?, email = ? WHERE userID = ?";
            // 返回受影響的列數

            jdbcTemplateProduct.update(sql, account.getFirstName(), account.getLastName(),
                    account.getEmail(),
                    account.getUserID());

        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.ok("個人資料修改成功");
    }

    @ResponseBody
    @GetMapping("/getOrderList")
    public ResponseEntity<String> getOrderList(@RequestBody String userID) throws Exception {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(userID, Account.class);
            System.out.println(account);

            String sql = "UPDATE account SET first_name = ? ,last_name = ?, email = ? WHERE userID = ?";
            // 返回受影響的列數

            jdbcTemplateProduct.update(sql, account.getFirstName(), account.getLastName(),
                    account.getEmail(),
                    account.getUserID());

        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.ok("個人資料修改成功");
    }

}
