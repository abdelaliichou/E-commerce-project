package com.example.e_commerce.Modele;

public class CardItemModel {
    String name ;
    String date ;
    String time ;
    String price ;
    String totalprice ;
    String quantity ;
    String imageurl ;

    public CardItemModel() {
    }

    public CardItemModel(String name, String date, String time, String price, String totalprice, String quantity, String imageurl) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.price = price;
        this.totalprice = totalprice;
        this.quantity = quantity;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
