package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingRejectionRequest.ApprovalRejectionRequest_Manufacturing;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RejectionRequestClosingViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> getRejectionRequestData;
    MutableLiveData<ApiResponseManufacturingRejectionRequestCloseRequest> getCloseRejectionRequestResponse;
    MutableLiveData<Status> status;
    private MutableLiveData<Throwable> error;

//    @Inject
//    Gson gson;
//    @Inject
    public RejectionRequestClosingViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        getRejectionRequestData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        error = new MutableLiveData<>();
        getCloseRejectionRequestResponse = new MutableLiveData<>();
    }

    public void getRejectionRequestData(int userId,String deviceSerialNo,int rejectionRequestId){
        disposable.add(apiInterface.ManufacturingRejectionRequestGetRejectionRequestByID(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,rejectionRequestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {
                            getRejectionRequestData.postValue(apiResponseGetRejectionRequestList);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);

                        }
                ));
    }
    public void closeRejectionRequest(int userId,String deviceSerialNo,int rejectionRequestId,String certificateNo){
        disposable.add(apiInterface.ManufacturingRejectionRequestCloseRequest(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,rejectionRequestId,certificateNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {
                            getCloseRejectionRequestResponse.postValue(apiResponseGetRejectionRequestList);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> getGetRejectionRequestData() {
        return getRejectionRequestData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestCloseRequest> getGetCloseRejectionRequestResponse() {
        return getCloseRejectionRequestResponse;
    }
}