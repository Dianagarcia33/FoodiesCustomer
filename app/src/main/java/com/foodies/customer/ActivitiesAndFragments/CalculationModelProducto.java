package com.foodies.customer.ActivitiesAndFragments;

import java.io.Serializable;

public class CalculationModelProducto implements Serializable {

    String key;
    String mName;
    String description;

    public CalculationModelProducto(String key, String mName,String description) {
        this.description = description;
        this.key = key;
        this.mName = mName;

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
