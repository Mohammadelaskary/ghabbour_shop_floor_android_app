package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Die {

    @SerializedName("dieId")
    @Expose
    private Integer dieId;
    @SerializedName("dieCode")
    @Expose
    private String dieCode;
    @SerializedName("dieName")
    @Expose
    private String dieName;

    public Integer getDieId() {
        return dieId;
    }

    public void setDieId(Integer dieId) {
        this.dieId = dieId;
    }

    public String getDieCode() {
        return dieCode;
    }

    public void setDieCode(String dieCode) {
        this.dieCode = dieCode;
    }

    public String getDieName() {
        return dieName;
    }

    public void setDieName(String dieName) {
        this.dieName = dieName;
    }

    @Override
    public String toString() {
        return dieName;
    }
}
