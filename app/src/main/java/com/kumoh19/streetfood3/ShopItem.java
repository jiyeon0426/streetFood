package com.kumoh19.streetfood3;

public class ShopItem {
    String shopName;
    String shopAddress;
    String shopCategory;

    public ShopItem(String shopName, String shopAddress, String shopCategory) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopCategory = shopCategory;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopName() {
        return shopName;
    }

    public void setshopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
    public String getshopAddress() {
        return shopAddress;
    }

    public void setshopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }
    public String getshopCategory() {
        return shopCategory;
    }
}
