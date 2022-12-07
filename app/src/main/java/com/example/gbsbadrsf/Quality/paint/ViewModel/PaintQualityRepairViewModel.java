package com.example.gbsbadrsf.Quality.paint.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponsePaintingRepair_QC;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseDefectedQualityOk_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityRepairViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;
    MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> defectsPaintingListLiveData;
    MutableLiveData<Status> defectsPaintingListStatus;
    MutableLiveData<ApiResponsePaintingRepair_QC> addPaintingRepairQuality;
    MutableLiveData<Status> addPaintingRepairQualityStatus;
//    LastMovePaintingBasket basketData;
//    List<DefectsPainting> defectsPaintingList;
    MutableLiveData<ApiResponseDefectedQualityOk_Painting> DefectedQualityOk_Painting;
    MutableLiveData<Status> status;
    private String basketCode;
//    @Inject
//    Gson gson;
//    @Inject
    public PaintQualityRepairViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        defectsPaintingListLiveData = new MutableLiveData<>();
        defectsPaintingListStatus = new MutableLiveData<>();
        addPaintingRepairQuality = new MutableLiveData<>();
        addPaintingRepairQualityStatus = new MutableLiveData<>();
        DefectedQualityOk_Painting = new MutableLiveData<>();
        status = new MutableLiveData<>();
//        basketData = new LastMovePaintingBasket();
    }

    public void getBasketData(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Painting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,basketCode)
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

    public void getDefectsPaintingViewModel(int userId, String deviceSerialNo, String basketCode){
        disposable.add(apiInterface.getPaintingDefectedQtyByBasketCode(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId, deviceSerialNo, basketCode)                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsPaintingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            defectsPaintingListLiveData.postValue(response);
                            defectsPaintingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsPaintingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addPaintingRepairQuality(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyApproved
    ) {
        disposable.add(apiInterface.PaintingRepair_QC(
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
                .doOnSubscribe(__ -> addPaintingRepairQualityStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addPaintingRepairQuality.postValue(response);
                            addPaintingRepairQualityStatus.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            addPaintingRepairQualityStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void DefectedQualityOk_Painting(
            int userId,
            String deviceSerialNumber,
            String defectedBasketCode,
            String newBasketCode,
            int defectGroupId,
            String dt
    ) {
        disposable.add(apiInterface.DefectedQualityOk_Painting(
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
                            DefectedQualityOk_Painting.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> getApiResponseBasketDataLiveData() {
        return apiResponseBasketDataLiveData;
    }

    public MutableLiveData<Status> getApiResponseBasketDataStatus() {
        return apiResponseBasketDataStatus;
    }

    public MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> getDefectsPaintingListLiveData() {
        return defectsPaintingListLiveData;
    }

    public MutableLiveData<Status> getDefectsPaintingListStatus() {
        return defectsPaintingListStatus;
    }

    public MutableLiveData<ApiResponsePaintingRepair_QC> getAddPaintingRepairQuality() {
        return addPaintingRepairQuality;
    }

    public MutableLiveData<Status> getAddPaintingRepairQualityStatus() {
        return addPaintingRepairQualityStatus;
    }

//    public LastMovePaintingBasket getBasketData() {
//        return basketData;
//    }
//
//    public void setBasketData(LastMovePaintingBasket basketData) {
//        this.basketData = basketData;
//    }
//
//    public List<DefectsPainting> getDefectsPaintingList() {
//        return defectsPaintingList;
//    }
//
//    public void setDefectsPaintingList(List<DefectsPainting> defectsPaintingList) {
//        this.defectsPaintingList = defectsPaintingList;
//    }

    public MutableLiveData<ApiResponseDefectedQualityOk_Painting> getDefectedQualityOk_Painting() {
        return DefectedQualityOk_Painting;
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
