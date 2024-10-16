package com.example.e_commerce.Modele;

public class ItemsModel {
    String productName ;
    String Price ;
    String ImageUrl ;
    boolean Liked ;

    public ItemsModel(String productName, String price, String imageUrl, boolean liked) {
        this.productName = productName;
        Price = price;
        ImageUrl = imageUrl;
        Liked = liked;
    }

    public ItemsModel(String productName, String price, String imageUrl) {
        this.productName = productName;
        Price = price;
        ImageUrl = imageUrl;
    }

    public ItemsModel() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public boolean isLiked() {
        return Liked;
    }

    public void setLiked(boolean liked) {
        Liked = liked;
    }
}
