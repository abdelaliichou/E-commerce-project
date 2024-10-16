package com.example.e_commerce.Modele;

import android.net.Uri;

import java.net.URL;

public class CategoryModel {
    String Category ;
    String imageUrl;

    public CategoryModel() {
    }

    public CategoryModel(String category, String image) {
        Category = category;
        this.imageUrl = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
