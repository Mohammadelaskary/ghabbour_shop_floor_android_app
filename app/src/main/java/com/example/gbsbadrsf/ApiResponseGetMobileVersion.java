package com.example.gbsbadrsf;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetMobileVersion {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;

    @SerializedName("mobileVersionNo")
    @Expose
    private Integer mobileVersionNo;
    @SerializedName("apkUrl")
    @Expose
    private String apkUrl;
    @SerializedName("apkData")
    @Expose
    private String apkData;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Integer getMobileVersionNo() {
        return mobileVersionNo;
    }

    public void setMobileVersionNo(Integer mobileVersionNo) {
        this.mobileVersionNo = mobileVersionNo;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getApkData() {
        return apkData;
    }

    public void setApkData(String apkData) {
        this.apkData = apkData;
    }
}
