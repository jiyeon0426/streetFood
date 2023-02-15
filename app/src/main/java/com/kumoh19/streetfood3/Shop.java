package com.kumoh19.streetfood3;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Shop {

    public String shopName;
    public String shopTime;
    public String shopPrice;
    public String shopCatagory;
    public String shopInfo;
    public String shopAddress;
    public double shopLatitude;
    public double shopLongitude;

    public Shop() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Shop(String shopName, String shopTime, String shopPrice, String shopCatagory, String shopInfo, String shopAddress, double shopLatitude, double shopLongitude) {
        this.shopName = shopName;
        this.shopTime = shopTime;
        this.shopPrice = shopPrice;
        this.shopCatagory = shopCatagory;
        this.shopInfo = shopInfo;
        this.shopAddress = shopAddress;
        this.shopLatitude = shopLatitude;
        this.shopLongitude = shopLongitude;
    }
    public String getshopName() { return shopName; }

    public void setshopName(String shopName) {
        this.shopName = shopName;
    }

    public String getshopTime() { return shopTime; }

    public void setshopTime(String shopTime) {
        this.shopTime = shopTime;
    }

    public String getshopPrice() {
        return shopPrice;
    }

    public void setshopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }
    public String getshopCatagory() {
        return shopCatagory;
    }

    public void setshopCatagory(String shopCatagory) {
        this.shopCatagory = shopCatagory;
    }

    public String getshopInfo() {
        return shopInfo;
    }

    public void setshopInfo(String shopInfo) {
        this.shopInfo = shopInfo;
    }

    public String getshopAddress() {
        return shopAddress;
    }

    public void setshopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public double getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(double shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public double getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(double shopLongitude) {
        this.shopLongitude = shopLongitude;
    }



    @Override
    public String toString() {
        return "Shop{" +
                "Shop='" + shopName + '\'' +
                "Time='" + shopTime + '\'' +
                "Price='" + shopPrice + '\'' +
                "Catagory='" + shopCatagory + '\'' +
                "Info='" + shopInfo + '\'' +
                "Address='" + shopAddress + '\'' +
                '}';
    }
}

