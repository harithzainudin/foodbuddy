package com.example.foodbuddy;

public class OrderObject {
    public String shopname;
    public String orderdesc;
    public Double price;
    public String shopimg;

    public String getShopimg() {
        return shopimg;
    }

    public void setShopimg(String shopimg) {
        this.shopimg = shopimg;
    }

    public OrderObject(){
    }

    public OrderObject(String shopname, String orderdesc, Double price, String shopimg){
        this.shopname = shopname;
        this.orderdesc = orderdesc;
        this.price = price;
        this.shopimg = shopimg;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getOrderdesc() {
        return orderdesc;
    }

    public void setOrderdesc(String orderdesc) {
        this.orderdesc = orderdesc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

