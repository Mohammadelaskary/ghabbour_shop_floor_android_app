package com.example.gbsbadrsf.ProductionRepair.PaintProductionRepair.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.ProductionRepair.PaintProductionRepair.ApiReponse.ApiResponsePaintingRepair_Production;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintProductionDefectRepairViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponsePaintingRepair_Production> addPaintingRepairProduction;
    MutableLiveData<Status> addPaintingRepairProductionStatus;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintProductionDefectRepairViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addPaintingRepairProduction = new MutableLiveData<>();
        addPaintingRepairProductionStatus = new MutableLiveData<>();
    }

    public void addPaintingRepairProduction(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyRepaired
    ){
        disposable.add(apiInterface.PaintingRepair_Production(
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
                .doOnSubscribe( __ -> addPaintingRepairProductionStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addPaintingRepairProduction.postValue(response);
                            addPaintingRepairProductionStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addPaintingRepairProductionStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponsePaintingRepair_Production> getAddPaintingRepairProduction() {
        return addPaintingRepairProduction;
    }

    public MutableLiveData<Status> getAddPaintingRepairProductionStatus() {
        return addPaintingRepairProductionStatus;
    }
}