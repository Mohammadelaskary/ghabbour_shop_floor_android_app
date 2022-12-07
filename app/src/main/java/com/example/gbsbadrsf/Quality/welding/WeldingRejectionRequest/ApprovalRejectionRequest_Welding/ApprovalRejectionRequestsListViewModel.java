package com.example.gbsbadrsf.Quality.welding.WeldingRejectionRequest.ApprovalRejectionRequest_Welding;

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

public class ApprovalRejectionRequestsListViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    private MutableLiveData<ApiResponseWeldingRejectionRequestGetRejectionRequestList> getRejectionRequestListLiveData;
    private MutableLiveData<Status> getRejectionRequestListStatus;
    private MutableLiveData<Throwable> error;

//    @Inject
//    Gson gson;
//    @Inject
    public ApprovalRejectionRequestsListViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        getRejectionRequestListLiveData = new MutableLiveData<>();
        getRejectionRequestListStatus   = new MutableLiveData<>();
        error = new MutableLiveData<>();
    }

    public void getRejectionRequests(int userId,String deviceSerialNo){
        disposable.add(apiInterface.getApprovalRejectionRequestsList_Welding(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> getRejectionRequestListStatus.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {getRejectionRequestListLiveData.postValue(apiResponseGetRejectionRequestList);
                            getRejectionRequestListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            getRejectionRequestListStatus.postValue(Status.ERROR);
                            error.postValue(throwable);
                        }
                ));
    }

    public MutableLiveData<ApiResponseWeldingRejectionRequestGetRejectionRequestList> getGetRejectionRequestListLiveData() {
        return getRejectionRequestListLiveData;
    }

    public MutableLiveData<Status> getGetRejectionRequestListStatus() {
        return getRejectionRequestListStatus;
    }

    public MutableLiveData<Throwable> getError() {
        return error;
    }
}