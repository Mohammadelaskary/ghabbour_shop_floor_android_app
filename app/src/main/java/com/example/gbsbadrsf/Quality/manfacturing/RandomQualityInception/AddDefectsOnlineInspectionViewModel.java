package com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect_Online;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseUpdateManufacturingDefects_Online;
import com.example.gbsbadrsf.Quality.Data.UpdateManufacturingDefectsData_Online;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddDefectsOnlineInspectionViewModel extends AndroidViewModel {
    private ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<Status> status;
    private SingleLiveEvent<ApiResponseDefectsList> defectsList;
    private SingleLiveEvent<ApiResponseAddingManufacturingDefect_Online> addManufacturingDefectsResponse;
    private SingleLiveEvent<ApiResponseUpdateManufacturingDefects_Online> updateManufacturingDefectsResponse;

    public AddDefectsOnlineInspectionViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();
        defectsList = new SingleLiveEvent<>();
        addManufacturingDefectsResponse = new SingleLiveEvent<>();
        updateManufacturingDefectsResponse = new SingleLiveEvent<>();
    }
    public void getDefects(int operationId){
        apiInterface.getDefectsListPerOperation(LocaleHelper.getLanguage(getApplication().getApplicationContext()),operationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseDefectsList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        status.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseDefectsList listApiResponseDefectsList) {
                        status.postValue(Status.SUCCESS);
                        defectsList.postValue(listApiResponseDefectsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                    }
                });
    }
    public void addManufacturingDefectResponseViewModel(AddManufacturingDefectData_Online addManufacturingDefectData){
        disposable.add(apiInterface.
                AddManufacturingDefect_Online(addManufacturingDefectData)
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

    public void updateManufacturingDefectResponseViewModel(UpdateManufacturingDefectsData_Online data){
        disposable.add(apiInterface.UpdateManufacturingDefect_Online(data)
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


    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public SingleLiveEvent<ApiResponseDefectsList> getDefectsList() {
        return defectsList;
    }

    public SingleLiveEvent<ApiResponseAddingManufacturingDefect_Online> getAddManufacturingDefectsResponse() {
        return addManufacturingDefectsResponse;
    }

    public SingleLiveEvent<ApiResponseUpdateManufacturingDefects_Online> getUpdateManufacturingDefectsResponse() {
        return updateManufacturingDefectsResponse;
    }
}