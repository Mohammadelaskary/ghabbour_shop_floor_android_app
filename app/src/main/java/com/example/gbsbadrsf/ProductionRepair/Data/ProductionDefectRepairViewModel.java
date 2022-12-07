package com.example.gbsbadrsf.ProductionRepair.Data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProductionDefectRepairViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepairProduction ;
    MutableLiveData<Status> addManufacturingRepairProductionStatus;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public ProductionDefectRepairViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addManufacturingRepairProduction = new MutableLiveData<>();
        addManufacturingRepairProductionStatus = new MutableLiveData<>();
    }

    public void addManufacturingRepairProduction(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyRepaired
            ){
        disposable.add(apiInterface.addManufacturingRepair_Production(
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
                .doOnSubscribe( __ -> addManufacturingRepairProductionStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {addManufacturingRepairProduction.postValue(response);
                            addManufacturingRepairProductionStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addManufacturingRepairProductionStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> getAddManufacturingRepairProduction() {
        return addManufacturingRepairProduction;
    }

    public MutableLiveData<Status> getAddManufacturingRepairProductionStatus() {
        return addManufacturingRepairProductionStatus;
    }
}
