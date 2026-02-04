package com.lccnet.lccnet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AccountController {

    @Autowired
    private JdbcTemplate jdbcTemplate1;

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

            if (account.emailExist(jdbcTemplate1)) {
                throw new RuntimeException("email 已經被使用!!");
            }

            if (account.accountExist(jdbcTemplate1)) {
                throw new RuntimeException("帳號已經被使用");
            }

            if (!account.passWordEquals()) {
                throw new RuntimeException("密碼不相同");
            }

            UserRepository dRepository = new UserRepository(jdbcTemplate1);
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

            if (!account.accountExist(jdbcTemplate1)) {
                throw new RuntimeException("帳號不存在");
            }

            if (!account.passWordCheck(jdbcTemplate1)) {
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
    public ResponseEntity<String> getAccountInfo(@RequestBody String jsonString) {

        String strAccount = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account accountInput = mapper.readValue(jsonString, Account.class);





            String sql = "SELECT userID, password, email,first_name,last_name FROM account WHERE userID = ?";
            Account account_out = jdbcTemplate1.queryForObject(sql, (rs, row) -> {
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
        } catch (DuplicateKeyException e) {

            System.out.println("Catch Excpetion");
            throw e;
        } catch (Exception e) {
        }
        return ResponseEntity.ok(strAccount);
    }

}
