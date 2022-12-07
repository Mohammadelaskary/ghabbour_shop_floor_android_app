package com.example.gbsbadrsf.repository;

import com.example.gbsbadrsf.AddStationWorkers.AddWorkers_StationData;
import com.example.gbsbadrsf.AddStationWorkers.ApiResponseAddWorkers_Station;
import com.example.gbsbadrsf.AddStationWorkers.ApiResponseGetStationWorkers;
import com.example.gbsbadrsf.AddWorkers.AddWorkers_MachineData;
import com.example.gbsbadrsf.AddWorkers.ApiResponseAddWorkers_Machine;
import com.example.gbsbadrsf.AddWorkers.ApiResponseGetMachineWorkers;
import com.example.gbsbadrsf.AddWorkers.ApiResponseGetWorkersList;
import com.example.gbsbadrsf.ApiResponseGetMobileVersion;
import com.example.gbsbadrsf.ApiResponseTestConnectivity;
import com.example.gbsbadrsf.BasketInfo.ApiResponseBasketsWIP;
import com.example.gbsbadrsf.Handling.ManufacturingCounting.ApiResponseGetBasketInfo_ManufacturingProductionCounting;
import com.example.gbsbadrsf.Handling.ManufacturingCounting.ApiResponseSaveManufacturingProductionCounting;
import com.example.gbsbadrsf.Handling.WeldingCounting.ApiResponseGetBasketInfo_WeldingProductionCounting;
import com.example.gbsbadrsf.Handling.WeldingCounting.ApiResponseSaveWeldingProductionCounting;
import com.example.gbsbadrsf.Manfacturing.machineloading.ApiResponseMachineReload;
import com.example.gbsbadrsf.Manfacturing.machineloading.ContinueLoadingData;
import com.example.gbsbadrsf.Manfacturing.machineloading.MachineReloadData;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.ApiResponseGetMachineData;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.ApiResponseGetStopageReasonsList;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.ApiResponseMachineHold;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.ApiResponseTransferMachineLoading;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineHoldData;
import com.example.gbsbadrsf.Model.ApiResponseBasketTransfer;
import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import com.example.gbsbadrsf.Model.ApiResponseGetBasketInfoLocateInQuality;
import com.example.gbsbadrsf.Model.ApiResponseGetJobOrderIssuedChilds;
import com.example.gbsbadrsf.Model.ApiResponseGetJobOrdersForIssue;
import com.example.gbsbadrsf.Model.ApiResponseLastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ApiResponseQualityOk;
import com.example.gbsbadrsf.Model.ApiResponseQualityPass;
import com.example.gbsbadrsf.Model.ApiResponseTransferIssuedChildToBasket;
import com.example.gbsbadrsf.Paint.PaintSignInData;
import com.example.gbsbadrsf.ProductionRepair.Data.ApiResponseSaveRejectionRequest;
import com.example.gbsbadrsf.ProductionRepair.PaintProductionRepair.ApiReponse.ApiResponsePaintingRepair_Production;
import com.example.gbsbadrsf.ProductionRepair.WeldingQuality.Data.ApiReponse.ApiResponseWeldingRepair_Production;
import com.example.gbsbadrsf.Quality.Data.AddManufacturingDefectData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddManufacturingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect_Online;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectedQualityOk_Manufacturing;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDeleteManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDeleteManufacturingDefect_Online;
import com.example.gbsbadrsf.Quality.Data.ApiResponseFullInspection;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRejectionReasonsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetSavedCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGettingFinalQualityDecision;
import com.example.gbsbadrsf.Quality.Data.ApiResponseLastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetDeclinedRejectionList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetItemByCode;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSavingOperationSignOffDecision;
import com.example.gbsbadrsf.Quality.Data.ApiResponseUpdateManufacturingDefects;
import com.example.gbsbadrsf.Quality.Data.ApiResponseUpdateManufacturingDefects_Online;
import com.example.gbsbadrsf.Quality.Data.FullInspectionData;
import com.example.gbsbadrsf.Quality.Data.FullInspectionQuality_OnlineData;
import com.example.gbsbadrsf.Quality.Data.UpdateManufacturingDefectsData;
import com.example.gbsbadrsf.Quality.Data.UpdateManufacturingDefectsData_Online;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingRejectionRequest.ApprovalRejectionRequest_Manufacturing.ApiResponseManufacturingRejectionRequestCloseRequest;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingRejectionRequest.ApprovalRejectionRequest_Manufacturing.ApiResponseManufacturingRejectionRequestGetApprovedRejectionList;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingRejectionRequest.DeclineRejectionRequest_Manufacturing.ApiResponseManufacturingRejectionRequestCloseDeclinedRequest;
import com.example.gbsbadrsf.Quality.manfacturing.Model.ApiResponseRejectionRequestTakeAction;
import com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception.AddManufacturingDefectData_Online;
import com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception.UpdateWeldingDefectsData_Online;
import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.SaveRejectionRequestBody;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponsePaintingRepair_QC;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseQualityOperationSignOff_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseRejectionRequest_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseSaveQualityRandomInpection_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseAddingPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseDefectedQualityOk_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseDeletePaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseFullInspection_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityOk_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityPass_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseUpdatePaintingDefects;
import com.example.gbsbadrsf.Quality.paint.Model.FullInspectionData_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.SaveRejectionRequestBody_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.UpdatePaintingDefectsData;
import com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.ApprovalRejectionRequest_Paint.ApiResponsePaintingRejectionRequestCloseRequest;
import com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.ApprovalRejectionRequest_Paint.ApiResponsePaintingRejectionRequestGetRejectionRequestList;
import com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.DeclineRejectionRequest_Paint.ApiResponsePaintingRejectionRequestCloseDeclinedRequest;
import com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.DeclineRejectionRequest_Paint.ApiResponsePaintingRejectionRequestGetDeclinedRejectionList;
import com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.DeclineRejectionRequest_Paint.ApiResponsePaintingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.Quality.paint.PprListQualityOperation.ApiResponseGetPaintQualityPprList;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.AddPaintingDefectData_Online;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.ApiResponseAddingPaintingDefect_Online;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.ApiResponseDeletePaintingDefect_Online;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.ApiResponseGetStationPPRInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.ApiResponseUpdatePaintingDefects_Online;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.FullInspectionQuality_Online_Painting_Data;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment.ApiResponseGetStationInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.UpdatePaintingDefectsData_Online;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseDefectedQualityOk_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseDeleteWeldingDefect_Online;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetInfoForQualityRandomInpection_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetRejectionRequestList;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseQualityOperationSignOff_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseRejectionRequest_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseAddingWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseDeleteWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseFullInspection_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseQualityOk_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseQualityPass_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseUpdateWeldingDefects;
import com.example.gbsbadrsf.Quality.welding.Model.FullInspectionData_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.UpdateWeldingDefectsData;
import com.example.gbsbadrsf.Quality.welding.RandomQualityInception.AddWeldingDefectData_Online;
import com.example.gbsbadrsf.Quality.welding.RandomQualityInception.ApiResponseAddingWeldingDefect_Online;
import com.example.gbsbadrsf.Quality.welding.RandomQualityInception.ApiResponseUpdatingWeldingDefect_Online;
import com.example.gbsbadrsf.Quality.welding.RandomQualityInception.FullInspectionQuality_Online_Welding_Data;
import com.example.gbsbadrsf.Quality.welding.RejectionRequest.SaveRejectionRequestBody_Welding;
import com.example.gbsbadrsf.Quality.welding.WeldingRejectionRequest.ApprovalRejectionRequest_Welding.ApiResponseWeldingRejectionRequestCloseRequest;
import com.example.gbsbadrsf.Quality.welding.WeldingRejectionRequest.ApprovalRejectionRequest_Welding.ApiResponseWeldingRejectionRequestGetRejectionRequestList;
import com.example.gbsbadrsf.Quality.welding.WeldingRejectionRequest.DeclineRejectionRequest_Welding.ApiResponseWeldingRejectionRequestCloseDeclinedRequest;
import com.example.gbsbadrsf.Quality.welding.WeldingRejectionRequest.DeclineRejectionRequest_Welding.ApiResponseWeldingRejectionRequestGetDeclinedRejectionList;
import com.example.gbsbadrsf.Quality.welding.WeldingRejectionRequest.DeclineRejectionRequest_Welding.ApiResponseWeldingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.data.ApiResponseGetWIP_Painting;
import com.example.gbsbadrsf.data.response.APIResponse;
import com.example.gbsbadrsf.data.response.APIResponseLoadingsequenceinfo;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.ApiContinueloading;
import com.example.gbsbadrsf.data.response.ApiGetCountingData;
import com.example.gbsbadrsf.data.response.ApiGetPaintingLoadingSequenceStartLoading;
import com.example.gbsbadrsf.data.response.ApiGetRecivingData;
import com.example.gbsbadrsf.data.response.ApiGetweldingloadingstartloading;
import com.example.gbsbadrsf.data.response.ApiMachinesignoff;
import com.example.gbsbadrsf.data.response.ApiPaintstation;
import com.example.gbsbadrsf.data.response.ApiResponseMachinewip;
import com.example.gbsbadrsf.data.response.ApiResponseProductionSignOff_Painting;
import com.example.gbsbadrsf.data.response.ApiResponseStationwip;
import com.example.gbsbadrsf.data.response.ApiResponseweldingbyjoborder;
import com.example.gbsbadrsf.data.response.ApiSavePaintloading;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.ApiWeldingsignoff;
import com.example.gbsbadrsf.data.response.Apigetbasketcode;
import com.example.gbsbadrsf.data.response.Apigetmachinecode;
import com.example.gbsbadrsf.data.response.Apiinfoforstationcode;
import com.example.gbsbadrsf.data.response.CountingData;
import com.example.gbsbadrsf.data.response.CountingDataRecivingdata;
import com.example.gbsbadrsf.data.response.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.data.response.Pprpaintcontainbaskets;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody_Partial;
import com.example.gbsbadrsf.signin.ApiResponseChangePassword;
import com.example.gbsbadrsf.welding.ItemsReceiving.PutChildsToBasketData;
import com.example.gbsbadrsf.weldingsequence.StationSignIn;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("GetManufacturingLoadingSequenceByJobOrder")
    Single<APIResponse<List<Ppr>>> getproductionsequence(
            @Query("applang") String applang,
            @Query("JobOrderName") String jobordername
    );
    @GET("GetManufacturingLoadingSequenceByJobOrder")
    Single<APIResponse<ResponseStatus>> getproductionSequenceResponseStatus(
            @Query("applang") String applang,
            @Query("JobOrderName") String jobordername
    );


  @GET("GetWeldingLoadingSequence")
    Single<ApiResponseweldingbyjoborder<List<PprWelding>>> getweldingsequence(
          @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber
  );

    //get paint station by job order
    @GET("GetPaintingLoadingSequence")
    Single<ApiPaintstation<List<Pprpaint>>> getpaintsequence(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber
    );

    //getinfo for selected station
