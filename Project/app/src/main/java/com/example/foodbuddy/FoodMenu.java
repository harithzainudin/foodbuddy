package com.example.foodbuddy;

public class FoodMenu {
    private String Foodimage;
    private String Foodname;
    private String Foodprice;

    public FoodMenu(){

    }

    public FoodMenu(String gambarmenu, String hargamenu, String namamenu){
        Foodimage = gambarmenu;
        Foodname = namamenu;
        Foodprice = hargamenu;
    }

    public String getFoodimage(){
        return Foodimage;
    }

    public String getFoodname(){
        return Foodname;
    }

    public String getFoodprice(){
        return Foodprice;
    }

    public void setFoodimage(String gambarmenu){
        Foodimage = gambarmenu;
    }

    public void setFoodname(String namamenu){
        Foodname = namamenu;
    }

    public void setFoodprice(String hargamenu){
        Foodprice = hargamenu;
    }
}

