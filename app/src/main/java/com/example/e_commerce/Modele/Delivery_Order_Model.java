package com.example.e_commerce.Modele;

import java.util.ArrayList;

public class Delivery_Order_Model {
    String OdrderID ;
    String orderElements = "" ;
    String date ;
    String time ;
    String TotalPrice ;
    String ProductsNumber ;
    String userEmail;

    public Delivery_Order_Model(){
    }

    public Delivery_Order_Model(String odrderID, String date, String time, String totalPrice, String productsNumber, String userEmail) {
        this.OdrderID = odrderID;
        this.date = date;
        this.time = time;
        this.TotalPrice = totalPrice;
        this.ProductsNumber = productsNumber;
        this.userEmail = userEmail;
    }

    public String getOdrderID() {
        return OdrderID;
    }

    public void setOdrderID(String odrderID) {
        OdrderID = odrderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getProductsNumber() {
        return ProductsNumber;
    }

    public void setProductsNumber(String productsNumber) {
        ProductsNumber = productsNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
