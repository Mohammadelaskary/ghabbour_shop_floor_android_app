package com.example.gbsbadrsf.Quality.welding.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseDefectedQualityOk_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingQualityRepairViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;
    MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> defectsWeldingListLiveData;
    MutableLiveData<Status> defectsWeldingListStatus;
    MutableLiveData<ApiResponseWeldingRepair_QC> addWeldingRepairQuality;
    MutableLiveData<Status> addWeldingRepairQualityStatus;
//    LastMoveWeldingBasket basketData;
//    List<DefectsWelding> defectsWeldingList;

    private MutableLiveData<ApiResponseDefectedQualityOk_Welding> apiResponseDefectedQualityOk_welding;
    private MutableLiveData<Status> status;
    private String basketCode;

//    @Inject
//    Gson gson;
//    @Inject
    public WeldingQualityRepairViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        defectsWeldingListLiveData = new MutableLiveData<>();
        defectsWeldingListStatus = new MutableLiveData<>();
        addWeldingRepairQuality = new MutableLiveData<>();
        addWeldingRepairQualityStatus = new MutableLiveData<>();
        apiResponseDefectedQualityOk_welding = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getBasketData(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Welding(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseBasketDataStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseBasketDataLiveData.postValue(response);
                            apiResponseBasketDataStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseBasketDataStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public void getDefectsWeldingViewModel(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getWeldingDefectedQtyByBasketCode(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId, deviceSerialNo, basketCode)                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsWeldingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {defectsWeldingListLiveData.postValue(response);
                            defectsWeldingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsWeldingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addWeldingRepairQuality(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyApproved
    ) {
        disposable.add(apiInterface.WeldingRepair_QC(
                LocaleHelper.getLanguage(getApplication().getApplicationContext()),
                userId,
                deviceSerialNumber,
                defectsManufacturingDetailsId,
                notes,
                defectStatus,
                qtyApproved
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> addWeldingRepairQualityStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addWeldingRepairQuality.postValue(response);
                            addWeldingRepairQualityStatus.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            addWeldingRepairQualityStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public void DefectedQualityOk_Welding(
            int userId,
            String deviceSerialNumber,
            String defectedBasketCode,
            String newBasketCode,
            int defectGroupId,
            String dt
    ) {
        disposable.add(apiInterface.DefectedQualityOk_Welding(
                        LocaleHelper.getLanguage(getApplication().getApplicationContext()),
                        userId,
                        deviceSerialNumber,
                        defectedBasketCode,
                        newBasketCode,
                        defectGroupId,
                        dt
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            apiResponseDefectedQualityOk_welding.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseWeldingRepair_QC> getAddWeldingRepairQuality() {
        return addWeldingRepairQuality;
    }

    public MutableLiveData<Status> getAddWeldingRepairQualityStatus() {
        return addWeldingRepairQualityStatus;
    }

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> getApiResponseBasketDataLiveData() {
        return apiResponseBasketDataLiveData;
    }

    public MutableLiveData<Status> getApiResponseBasketDataStatus() {
        return apiResponseBasketDataStatus;
    }

    public MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> getDefectsWeldingListLiveData() {
        return defectsWeldingListLiveData;
    }

    public MutableLiveData<Status> getDefectsWeldingListStatus() {
        return defectsWeldingListStatus;
    }

//    public LastMoveWeldingBasket getBasketData() {
//        return basketData;
//    }
//
//    public void setBasketData(LastMoveWeldingBasket basketData) {
//        this.basketData = basketData;
//    }
//
//    public List<DefectsWelding> getDefectsWeldingList() {
//        return defectsWeldingList;
//    }
//
//    public void setDefectsWeldingList(List<DefectsWelding> defectsWeldingList) {
//        this.defectsWeldingList = defectsWeldingList;
//    }

    public MutableLiveData<ApiResponseDefectedQualityOk_Welding> getApiResponseDefectedQualityOk_welding() {
        return apiResponseDefectedQualityOk_welding;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }
}
