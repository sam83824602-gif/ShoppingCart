package com.lccnet.lccnet.Controller;

public class Order {

    String Number = "";
    String userID = "";

    
    @Override
    public String toString() {
        return "Order [Number=" + Number + ", userID=" + userID + "]";
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
