package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIResponseSignin {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("userInfo")
    @Expose
    private UserInfo userInfo;
    @SerializedName("mobileVersionNo")
    @Expose
    private Integer mobileVersionNo;
    @SerializedName("apkUrl")
    @Expose
    private String apkUrl;
    @SerializedName("apkIP")
    @Expose
    private String apkIP;

    @SerializedName("apkPORT")
    @Expose
    private String apkPORT;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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

    public String getApkIP() {
        return apkIP;
    }

    public void setApkIP(String apkIP) {
        this.apkIP = apkIP;
    }

    public String getApkPORT() {
        return apkPORT;
    }

    public void setApkPORT(String apkPORT) {
        this.apkPORT = apkPORT;
    }
}
