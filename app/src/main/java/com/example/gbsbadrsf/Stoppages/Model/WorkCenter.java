package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkCenter {

    @SerializedName("workCenterId")
    @Expose
    private Integer workCenterId;
    @SerializedName("workCenterName")
    @Expose
    private String workCenterName;

    public Integer getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(Integer workCenterId) {
        this.workCenterId = workCenterId;
    }

    public String getWorkCenterName() {
        return workCenterName;
    }

    public void setWorkCenterName(String workCenterName) {
        this.workCenterName = workCenterName;
    }

    @Override
    public String toString() {
        return workCenterName;
    }
}
