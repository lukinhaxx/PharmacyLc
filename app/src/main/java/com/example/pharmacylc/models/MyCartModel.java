package com.example.pharmacylc.models;

public class MyCartModel {

    String currentTime;
    String currentDate;
    String productNameproductPrice;
    String productPrice;
    String totalQuantity;
    int totalPrice;

    public MyCartModel() {
    }

    public MyCartModel(String currentTime, String currentDate, String productNameproductPrice, String productPrice, String totalQuantity, int totalPrice) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productNameproductPrice = productNameproductPrice;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getProductNameproductPrice() {
        return productNameproductPrice;
    }

    public void setProductNameproductPrice(String productNameproductPrice) {
        this.productNameproductPrice = productNameproductPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
