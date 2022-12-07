package com.example.gbsbadrsf.Quality.Data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QualityDefectRepairViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepairQuality;
    MutableLiveData<ApiResponseDefectedQualityOk_Manufacturing> DefectedQualityOk_Manufacturing;
    MutableLiveData<Status> status;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//
//    @Inject
    public QualityDefectRepairViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addManufacturingRepairQuality = new MutableLiveData<>();
        status = new MutableLiveData<>();
        DefectedQualityOk_Manufacturing = new MutableLiveData<>();
    }

    public void addManufacturingRepairQuality(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyApproved
    ) {
        disposable.add(apiInterface.addManufacturingRepair_QC(
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
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addManufacturingRepairQuality.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void DefectedQualityOk_Manufacturing(
            int userId,
            String deviceSerialNumber,
            String defectedBasketCode,
            String newBasketCode,
            int groupId,
            String dt
    ) {
        disposable.add(apiInterface.DefectedQualityOk_Manufacturing(
                LocaleHelper.getLanguage(getApplication().getApplicationContext()),
                userId,
                deviceSerialNumber,
                defectedBasketCode,
                newBasketCode,
                groupId,
                dt
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            DefectedQualityOk_Manufacturing.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> getAddManufacturingRepairQuality() {
        return addManufacturingRepairQuality;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseDefectedQualityOk_Manufacturing> getDefectedQualityOk_Manufacturing() {
        return DefectedQualityOk_Manufacturing;
    }
}