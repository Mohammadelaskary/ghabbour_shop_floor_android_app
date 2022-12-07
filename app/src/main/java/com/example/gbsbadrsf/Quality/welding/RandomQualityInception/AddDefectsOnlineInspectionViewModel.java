package com.example.gbsbadrsf.Quality.welding.RandomQualityInception;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception.UpdateWeldingDefectsData_Online;
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
    private SingleLiveEvent<ApiResponseAddingWeldingDefect_Online> addWeldingDefectsResponse;
    private SingleLiveEvent<ApiResponseUpdatingWeldingDefect_Online> updateWeldingDefectsResponse;

    public AddDefectsOnlineInspectionViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();
        defectsList = new SingleLiveEvent<>();
        addWeldingDefectsResponse = new SingleLiveEvent<>();
        updateWeldingDefectsResponse = new SingleLiveEvent<>();
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
    public void addWeldingDefectResponseViewModel(AddWeldingDefectData_Online addWeldingDefectData){
        disposable.add(apiInterface.AddWeldingDefect_Online(addWeldingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsWelding -> {
                            status.postValue(Status.SUCCESS);
                            addWeldingDefectsResponse.postValue(apiResponseAddingDefectsWelding);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public void updateWeldingDefectResponseViewModel(UpdateWeldingDefectsData_Online data){
        disposable.add(apiInterface.UpdateWeldingDefect_Online(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseUpdatingDefectsManufacturing -> {
                            status.postValue(Status.SUCCESS);
                            updateWeldingDefectsResponse.postValue(apiResponseUpdatingDefectsManufacturing);
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

    public SingleLiveEvent<ApiResponseAddingWeldingDefect_Online> getAddWeldingDefectsResponse() {
        return addWeldingDefectsResponse;
    }

    public SingleLiveEvent<ApiResponseUpdatingWeldingDefect_Online> getUpdateWeldingDefectsResponse() {
        return updateWeldingDefectsResponse;
    }
}