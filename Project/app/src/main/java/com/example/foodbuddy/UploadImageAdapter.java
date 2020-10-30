package com.example.foodbuddy;

public class UploadImageAdapter {

    private String mName;
    private String mImageUrl;
    private String mmenuname;
    private String mmenuprice;

    public UploadImageAdapter() {

    }

    public UploadImageAdapter(String name, String imageUrl, String menuname, String menuprice) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        mmenuname = menuname;
        mmenuprice = menuprice;
    }

    //namekedai
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    //imagemenu
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    //nemamenu
    public String getmenuname(){
        return mmenuname;
    }
    public void setmenuname(String menuname){
        mmenuname = menuname;
    }

    //pricemenu
    public String getmenuprice(){
        return mmenuprice;
    }
    public void setmenuprice(String menuprice){
        mmenuprice = menuprice;
    }
}
