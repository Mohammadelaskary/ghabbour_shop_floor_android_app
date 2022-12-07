package com.example.gbsbadrsf.Model;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Util.Lang;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("departmentEnName")
    @Expose
    private String departmentEnName;
    @SerializedName("departmentArName")
    @Expose
    private String departmentArName;
    private String lang;
    public Department(Integer departmentId, String departmentEnName, String departmentArName) {
        this.departmentId = departmentId;
        this.departmentEnName = departmentEnName;
        this.departmentArName = departmentArName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentEnName() {
        return departmentEnName;
    }

    public void setDepartmentEnName(String departmentEnName) {
        this.departmentEnName = departmentEnName;
    }

    public String getDepartmentArName() {
        return departmentArName;
    }

    public void setDepartmentArName(String departmentArName) {
        this.departmentArName = departmentArName;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        if (lang.equals("en"))
            return departmentEnName;
        else
            return departmentArName;
    }
}
