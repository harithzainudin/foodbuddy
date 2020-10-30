package com.example.foodbuddy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfileObject {
    public String email;
    public String password;
    public String phone;
    public String address;
    public String shopname;
    public String userimg;

    public String getShopimg() {
        return userimg;
    }

    public void setShopimg(String userimg) {
        this.userimg = userimg;
    }

    public UserProfileObject (){
        //default const
    }

    public UserProfileObject(String email,String pass,String phone,String shopname,String address, String userimg){
        this.email = email;
        this.password = pass;
        this.phone = phone;
        this.address = address;
        this.shopname = shopname;
        this.userimg = userimg;
    }

    public boolean emailRegex (String emailRegex){
        final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";//OWASP validation regex

        //initialize the Pattern object
        Pattern emailPattern = Pattern.compile(regex);

        Matcher matcher = emailPattern.matcher(emailRegex);
        return matcher.matches();
    }

    public boolean phoneRegex (String phoneRegex){
        final String regex = "^\\\\d{10}$";//OWASP validation regex

        //initialize the Pattern object
        Pattern emailPattern = Pattern.compile(regex);

        Matcher matcher = emailPattern.matcher(phoneRegex);
        return matcher.matches();
    }

}
