package com.example.gbsbadrsf.ProductionRepair.WeldingQuality.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.ProductionRepair.WeldingQuality.Data.ApiReponse.ApiResponseWeldingRepair_Production;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingProductionDefectRepairViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseWeldingRepair_Production> addWeldingRepairProduction;
    MutableLiveData<Status> addWeldingRepairProductionStatus;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public WeldingProductionDefectRepairViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addWeldingRepairProduction = new MutableLiveData<>();
        addWeldingRepairProductionStatus = new MutableLiveData<>();
    }

    public void addWeldingRepairProduction(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyRepaired
    ){
        disposable.add(apiInterface.WeldingRepair_Production(
                        LocaleHelper.getLanguage(getApplication().getApplicationContext()),
                userId,
                deviceSerialNumber,
                defectsManufacturingDetailsId,
                notes,
                defectStatus,
                qtyRepaired
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addWeldingRepairProductionStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addWeldingRepairProduction.postValue(response);
                            addWeldingRepairProductionStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addWeldingRepairProductionStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseWeldingRepair_Production> getAddWeldingRepairProduction() {
        return addWeldingRepairProduction;
    }

    public MutableLiveData<Status> getAddWeldingRepairProductionStatus() {
        return addWeldingRepairProductionStatus;
    }
}