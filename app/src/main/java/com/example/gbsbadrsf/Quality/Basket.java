package com.example.gbsbadrsf.Quality;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basket {

    @SerializedName("basketCode")
    @Expose
    private String basketCode;
    @SerializedName("qty")
    @Expose
    private Integer qty;

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
