package com.example.gbsbadrsf.repository;

import com.example.gbsbadrsf.Stoppages.Model.AddStoppageData;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseAddStoppage;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetAvailableDatesForStoppage;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetDiesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetFactoriesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetJigsList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetMachineDetails;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetMachineFamilyList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetMachinesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetNameOfStoppagesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetStationDetails;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetStationsList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetStoppagesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetWorkCenterLinesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetWorkCenterSubLinesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetWorkCentersList;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StoppagesApiInterface {
    @GET("GetAvailableDatesForStoppage")
    Single<ApiResponseGetAvailableDatesForStoppage> GetAvailableDatesForStoppage(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo
    );
    @GET("GetNameOfStoppagesList")
    Single<ApiResponseGetNameOfStoppagesList> GetNameOfStoppagesList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo
    );
    @GET("GetFactoriesList")
    Single<ApiResponseGetFactoriesList> GetFactoriesList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo
    );
    @GET("GetWorkCentersList")
    Single<ApiResponseGetWorkCentersList> GetWorkCentersList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("FactoryID") int FactoryID
    );
    @GET("GetWorkCenterLinesList")
    Single<ApiResponseGetWorkCenterLinesList> GetWorkCenterLinesList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("WorkCenterID") int WorkCenterID
    );
    @GET("GetWorkCenterSubLinesList")
    Single<ApiResponseGetWorkCenterSubLinesList> GetWorkCenterSubLinesList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("WorkCenterLineID") int WorkCenterLineID
    );

    @GET("GetMachinesList")
    Single<ApiResponseGetMachinesList> GetMachinesList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("WorkCenterLineID") int MachineFamilyID
    );
    @GET("GetMachineFamilyList")
    Single<ApiResponseGetMachineFamilyList> GetMachineFamilyList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("ProductionSubLineId") int ProductionSubLineId
    );
    @GET("GetStationsList")
    Single<ApiResponseGetStationsList> GetStationsList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("ProductionSubLineId") int ProductionSubLineId
    );

    @GET("GetStoppagesList")
    Single<ApiResponseGetStoppagesList> GetStoppagesList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo
    );
    @GET("GetMachineDetails")
    Single<ApiResponseGetMachineDetails> GetMachineDetails(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("MachineCode") String MachineCode
    );
    @GET("GetStationDetails")
    Single<ApiResponseGetStationDetails> GetStationDetails(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("StationCode") String StationCode
    );
    @POST("AddStoppage")
    Single<ApiResponseAddStoppage> AddStoppage(
            @Body AddStoppageData addStoppageData
    );

    @GET("GetDiesList")
    Single<ApiResponseGetDiesList> GetDiesList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo
    );
    @GET("GetJigsList")
    Single<ApiResponseGetJigsList> GetJigsList(
            @Query("applang") String AppLang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo
    );
}
