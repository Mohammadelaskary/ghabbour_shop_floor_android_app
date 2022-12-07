package com.example.gbsbadrsf.Quality.welding.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseDefectedQualityOk_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingQualityDefectRepairViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseWeldingRepair_QC> addWeldingRepairQuality;
    MutableLiveData<Status> addWeldingRepairQualityStatus;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    private final MutableLiveData<ApiResponseDefectedQualityOk_Welding> apiResponseDefectedQualityOk_welding;
    private final MutableLiveData<Status> status;


//    @Inject
//    Gson gson;
//
//    @Inject
    public WeldingQualityDefectRepairViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addWeldingRepairQuality = new MutableLiveData<>();
        addWeldingRepairQualityStatus = new MutableLiveData<>();
        apiResponseDefectedQualityOk_welding = new MutableLiveData<>();
        status = new MutableLiveData<>();
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


    public MutableLiveData<ApiResponseWeldingRepair_QC> getAddWeldingRepairQuality() {
        return addWeldingRepairQuality;
    }

    public MutableLiveData<Status> getAddWeldingRepairQualityStatus() {
        return addWeldingRepairQualityStatus;
    }

    public MutableLiveData<ApiResponseDefectedQualityOk_Welding> getApiResponseDefectedQualityOk_welding() {
        return apiResponseDefectedQualityOk_welding;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}