//@GET("GetInfoForSelectedStation")
//Single<Apigetinfoforselectedstation<StationLoading>> getinfoforselectedstation(@Query("UserID") String userid,@Query("DeviceSerialNo") String deviceserialnumber,@Query("ProductionStationEnName")String ProductionStationEnName);//old
    @GET("GetWeldingLoadingSequenceStartLoading")
//the new one of get selection info
    Single<ApiGetweldingloadingstartloading<PprWelding>> getweldingloadingsequence(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber,
            @Query("LoadingSequenceID") String loadingsequenceid);

    //get info for selected paint
    @GET("GetPaintingLoadingSequenceStartLoading")
    Single<ApiGetPaintingLoadingSequenceStartLoading<Pprpaintcontainbaskets>> getpaintloadingsequence(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber,
            @Query("LoadingSequenceID") String loadingsequenceid);

    //saveweldingloadingsequence
    @POST("SaveWeldingLoadingSequence")
    Single<ApiSavefirstloading<ResponseStatus>> saveweldingloadingsequence(@Body StationSignIn stationSignIn);

    //savepaintloadingsequence
    @POST("SavePaintingLoadingSequence")
    Single<ApiSavePaintloading<ResponseStatus>> savepaintloadingsequence(@Body PaintSignInData data
    );

    //GetCountingdata
    @GET("GetCountingData")
    Single<ApiGetCountingData<CountingData>> getcountingdata(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("Barcode") String barcode);

    //SetCountingData
    @GET("SetCountingData")
    Single<ApiGetCountingData<ResponseStatus>> seetcountingdata(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("Barcode") String barcode,
            @Query("CountingQty") String contingqty
    );

    //Get Recivingdata
    @GET("GetReceivingData")
    Single<ApiGetRecivingData<CountingDataRecivingdata>> getrecivingcountingdata(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("Barcode") String barcode);

    //setRecivingData
    @GET("SetReceivingData")
    Single<ApiGetRecivingData<ResponseStatus>> setRecivinggdata(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("Barcode") String barcode,
            @Query("ReceivingQty") String recivingqty);


    //Getmachinewip
    @GET("GetMachinesWIP")
    Single<ApiResponseMachinewip<List<MachinesWIP>>> getmachinewip(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber);

    //Getweldingwip
    @GET("GetStationsWIP")
    Single<ApiResponseStationwip<List<StationsWIP>>> getstationwip(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber);

    //Getpaintwip
    @GET("GetStationsWIP_Painting")
    Single<ApiResponseStationwip<List<StationsWIP>>> getpaintwip(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber);


    //Get info for stationcode
    @GET("GetInfoForSelectedStation")
    Single<Apiinfoforstationcode<Stationcodeloading>> getinfoforstationcode(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("ProductionStationCode") String ProductionStationCode);

  @GET("GetManufacturingBasketWIP")
  Single<ApiResponseBasketsWIP> GetManufacturingBasketWIP(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode);


    @GET("SignIn")
    Single<APIResponseSignin> login(
            @Query("applang") String applang,
            @Query("Username") String username,
            @Query("Pass") String pass);

    @GET("GetInfoForSelectedLoadingSequence")
    Single<APIResponseLoadingsequenceinfo<LoadingSequenceInfo>> loadingswquenceinfo(
            @Query("applang") String applang,
            @Query("UserID") int username,
            @Query("DeviceSerialNo") String deviceserialnumber,
            @Query("LoadingSequenceID") int loadingsequenceid);

    @GET("SaveFistLoadingSequence")
    Single<ApiSavefirstloading<ResponseStatus>> savefirstloading(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String deviceserialnumber,
            @Query("LoadingSequenceID") int loadingsequenceid,
            @Query("MachineCode") String machinecode,
            @Query("DieCode") String DieCode,
            @Query("LoadingQtyMobile") String loadinyqtymobile
    );

    @POST("MachineSignOff")
    Single<ApiMachinesignoff<ResponseStatus>> machinesignoff(@Body MachineSignoffBody jsonObject);

    //welding signoff
    @POST("StationSignOff")
    Single<ApiWeldingsignoff<ResponseStatus>> weldingsignoff(@Body WeldingSignoffBody jsonObject);

    //get machine code in signoff
    @GET("GetInfoForSelectedMachine")
    Single<Apigetmachinecode<MachineLoading>> getmachinecodedata(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String devicenumber,
            @Query("MachineCode") String machinecode);

    @GET("GetSecondLoading")
    Single<Apigetbasketcode<LastMoveManufacturingBasketInfo>> getbasketcodedata(
            @Query("applang") String applang,
            @Query("UserID") int userid,
            @Query("DeviceSerialNo") String devicenumber,
            @Query("BasketCode") String basketcode);

    @POST("ContinueLoading")
    Single<ApiContinueloading<ResponseStatus>> savecontinueloading(@Body ContinueLoadingData data);


    @GET("GetBasketInfoForQuality")
    Single<ApiResponseLastMoveManufacturingBasket> getBasketData(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String basketCode);
  @GET("GetBasketInfoForQuality_Welding")
  Single<ApiResponseLastMoveWeldingBasket> getBasketData_welding(
          @Query("applang") String applang,
          @Query("UserID") int userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String basketCode);


    @GET("GetManufacturingDefectedQtyByBasketCode")
    Single<ApiResponseDefectsManufacturing> getManufacturingDefectedQtyByBasketCode(
            @Query("applang") String applang,
            @Query("BasketCode") String BasketCode
    );
  @GET("GetBasketInfoLocateInQuality")
  Single<ApiResponseGetBasketInfoLocateInQuality> GetBasketInfoLocateInQuality(
          @Query("applang") String applang,
          @Query("BasketCode") String BasketCode
  );


    @GET("GetManufacturingDefectsList")
    Single<ApiResponseDefectsManufacturing> getManufacturingDefectsList(
            @Query("applang") String applang,
            @Query("JobOrderId") int JobOrderId,
            @Query("ChildId") int ChildId
    );

    @GET("GetQualityOperationByBasketCode")
    Single<ApiResponseDefectsManufacturing> getQualityOperationByBasketCode(
            @Query("applang") String applang,
            @Query("UserID") long userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @POST("AddManufacturingDefect")
    Single<ApiResponseAddingManufacturingDefect> AddManufacturingDefect(
            @Body AddManufacturingDefectData data
    );
  @POST("AddWeldingDefect")
  Single<ApiResponseAddingWeldingDefect> AddWeldingDefect(
          @Body AddWeldingDefectData data
  );
  @POST("AddPaintingDefect")
  Single<ApiResponseAddingPaintingDefect> AddPaintingDefect(
          @Body AddPaintingDefectData data
  );
  @POST("AddManufacturingDefect_Online")
  Single<ApiResponseAddingManufacturingDefect_Online> AddManufacturingDefect_Online(
          @Body AddManufacturingDefectData_Online data
  );
  @POST("AddWeldingDefect_Online")
  Single<ApiResponseAddingWeldingDefect_Online> AddWeldingDefect_Online(
          @Body AddWeldingDefectData_Online data
  );
  @POST("AddPaintingDefect_Online")
  Single<ApiResponseAddingPaintingDefect_Online> AddPaintingDefect_Online(
          @Body AddPaintingDefectData_Online data
  );
  @POST("UpdateManufacturingDefect")
  Single<ApiResponseUpdateManufacturingDefects> UpdateManufacturingDefect(
          @Body UpdateManufacturingDefectsData data
  );
  @POST("UpdateWeldingDefect")
  Single<ApiResponseUpdateWeldingDefects> UpdateWeldingDefect(
          @Body UpdateWeldingDefectsData data
  );
  @POST("UpdatePaintingDefect")
  Single<ApiResponseUpdatePaintingDefects> UpdatePaintingDefect(
          @Body UpdatePaintingDefectsData data
  );
  @POST("UpdateManufacturingDefect_Online")
  Single<ApiResponseUpdateManufacturingDefects_Online> UpdateManufacturingDefect_Online(
          @Body UpdateManufacturingDefectsData_Online data
  );
  @POST("UpdateWeldingDefect_Online")
  Single<ApiResponseUpdatingWeldingDefect_Online> UpdateWeldingDefect_Online(
          @Body UpdateWeldingDefectsData_Online data
  );
  @POST("UpdatePaintingDefect_Online")
  Single<ApiResponseUpdatePaintingDefects_Online> UpdatePaintingDefect_Online(
          @Body UpdatePaintingDefectsData_Online data
  );

    @GET("QualityOperationStatus")
    Single<ResponseStatus> setQualityOperationStatus(
            @Query("applang") String applang,
            @Query("QualityOperationStatus") int QualityOperationStatus,
            @Query("ChildId") int ChildId,
            @Query("SignOffQty") int SignOffQty,
            @Query("IsDefected") boolean IsDefected
    );

    @GET("GetDefectsList")
    Single<ApiResponseDefectsList> getDefectsList(
            @Query("applang") String applang
    );

    @GET("GetDefectsListPerOperation")
    Single<ApiResponseDefectsList> getDefectsListPerOperation(
            @Query("applang") String applang,
            @Query("OperationID") int OperationID
    );

    @GET("AddManufacturingDefectedChildToBasket")
    Single<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectedChildToBasket(
            @Query("applang") String applang,
            @Query("JobOrderId") int JobOrderId,
            @Query("ParentID") int ParentID,
            @Query("ChildId") int ChildId,
            @Query("BasketCode") String BasketCode,
            @Query("NewBasketCode") String NewBasketCode
    );

    @GET("ManufacturingRepair_Production")
    Single<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepair_Production(
            @Query("applang") String applang,
            @Query("UserID") long userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("DefectsManufacturingDetailsId") int DefectsManufacturingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyRepaired") int QtyRepaired
    );

    @GET("ManufacturingRepair_QC")
    Single<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepair_QC(
            @Query("applang") String applang,
            @Query("UserID") long userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("DefectsManufacturingDetailsId") int DefectsManufacturingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyApproved") int QtyApproved
    );

    @GET("GetMachineInfoForQuality")
    Single<ApiResponseGetRandomQualityInception> GetInfoForQualityRandomInspection(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("MachineCode") String MachineCode
    );

    @GET("GetStationInfoForQuality_Welding")
    Single<ApiResponseGetInfoForQualityRandomInpection_Welding> GetInfoForQualityRandomInpection_Welding(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("StationCode") String Code
    );

    @GET("GetStationPPRInfoForQuality_Painting")
    Single<ApiResponseGetStationPPRInfoForQuality_Painting> GetStationPPRInfoForQuality_Painting(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("PprLoadingId") int PprLoadingId,
            @Query("StationCode") String StationCode
    );

    @GET("ManufacturingRejectionRequestGetItemByCode")
    Single<ApiResponseManufacturingRejectionRequestGetItemByCode> ManufacturingRejectionRequestGetItemByCode(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("ItemCode") String ItemCode
    );

    @POST("FullInspectionQuality_Online")
    Single<ApiResponseSaveRandomQualityInception> FullInspectionQuality_Online(
            @Body FullInspectionQuality_OnlineData data
    );

    @POST("FullInspectionQuality_Welding_Online")
    Single<ApiResponseSaveRandomQualityInception> FullInspectionQuality_Welding_Online(
            @Body FullInspectionQuality_Online_Welding_Data data
    );
  @POST("FullInspectionQuality_Painting_Online")
  Single<ApiResponseSaveRandomQualityInception> FullInspectionQuality_Painting_Online(
          @Body FullInspectionQuality_Online_Painting_Data data
  );

    @GET("SaveQualityRandomInpection_Painting")
    Single<ApiResponseSaveQualityRandomInpection_Painting> SaveQualityRandomInpection_Painting(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("LastMoveId") int LastMoveId,
            @Query("QtyDefected") int QtyDefected,
            @Query("SampleQty") int SampleQty,
            @Query("Notes") String Notes
    );

    @POST("ManufacturingCreateRejectionRequest")
    Single<ApiResponseSaveRejectionRequest> SaveRejectionRequest(
            @Body SaveRejectionRequestBody body
    );

    @GET("GetDepartmentsList")
    Single<ApiResponseDepartmentsList> getDepartmentsList(
            @Query("applang") String applang,
            @Query("UserID") int UserID
    );

    @GET("GetFinalQualityDecision")
    Single<ApiResponseGettingFinalQualityDecision> getFinalQualityDecision(
            @Query("applang") String applang,
            @Query("UserID") int UserID
    );

    @POST("QualityOperationSignOff")
    Single<ApiResponseSavingOperationSignOffDecision> saveQualityOperationSignOff(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode,
            @Query("DT") String date,
            @Query("FinalQualityDecisionId") int FinalQualityDecisionId
    );

    @POST("QualityOperationSignOff_Welding")
    Single<ApiResponseQualityOperationSignOff_Welding> QualityOperationSignOff_Welding(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode,
            @Query("DT") String date,
            @Query("FinalQualityDecisionId") int FinalQualityDecisionId
    );

    @POST("QualityOperationSignOff_Painting")
    Single<ApiResponseQualityOperationSignOff_Painting> QualityOperationSignOff_Painting(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode,
            @Query("DT") String date,
            @Query("FinalQualityDecisionId") int FinalQualityDecisionId
    );

  @GET("ManufacturingRejectionRequestGetRejectionRequestList")
  Single<ApiResponseManufacturingRejectionRequestGetRejectionRequestList> ManufacturingRejectionRequestGetRejectionRequestList(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber
  );

    @GET("ManufacturingRejectionRequestGetRejectionListForClosing")
    Single<ApiResponseManufacturingRejectionRequestGetApprovedRejectionList> getApprovalRejectionRequestsList_Manufacturing(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber
    );
  @GET("WeldingRejectionRequestGetRejectionListForClosing")
  Single<ApiResponseWeldingRejectionRequestGetRejectionRequestList> getApprovalRejectionRequestsList_Welding(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber
  );
  @GET("PaintingRejectionRequestGetRejectionListForClosing")
  Single<ApiResponsePaintingRejectionRequestGetRejectionRequestList> getApprovalRejectionRequestsList_Painting(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber
  );
  @GET("ManufacturingRejectionRequestGetDeclinedRejectionList")
  Single<ApiResponseManufacturingRejectionRequestGetDeclinedRejectionList> getRejectionRequestsListCommittee(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber
  );
  @GET("WeldingRejectionRequestGetDeclinedRejectionList")
  Single<ApiResponseWeldingRejectionRequestGetDeclinedRejectionList> getRejectionRequestsListCommittee_Welding(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber
  );
  @GET("PaintingRejectionRequestGetDeclinedRejectionList")
  Single<ApiResponsePaintingRejectionRequestGetDeclinedRejectionList> getRejectionRequestsListCommittee_Paint(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber
  );
  @GET("ManufacturingRejectionRequestCommitteeDecision")
  Single<ApiResponseManufacturingRejectionRequestCloseDeclinedRequest> saveCommitteeDecision_Manufacturing(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("OkQty") String OkQty,
          @Query("OkBasketCode") String OkBasketCode,
          @Query("RejectedQty") String RejectedQty,
          @Query("DepartmentId") int DepartmentId,
          @Query("RejectionReasonID") int RejectionReasonID,
          @Query("Notes") String Notes
  );
  @GET("WeldingRejectionRequestCommitteeDecision")
  Single<ApiResponseWeldingRejectionRequestCloseDeclinedRequest> saveCommitteeDecision_Welding(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("OkQty") String OkQty,
          @Query("OkBasketCode") String OkBasketCode,
          @Query("RejectedQty") String RejectedQty,
          @Query("DepartmentId") int DepartmentId,
          @Query("RejectionReasonID") int RejectionReasonID,
          @Query("Notes") String Notes
  );
  @GET("PaintingRejectionRequestCommitteeDecision")
  Single<ApiResponsePaintingRejectionRequestCloseDeclinedRequest> saveCommitteeDecision_Paint(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("OkQty") String OkQty,
          @Query("OkBasketCode") String OkBasketCode,
          @Query("RejectedQty") String RejectedQty,
          @Query("DepartmentId") int DepartmentId,
          @Query("RejectionReasonID") int RejectionReasonID,
          @Query("Notes") String Notes
  );
//  @GET("ManufacturingRejectionRequestGetRejectionRequestByID")
//  Single<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> ManufacturingRejectionRequestGetRejectionRequestByID(
//          @Query("UserID") int UserID,
//          @Query("DeviceSerialNo") String deviceSerialNumber,
//          @Query("RejectionRequestId") int RejectionRequestId,
//          @Query("SubInventoryCode") String SubInventoryCode,
//          @Query("LocatorId") String LocatorId,
//          @Query("OkQty") String OkQty,
//          @Query("OkBasketCode") int OkBasketCode,
//          @Query("RejectedQty") String RejectedQty,
//          @Query("DepartmentId") int DepartmentId,
//          @Query("RejectionReasonID") int RejectionReasonID,
//          @Query("Notes") String Notes
//
//  );

    @GET("WeldingRejectionRequestGetRejectionRequestList")
    Single<ApiResponseGetRejectionRequestList> getRejectionRequestsList_Welding(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber
    );

    @GET("PaintingRejectionRequestGetRejectionRequestList")
    Single<com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetRejectionRequestList> getRejectionRequestsList_Painting(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber
    );

    @GET("ManufacturingRejectionRequestProductionTeamLeaderDecision")
    Single<ApiResponseRejectionRequestTakeAction> RejectionRequestTakeAction(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("RejectionRequestId") int RejectionRequestId,
            @Query("IsAccept") boolean IsAccept,
            @Query("Notes") String notes

    );
  @GET("ManufacturingRejectionRequestGetRejectionRequestByID")
  Single<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> ManufacturingRejectionRequestGetRejectionRequestByID(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId
  );
  @GET("WeldingRejectionRequestGetRejectionRequestByID")
  Single<ApiResponseWeldingRejectionRequestGetRejectionRequestByID> WeldingRejectionRequestGetRejectionRequestByID(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId
  );
  @GET("PaintingRejectionRequestGetRejectionRequestByID")
  Single<ApiResponsePaintingRejectionRequestGetRejectionRequestByID> PaintingRejectionRequestGetRejectionRequestByID(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId
  );

    @GET("WeldingRejectionRequestProductionTeamLeaderDecision")
    Single<ApiResponseRejectionRequestTakeAction> RejectionRequestTakeAction_Welding(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("RejectionRequestId") int RejectionRequestId,
            @Query("IsAccept") boolean IsApproved

    );

    @GET("PaintingRejectionRequestProductionTeamLeaderDecision")
    Single<ApiResponseRejectionRequestTakeAction> RejectionRequestTakeAction_Painting(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("RejectionRequestId") int RejectionRequestId,
            @Query("IsAccept") boolean IsApproved

    );

    @GET("GetCheckList")
    Single<ApiResponseGetCheckList> getCheckList(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("OperationID") int OperationID
    );

    @GET("GetCheckList")
    Single<ApiResponseGetCheckList> getCheckList_Welding(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("OperationID") int OperationID
    );

    @GET("SaveCheckList")
    Single<ApiResponseSaveCheckList> saveCheckList(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("LastMoveId") int LastMoveId,
            @Query("ChildId") int ChildId,
            @Query("ChildCode") String ChildCode,
            @Query("JobOrderId") int JobOrderId,
            @Query("JobOrderName") String JobOrderName,
            @Query("PprLoadingId") int PprLoadingId,
            @Query("OperationId") int OperationId,
            @Query("CheckListElementId") int CheckListElementId
    );

    @GET("SaveCheckList_Welding")
    Single<ApiResponseSaveCheckList> saveCheckList_Welding(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("LastMoveId") int LastMoveId,
            @Query("ChildId") int ChildId,
            @Query("ChildCode") String ChildCode,
            @Query("JobOrderId") int JobOrderId,
            @Query("JobOrderName") String JobOrderName,
            @Query("PprLoadingId") int PprLoadingId,
            @Query("OperationId") int OperationId,
            @Query("CheckListElementId") int CheckListElementId
    );

    @GET("GetSavedCheckList")
    Single<ApiResponseGetSavedCheckList> getSavedCheckList(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("ChildId") int ChildId,
            @Query("JobOrderId") int JobOrderId,
            @Query("OperationID") int OperationID
    );

    @GET("GetSavedCheckList")
    Single<ApiResponseGetSavedCheckList> getSavedCheckList_Welding(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("ChildId") int ChildId,
            @Query("JobOrderId") int JobOrderId,
            @Query("OperationID") int OperationID
    );
  @GET("GetSavedCheckList")
  Single<ApiResponseGetSavedCheckList> getSavedCheckList_Painting(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("ChildId") int ChildId,
          @Query("JobOrderId") int JobOrderId,
          @Query("OperationID") int OperationID
  );

    @GET("GetBasketInfoForQuality_Welding")
    Single<ApiResponseGetBasketInfoForQuality_Welding> getBasketInfoForQuality_Welding(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @GET("GetPPRInfoForQuality_Painting")
    Single<ApiResponseGetBasketInfoForQuality_Painting> getPprInfoForQuality_Painting(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("loadingPaintingSignOutTransactionId") int loadingPaintingSignOutTransactionId
    );
  @GET("GetBasketInfoForQuality_Painting")
  Single<ApiResponseGetBasketInfoForQuality_Painting> getBasketInfoForQuality_Painting(
          @Query("applang") String applang,
          @Query("UserID") int userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String BasketCode
  );

    @GET("GetQualityOperationByBasketCode_Painting")
    Single<ApiResponseGetPaintingDefectedQtyByBasketCode> getPaintingDefectedQtyByBasketCode(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @GET("GetQualityOperationByBasketCode_Welding")
    Single<ApiResponseGetWeldingDefectedQtyByBasketCode> getWeldingDefectedQtyByBasketCode(
            @Query("applang") String applang,
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @GET("AddManufacturingDefectedParentToBasket")
    Single<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectedParentToBasket(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("JobOrderId") int JobOrderId,
            @Query("ParentID") int ParentID,
            @Query("BasketCode") String BasketCode,
            @Query("NewBasketCode") String NewBasketCode
    );

    @GET("AddWeldingDefectedParentToBasket")
    Single<ApiResponseAddWeldingDefectedChildToBasket> addWeldingDefectedParentToBasket(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("JobOrderId") int JobOrderId,
            @Query("ParentID") int ParentID,
            @Query("BasketCode") String BasketCode,
            @Query("NewBasketCode") String NewBasketCode
    );

    @GET("AddPaintingDefectedParentToBasket")
    Single<ApiResponseAddPaintingDefectedChildToBasket> addPaintingDefectedParentToBasket(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("JobOrderId") int JobOrderId,
            @Query("ParentID") int ParentID,
            @Query("BasketCode") String BasketCode,
            @Query("NewBasketCode") String NewBasketCode
    );

    @POST("AddWeldingDefect")
    Single<ApiResponseAddWeldingDefect> addWeldingDefect(
            @Body AddWeldingDefectData data
    );

    @POST("AddPaintingDefect")
    Single<ApiResponseAddPaintingDefect> addPaintingDefect(
            @Body AddPaintingDefectData data
    );

    @GET("WeldingRepair_QC")
    Single<ApiResponseWeldingRepair_QC> WeldingRepair_QC(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsWeldingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyApproved") int QtyApproved
    );

    @GET("PaintingRepair_QC")
    Single<ApiResponsePaintingRepair_QC> PaintingRepair_QC(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsPaintingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyApproved") int QtyApproved
    );

    @GET("WeldingRepair_Production")
    Single<ApiResponseWeldingRepair_Production> WeldingRepair_Production(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsWeldingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyRepaired") int QtyRepaired
    );

    @GET("PaintingRepair_Production")
    Single<ApiResponsePaintingRepair_Production> PaintingRepair_Production(
            @Query("applang") String applang,
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsPaintingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyRepaired") int QtyRepaired
    );



  @POST("SaveRejectionRequest_Welding")
  Single<ApiResponseRejectionRequest_Welding> RejectionRequest_Welding(
          @Body SaveRejectionRequestBody_Welding body
  );
  @POST("SaveRejectionRequest_Painting")
  Single<ApiResponseRejectionRequest_Painting> RejectionRequest_Painting(
          @Body SaveRejectionRequestBody_Painting body
  );
    @GET("test")
    Single<ApiResponseTestConnectivity> Test_Connectivity(
            @Query("text") String text
    );
  @GET("ChangePassword")
  Single<ApiResponseChangePassword> changePassword(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("OldPass") String OldPass,
          @Query("NewPass") String NewPass
  );

  @GET("QualityOk")
  Single<ApiResponseQualityOk> QualityOk(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT,
          @Query("SampleQty") int SampleQty
  );
  @GET("QualityOk_Welding")
  Single<ApiResponseQualityOk_Welding> QualityOk_Welding(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT,
          @Query("SampleQty") int SampleQty
  );
  @GET("QualityOk_Painting")
  Single<ApiResponseQualityOk_Painting> QualityOk_Painting(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("loadingPaintingSignOutTransactionId") int loadingPaintingSignOutTransactionId,
          @Query("DT") String DT,
          @Query("SampleQty") int SampleQty
  );
  @GET("QualityPass")
  Single<ApiResponseQualityPass> QualityPass(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT
//          ,@Query("SampleQty") int SampleQty
  );
  @GET("QualityPass_Welding")
  Single<ApiResponseQualityPass_Welding> QualityPass_Welding(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT
//          ,@Query("SampleQty") int SampleQty
  );
  @GET("QualityPass_Painting")
  Single<ApiResponseQualityPass_Painting> QualityPass_Painting(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("loadingPaintingSignOutTransactionId") int loadingPaintingSignOutTransactionId,
          @Query("DT") String DT
//          ,@Query("SampleQty") int SampleQty
  );
  @GET("DeleteManufacturingDefect")
  Single<ApiResponseDeleteManufacturingDefect> DeleteManufacturingDefect(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("MainDefectsId") int MainDefectsId,
          @Query("DefectGroupId") int DefectGroupId
  );
  @GET("DeleteManufacturingDefect_Online")
  Single<ApiResponseDeleteManufacturingDefect_Online> DeleteManufacturingDefect_Online(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectGroupId") int DefectGroupId,
          @Query("MainDefectsId") int MainDefectsId
  );
  @GET("DeleteWeldingDefect_Online")
  Single<ApiResponseDeleteWeldingDefect_Online> DeleteWeldingDefect_Online(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectGroupId") int DefectGroupId,
          @Query("MainDefectsId") int MainDefectsId
  );
  @GET("DeletePaintingDefect_Online")
  Single<ApiResponseDeletePaintingDefect_Online> DeletePaintingDefect_Online(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectGroupId") int DefectGroupId,
          @Query("MainDefectsId") int MainDefectsId
  );
  @GET("DeleteWeldingDefect")
  Single<ApiResponseDeleteWeldingDefect> DeleteWeldingDefect(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("MainDefectsId") int MainDefectsId,
          @Query("DefectGroupId") int DefectGroupId
  );
  @GET("DeletePaintingDefect")
  Single<ApiResponseDeletePaintingDefect> DeletePaintingDefect(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("MainDefectsId") int MainDefectsId,
          @Query("DefectGroupId") int DefectGroupId
  );
  @GET("GetJobOrdersForIssue")
  Single<ApiResponseGetJobOrdersForIssue> GetJobOrdersForIssue(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo
  );
  @POST("FullInspectionQuality")
  Single<ApiResponseFullInspection> SaveFullInspectionData(
          @Body FullInspectionData data
          );
  @POST("FullInspectionQuality_Welding")
  Single<ApiResponseFullInspection_Welding> SaveFullInspectionData(
          @Body FullInspectionData_Welding data
  );
  @POST("FullInspectionQuality_Painting")
  Single<ApiResponseFullInspection_Painting> SaveFullInspectionData(
          @Body FullInspectionData_Painting data
  );
  @GET("CheckBasketEmpty")
  Single<ApiResponseChangePassword> checkBasketStatus(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("ParentId") String ParentId,
          @Query("ChildId") String ChildId,
          @Query("JobOrderId") String JobOrderId,
          @Query("OperationID") String OperationID,
          @Query("ProductionSequenceNo") int ProductionSequenceNo
  );
  @GET("BasketTransfer")
  Single<ApiResponseBasketTransfer> BasketTransfer(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("OldBasketCode") String OldBasketCode,
          @Query("NewBasketCode") String NewBasketCode,
          @Query("QtyToTranfer") int QtyToTransfer
  );
  @GET("GetJobOrderIssuedChilds")
  Single<ApiResponseGetJobOrderIssuedChilds> GetJobOrderIssuedChilds(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("EntitiyId") int EntityId
  );
  @POST("TransferIssuedChildToBasket")
  Single<ApiResponseTransferIssuedChildToBasket> TransferIssuedChildToBasket(
          @Body PutChildsToBasketData data
  );
  @GET("DefectedQualityOk_Manufacturing")
  Single<ApiResponseDefectedQualityOk_Manufacturing> DefectedQualityOk_Manufacturing(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectedBasketCode") String DefectedBasketCode,
          @Query("NewBasketCode") String NewBasketCode,
          @Query("DefectGroupId") int DefectGroupId,
          @Query("DT") String DT
  );
  @GET("DefectedQualityOk_Welding")
  Single<ApiResponseDefectedQualityOk_Welding> DefectedQualityOk_Welding(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectedBasketCode") String DefectedBasketCode,
          @Query("NewBasketCode") String NewBasketCode,
          @Query("DefectGroupId") int DefectGroupId,
          @Query("DT") String DT
  );
  @GET("DefectedQualityOk_Painting")
  Single<ApiResponseDefectedQualityOk_Painting> DefectedQualityOk_Painting(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectedBasketCode") String DefectedBasketCode,
          @Query("NewBasketCode") String NewBasketCode,
          @Query("DefectGroupId") int DefectGroupId,
          @Query("DT") String DT
  );
  @GET("ManufacturingRejectionRequestCloseRequest")
  Single<ApiResponseManufacturingRejectionRequestCloseRequest> ManufacturingRejectionRequestCloseRequest(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("CertificateNo") String CertificateNo
  );
  @GET("WeldingRejectionRequestCloseRequest")
  Single<ApiResponseWeldingRejectionRequestCloseRequest> WeldingRejectionRequestCloseRequest(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("CertificateNo") String CertificateNo
  );
  @GET("PaintingRejectionRequestCloseRequest")
  Single<ApiResponsePaintingRejectionRequestCloseRequest> PaintingRejectionRequestCloseRequest(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("CertificateNo") String CertificateNo
  );

  @GET("GetRejectionReasonsList")
  Single<ApiResponseGetRejectionReasonsList> GetRejectionReasonsList(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo
  );

  @GET("GetWIP_Painting")
  Single<ApiResponseGetWIP_Painting> GetWIP_Painting(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceserialnumber);
  @GET("GetPaintQualityPprList")
  Single<ApiResponseGetPaintQualityPprList> GetPaintQualityPprList(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceserialnumber);
  @GET("GetStationInfoForQuality_Painting")
  Single<ApiResponseGetStationInfoForQuality_Painting> GetStationInfoForQuality_Painting(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("StationCode") String StationCode);
  @GET("ProductionSignOff_Painting")
  Single<ApiResponseProductionSignOff_Painting> ProductionSignOff_Painting(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("LoadingSequenceID") int loadingSequenceId);
  @GET("GetBasketInfo_ManufacturingProductionCounting")
  Single<ApiResponseGetBasketInfo_ManufacturingProductionCounting> GetBasketInfo_ManufacturingProductionCounting(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String BasketCode);

  @GET("SaveManufacturingProductionCounting")
  Single<ApiResponseSaveManufacturingProductionCounting> SaveManufacturingProductionCounting(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String BasketCode,
          @Query("ProductionCounting") int ProductionCounting);


  @GET("GetBasketInfo_WeldingProductionCounting")
  Single<ApiResponseGetBasketInfo_WeldingProductionCounting> GetBasketInfo_WeldingProductionCounting(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String BasketCode);

  @GET("SaveWeldingProductionCounting")
  Single<ApiResponseSaveWeldingProductionCounting> SaveWeldingProductionCounting(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String BasketCode,
          @Query("ProductionCounting") int ProductionCounting);


  @GET("GetStoppagesReasonsList")
  Single<ApiResponseGetStopageReasonsList> GetStoppagesReasonsList(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo
  );
  @GET("GetMachineData")
  Single<ApiResponseGetMachineData> GetMachineData(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("MachineCode") String MachineCode
  );

  @GET("TransferMachineLoading")
  Single<ApiResponseTransferMachineLoading> TransferMachineLoading(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("MachineCode1") String MachineCode1,
          @Query("MachineCode2") String MachineCode2,
          @Query("StoppageReasonId") int StoppageReasonId
  );
  @POST("MachineHold")
  Single<ApiResponseMachineHold> MachineHold(
          @Body MachineHoldData data
  );
  @POST("MachineReload")
  Single<ApiResponseMachineReload> MachineReload(@Body MachineReloadData data);
  @GET("GetMobileVersion")
  Single<ApiResponseGetMobileVersion> GetMobileVersion();
  @GET("GetWorkersList")
  Observable<ApiResponseGetWorkersList> GetWorkersList(
          @Query("applang") String applang,
          @Query("UserID") int userid,
          @Query("DeviceSerialNo") String DeviceSerialNo);
  @GET("GetStationWorkers")
  Observable<ApiResponseGetStationWorkers> GetStationWorkers(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("StationCode") String StationCode
  );
  @POST("AddWorkers_Station")
  Single<ApiResponseAddWorkers_Station> AddWorkers_Station(
          @Body AddWorkers_StationData data
  );
  @GET("GetMachineWorkers")
  Observable<ApiResponseGetMachineWorkers> GetMachineWorkers(
          @Query("applang") String applang,
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("MachineCode") String MachineCode
  );
  @POST("AddWorkers_Machine")
  Single<ApiResponseAddWorkers_Machine> AddWorkers_Machine(
          @Body AddWorkers_MachineData data
  );
  @POST("StationSignOff_Partial")
  Single<ApiWeldingsignoff<ResponseStatus>> StationSignOff_Partial(
          @Body WeldingSignoffBody_Partial weldingSignoffBody
  );
  @POST("MachineSignOff_Partial")
  Single<ApiMachinesignoff<ResponseStatus>> MachineSignOff_Partial(
          @Body MachineSignoffBody data
  );
}
