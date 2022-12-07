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

public class ManufacturingAddDefectsDetailsViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddingManufacturingDefect> addManufacturingDefectsResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseUpdateManufacturingDefects> updateManufacturingDefectsResponse;

//    @Inject
//    Gson gson;
//    @Inject
    public ManufacturingAddDefectsDetailsViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addManufacturingDefectsResponse = new MutableLiveData<>();
        updateManufacturingDefectsResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getDefectsListViewModel(int operationId){
        disposable.add(apiInterface.getDefectsListPerOperation(LocaleHelper.getLanguage(getApplication().getApplicationContext()),operationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->defectsListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            defectsListLiveData.postValue(response);
                            defectsListStatus.postValue(Status.SUCCESS);},
                        throwable -> {
                            defectsListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addManufacturingDefectResponseViewModel(AddManufacturingDefectData addManufacturingDefectData){
        disposable.add(apiInterface.AddManufacturingDefect(addManufacturingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsManufacturing -> {
                            status.postValue(Status.SUCCESS);
                            addManufacturingDefectsResponse.postValue(apiResponseAddingDefectsManufacturing);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public void updateManufacturingDefectResponseViewModel(UpdateManufacturingDefectsData data){
        disposable.add(apiInterface.UpdateManufacturingDefect(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseUpdatingDefectsManufacturing -> {
                            status.postValue(Status.SUCCESS);
                            updateManufacturingDefectsResponse.postValue(apiResponseUpdatingDefectsManufacturing);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddingManufacturingDefect> getAddManufacturingDefectsResponse() {
        return addManufacturingDefectsResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseUpdateManufacturingDefects> getUpdateManufacturingDefectsResponse() {
        return updateManufacturingDefectsResponse;
    }
}
