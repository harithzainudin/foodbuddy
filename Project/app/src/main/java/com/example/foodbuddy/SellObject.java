package com.example.foodbuddy;

public class SellObject {
    String selldesc;
    Double price;
    String shopimg;

    public String getShopimg() {
        return shopimg;
    }

    public void setShopimg(String shopimg) {
        this.shopimg = shopimg;
    }

    public SellObject(){

    }

    public SellObject(String selldesc, Double price, String shopimg){
        this.selldesc = selldesc;
        this.price = price;
        this.shopimg = shopimg;
    }

    public String getSelldesc() {
        return selldesc;
    }

    public void setSelldesc(String selldesc) {
        this.selldesc = selldesc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

