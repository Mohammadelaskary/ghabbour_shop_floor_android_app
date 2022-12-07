package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("employeeName")
    @Expose
    private String employeeName;
    @SerializedName("employeeTitle")
    @Expose
    private String employeeTitle;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("isPlanningUser")
    @Expose
    private Boolean isPlanningUser;
    @SerializedName("isProductionUser")
    @Expose
    private Boolean isProductionUser;
    @SerializedName("isQualityControlUser")
    @Expose
    private Boolean isQualityControlUser;
    @SerializedName("isWarehouseUser")
    @Expose
    private Boolean isWarehouseUser;
    @SerializedName("isHandlingUser")
    @Expose
    private Boolean isHandlingUser;
    @SerializedName("isProductionManufaturing")
    @Expose
    private Boolean isProductionManufaturing;
    @SerializedName("isProductionWelding")
    @Expose
    private Boolean isProductionWelding;
    @SerializedName("isProductionPainting")
    @Expose
    private Boolean isProductionPainting;
    @SerializedName("isQcmanufaturing")
    @Expose
    private Boolean isQcmanufaturing;
    @SerializedName("isQcwelding")
    @Expose
    private Boolean isQcwelding;
    @SerializedName("isQcpainting")
    @Expose
    private Boolean isQcpainting;
    @SerializedName("isProductionControl")
    @Expose
    private Boolean isProductionControl;
    @SerializedName("allowEditBarcode")
    @Expose
    private Boolean allowEditBarcode ;
    @SerializedName("allowPartialSignOffManufacturing")
    @Expose
    private Boolean allowPartialSignOffManufacturing;
    @SerializedName("allowPartialSignOffWelding")
    @Expose
    private Boolean allowPartialSignOffWelding;
    @SerializedName("mobileFirstLogIn")
    @Expose
    private Boolean mobileFirstLogIn;

    public Boolean getMobileFirstLogIn() {
        return mobileFirstLogIn;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeTitle() {
        return employeeTitle;
    }

    public void setEmployeeTitle(String employeeTitle) {
        this.employeeTitle = employeeTitle;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getIsPlanningUser() {
        return isPlanningUser;
    }

    public void setIsPlanningUser(Boolean isPlanningUser) {
        this.isPlanningUser = isPlanningUser;
    }

    public Boolean getIsProductionUser() {
        return isProductionUser;
    }

    public void setIsProductionUser(Boolean isProductionUser) {
        this.isProductionUser = isProductionUser;
    }

    public Boolean getIsQualityControlUser() {
        return isQualityControlUser;
    }

    public void setIsQualityControlUser(Boolean isQualityControlUser) {
        this.isQualityControlUser = isQualityControlUser;
    }

    public Boolean getIsWarehouseUser() {
        return isWarehouseUser;
    }

    public void setIsWarehouseUser(Boolean isWarehouseUser) {
        this.isWarehouseUser = isWarehouseUser;
    }

    public Boolean getIsProductionManufaturing() {
        return isProductionManufaturing;
    }

    public void setIsProductionManufaturing(Boolean isProductionManufaturing) {
        this.isProductionManufaturing = isProductionManufaturing;
    }

    public Boolean getIsProductionWelding() {
        return isProductionWelding;
    }

    public void setIsProductionWelding(Boolean isProductionWelding) {
        this.isProductionWelding = isProductionWelding;
    }

    public Boolean getIsProductionPainting() {
        return isProductionPainting;
    }

    public void setIsProductionPainting(Boolean isProductionPainting) {
        this.isProductionPainting = isProductionPainting;
    }

    public Boolean getIsQcmanufaturing() {
        return isQcmanufaturing;
    }

    public void setIsQcmanufaturing(Boolean isQcmanufaturing) {
        this.isQcmanufaturing = isQcmanufaturing;
    }

    public Boolean getIsQcwelding() {
        return isQcwelding;
    }

    public void setIsQcwelding(Boolean isQcwelding) {
        this.isQcwelding = isQcwelding;
    }

    public Boolean getIsQcpainting() {
        return isQcpainting;
    }

    public void setIsQcpainting(Boolean isQcpainting) {
        this.isQcpainting = isQcpainting;
    }

    public Boolean getHandlingUser() {
        return isHandlingUser;
    }

    public void setHandlingUser(Boolean handlingUser) {
        isHandlingUser = handlingUser;
    }

    public Boolean getProductionControl() {
        return isProductionControl;
    }

    public void setProductionControl(Boolean productionControl) {
        isProductionControl = productionControl;
    }

    public Boolean getAllowEditBarcode() {
        return allowEditBarcode;
    }

    public Boolean getAllowPartialSignOffManufacturing() {
        return allowPartialSignOffManufacturing;
    }

    public Boolean getAllowPartialSignOffWelding() {
        return allowPartialSignOffWelding;
    }
}
