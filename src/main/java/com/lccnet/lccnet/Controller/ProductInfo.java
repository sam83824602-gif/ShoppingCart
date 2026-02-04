package com.lccnet.lccnet.Controller;

public class ProductInfo {

    String title;
    String productImg;
    String productName;
    int price;
    String[] introductionList;
    String[] description;
    String[] deliver;
    String[] pay;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String[] getIntroductionList() {
        return introductionList;
    }

    public void setIntroductionList(String introductionList) {
        this.introductionList = introductionList.split("\r\n");
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.split("\r\n");
    }

    public String[] getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver.split("\r\n");
    }

    public String[] getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay.split("\r\n");
    }

}
