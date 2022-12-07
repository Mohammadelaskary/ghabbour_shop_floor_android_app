package com.example.gbsbadrsf.Quality.paint.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRejectionReasonsList;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseRejectionRequest_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.SaveRejectionRequestBody_Painting;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintRejectionRequestViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseDepartmentsList> apiResponseDepartmentsListLiveData;
    MutableLiveData<Status> apiResponseDepartmentsListStatus;

    MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;

    MutableLiveData<ApiResponseRejectionRequest_Painting> apiResponseSaveRejectionRequestLiveData;
    MutableLiveData<Status> apiResponseSaveRejectionRequestStatus;

    MutableLiveData<ApiResponseDefectsList> apiResponseDefectsListPerOperation;
    MutableLiveData<Status> apiResponseDefectsListPerOperationStatus;

    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseGetRejectionReasonsList> apiResponseReasonsList;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintRejectionRequestViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        apiResponseDepartmentsListLiveData = new MutableLiveData<>();
        apiResponseDepartmentsListStatus = new MutableLiveData<>();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        apiResponseSaveRejectionRequestStatus = new MutableLiveData<>();
        apiResponseSaveRejectionRequestLiveData = new MutableLiveData<>();
        apiResponseDefectsListPerOperation = new MutableLiveData<>();
        apiResponseDefectsListPerOperationStatus = new MutableLiveData<>();
        status = new MutableLiveData<>();
        apiResponseReasonsList = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerial,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Painting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerial,basketCode)
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
    public void getDepartmentsList(int userId){
        disposable.add(apiInterface.getDepartmentsList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseDepartmentsListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseDepartmentsListLiveData.postValue(response);
                            apiResponseDepartmentsListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseDepartmentsListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void getDefectsList(int operationId){
        disposable.add(apiInterface.getDefectsListPerOperation(LocaleHelper.getLanguage(getApplication().getApplicationContext()),operationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseDefectsListPerOperationStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseDefectsListPerOperation.postValue(response);
                            apiResponseDefectsListPerOperationStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseDefectsListPerOperationStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void getReasonsList(int userId,String deviceSerialNo){
        disposable.add(apiInterface.GetRejectionReasonsList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseReasonsList.postValue(response);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void saveRejectionRequest(SaveRejectionRequestBody_Painting body){
        disposable.add(apiInterface.RejectionRequest_Painting(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseSaveRejectionRequestStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseSaveRejectionRequestLiveData.postValue(response);
                            apiResponseSaveRejectionRequestStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseSaveRejectionRequestStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseDepartmentsList> getApiResponseDepartmentsListLiveData() {
        return apiResponseDepartmentsListLiveData;
    }

    public MutableLiveData<Status> getApiResponseDepartmentsListStatus() {
        return apiResponseDepartmentsListStatus;
    }

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> getApiResponseBasketDataLiveData() {
        return apiResponseBasketDataLiveData;
    }

    public MutableLiveData<Status> getApiResponseBasketDataStatus() {
        return apiResponseBasketDataStatus;
    }

    public MutableLiveData<ApiResponseRejectionRequest_Painting> getApiResponseSaveRejectionRequestLiveData() {
        return apiResponseSaveRejectionRequestLiveData;
    }

    public MutableLiveData<Status> getApiResponseSaveRejectionRequestStatus() {
        return apiResponseSaveRejectionRequestStatus;
    }

    public MutableLiveData<ApiResponseDefectsList> getApiResponseDefectsListPerOperation() {
        return apiResponseDefectsListPerOperation;
    }

    public MutableLiveData<Status> getApiResponseDefectsListPerOperationStatus() {
        return apiResponseDefectsListPerOperationStatus;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseGetRejectionReasonsList> getApiResponseReasonsList() {
        return apiResponseReasonsList;
    }
}