package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basket {
    @SerializedName("BasketCode")
    @Expose
    private String BasketCode;
    @SerializedName("Qty")
    @Expose
    private int Qty;

    public Basket(String BasketCode, int Qty) {
        this.BasketCode = BasketCode;
        this.Qty = Qty;
    }

    public String getBasketCode() {
        return BasketCode;
    }

    public void setBasketCode(String basketCode) {
        BasketCode = basketCode;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
