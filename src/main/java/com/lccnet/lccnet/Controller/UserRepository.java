package com.lccnet.lccnet.Controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

@Service
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public UserRepository(JdbcTemplate jdbcTemplate) { // 注入 JdbcTemplate
    this.jdbcTemplate = jdbcTemplate;
  }

  public ProductInfo getInfoByName(String name) {
    String sql = "SELECT title, productImg, productName,price,introductionList,description,deliver,pay FROM productinfo WHERE name = ?";

    return jdbcTemplate.queryForObject(sql, (rs, row) -> {
      ProductInfo productInfo = new ProductInfo();

      String title = rs.getString("title");
      String productImg = rs.getString("productImg");
      String productName = rs.getString("productName");
      int price = rs.getInt("price");
      String introductionList = rs.getString("introductionList");
      String description = rs.getString("description");
      String deliver = rs.getString("deliver");
      String pay = rs.getString("pay");

      productInfo.setTitle(title);
      productInfo.setProductImg(productImg);
      productInfo.setProductName(productName);
      productInfo.setPrice(price);

      productInfo.setDeliver(deliver);
      productInfo.setDescription(introductionList);
      productInfo.setIntroductionList(description);
      productInfo.setPay(pay);

      return productInfo;
    }, name);
  }

  public void writeAccount(Account account) throws DuplicateKeyException {

    String sql = "SELECT COUNT(*) FROM account WHERE userID = ?";

    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, account.getUserID());
    if (count == 0) {
      String sqlInsertData = "INSERT INTO account (userID, password,email,first_name,last_name) VALUES (?, ?, ?, ?, ?)";
      jdbcTemplate.update(sqlInsertData, account.getUserID(), account.getPassword(), account.getEmail(),
          account.getFirstName(),
          account.getLastName());
    } else {
      throw new DuplicateKeyException("ID " + account.getUserID() + " already exists!");
    }

  }

  public IndexProductList getIndexProductList() throws DuplicateKeyException {

    String sqlWhey = "SELECT * FROM wheylist";
    // 使用 RowMapper 將結果映射為 Java 物件

    IndexProductList indexProductList = new IndexProductList();

    List<IndexProduct> wheyList = jdbcTemplate.query(sqlWhey, (rs, rowNum) -> {
      IndexProduct wheyIndexProduct = new IndexProduct();

      String herf = rs.getString("herf");
      String productName = rs.getString("productName");
      String imgsrc = rs.getString("imgsrc");

      wheyIndexProduct.setHref(herf);
      wheyIndexProduct.setImgsrc(imgsrc);
      wheyIndexProduct.setName(productName);
      return wheyIndexProduct;
    });

    
    String sqlBcaa = "SELECT * FROM bcaalist";
    List<IndexProduct> bcaaList = jdbcTemplate.query(sqlBcaa, (rs, rowNum) -> {
      IndexProduct bcaaIndexProduct = new IndexProduct();

      String herf = rs.getString("herf");
      String name = rs.getString("productName");
      String imgsrc = rs.getString("imgsrc");

      bcaaIndexProduct.setHref(herf);
      bcaaIndexProduct.setImgsrc(imgsrc);
      bcaaIndexProduct.setName(name);
      return bcaaIndexProduct;
    });

    indexProductList.setWheyList(wheyList);
    indexProductList.setBcaaList(bcaaList);
    return indexProductList;
  }

}
