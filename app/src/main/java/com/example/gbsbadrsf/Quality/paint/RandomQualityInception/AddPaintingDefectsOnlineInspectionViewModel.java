package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect_Online;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseUpdateManufacturingDefects_Online;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddPaintingDefectsOnlineInspectionViewModel extends AndroidViewModel {
    private ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<Status> status;
    private SingleLiveEvent<ApiResponseDefectsList> defectsList;
    private SingleLiveEvent<ApiResponseAddingPaintingDefect_Online> addPaintingDefectsResponse;
    private SingleLiveEvent<ApiResponseUpdatePaintingDefects_Online> updatePaintingDefectsResponse;

    public AddPaintingDefectsOnlineInspectionViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();
        defectsList = new SingleLiveEvent<>();
        addPaintingDefectsResponse = new SingleLiveEvent<>();
        updatePaintingDefectsResponse = new SingleLiveEvent<>();
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
    public void addPaintingDefectResponseViewModel(AddPaintingDefectData_Online addPaintingDefectData){
        disposable.add(apiInterface.AddPaintingDefect_Online(addPaintingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsManufacturing -> {
                            status.postValue(Status.SUCCESS);
                            addPaintingDefectsResponse.postValue(apiResponseAddingDefectsManufacturing);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public void updatePaintingDefectResponseViewModel(UpdatePaintingDefectsData_Online data){
        disposable.add(apiInterface.UpdatePaintingDefect_Online(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseUpdatingDefectsPainting -> {
                            status.postValue(Status.SUCCESS);
                            updatePaintingDefectsResponse.postValue(apiResponseUpdatingDefectsPainting);
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

    public SingleLiveEvent<ApiResponseAddingPaintingDefect_Online> getAddPaintingDefectsResponse() {
        return addPaintingDefectsResponse;
    }

    public SingleLiveEvent<ApiResponseUpdatePaintingDefects_Online> getUpdatePaintingDefectsResponse() {
        return updatePaintingDefectsResponse;
    }
}