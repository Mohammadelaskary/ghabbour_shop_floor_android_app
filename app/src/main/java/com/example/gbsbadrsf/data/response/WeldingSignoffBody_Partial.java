package com.example.gbsbadrsf.data.response;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeldingSignoffBody_Partial {
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("ProductionStationCode")
    @Expose
    private String productionStationCode;
    @SerializedName("IsBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("BasketList")
    @Expose
    private List<Basketcodelst> basketLst = null;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public WeldingSignoffBody_Partial(Integer userID, String deviceSerialNo, String productionStationCode, Boolean isBulkQty, List<Basketcodelst> basketLst, String appLang) {
        this.userID = userID;
        this.deviceSerialNo = deviceSerialNo;
        this.productionStationCode = productionStationCode;
        this.isBulkQty = isBulkQty;
        this.basketLst = basketLst;
        AppLang = appLang;
    }
}
