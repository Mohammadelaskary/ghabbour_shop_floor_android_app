package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoppageName {

    @SerializedName("stoppagesNameId")
    @Expose
    private Integer stoppagesNameId;
    @SerializedName("stoppagesNameName")
    @Expose
    private String stoppagesNameName;

    public Integer getStoppagesNameId() {
        return stoppagesNameId;
    }

    public void setStoppagesNameId(Integer stoppagesNameId) {
        this.stoppagesNameId = stoppagesNameId;
    }

    public String getStoppagesNameName() {
        return stoppagesNameName;
    }

    public void setStoppagesNameName(String stoppagesNameName) {
        this.stoppagesNameName = stoppagesNameName;
    }

    @Override
    public String toString() {
        return stoppagesNameName;
    }
}
