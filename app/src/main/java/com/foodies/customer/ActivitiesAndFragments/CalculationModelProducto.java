package com.foodies.customer.ActivitiesAndFragments;

import java.io.Serializable;

public class CalculationModelProducto implements Serializable {

    String key;
    String mName;
    String description;
    String mCurrency;
    String mPrice;
    String mQuantity;
    String mPriceT;


    public CalculationModelProducto(String key, String mName,String description,String mCurrency,String mPrice,String mQuantity,String mPriceT) {
        this.description = description;
        this.key = key;
        this.mName = mName;
        this.mCurrency = mCurrency;
        this.mPrice = mPrice;
        this.mQuantity = mQuantity;
        this.mPriceT = mPriceT;

    }

    public String getmPriceT() {
        return mPriceT;
    }

    public void setmPriceT(String mPriceT) {
        this.mPriceT = mPriceT;
    }

    public String getmCurrency() {
        return mCurrency;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
