package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jig {

    @SerializedName("jigId")
    @Expose
    private Integer jigId;
    @SerializedName("jigCode")
    @Expose
    private String jigCode;
    @SerializedName("jigName")
    @Expose
    private String jigName;

    public Integer getJigId() {
        return jigId;
    }

    public void setJigId(Integer jigId) {
        this.jigId = jigId;
    }

    public String getJigCode() {
        return jigCode;
    }

    public void setJigCode(String jigCode) {
        this.jigCode = jigCode;
    }

    public String getJigName() {
        return jigName;
    }

    public void setJigName(String jigName) {
        this.jigName = jigName;
    }

    @Override
    public String toString() {
        return jigName;
    }
}
