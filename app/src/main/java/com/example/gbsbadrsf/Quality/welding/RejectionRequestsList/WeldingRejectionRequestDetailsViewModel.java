package com.example.gbsbadrsf.Quality.welding.RejectionRequestsList;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.Quality.manfacturing.Model.ApiResponseRejectionRequestTakeAction;
import com.example.gbsbadrsf.Quality.welding.WeldingRejectionRequest.DeclineRejectionRequest_Welding.ApiResponseWeldingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingRejectionRequestDetailsViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseRejectionRequestTakeAction> rejectionRequestTakeActionLiveData;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseWeldingRejectionRequestGetRejectionRequestByID> getRejectionRequestById;
//    @Inject
//    Gson gson;
//    @Inject
    public WeldingRejectionRequestDetailsViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        rejectionRequestTakeActionLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        getRejectionRequestById = new MutableLiveData<>();

    }

    public void saveRejectionRequestTakeAction(int userId,
                                     int rejectionRequestId,
                                     boolean isApproved){
        disposable.add(apiInterface.RejectionRequestTakeAction_Welding(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,rejectionRequestId,isApproved)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseRejectionRequestTakeAction -> {rejectionRequestTakeActionLiveData.postValue(apiResponseRejectionRequestTakeAction);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public void getDefects(int userId,
                           String deviceSerialNo,
                           int rejectionRequestId
    ){
        disposable.add(apiInterface.WeldingRejectionRequestGetRejectionRequestByID(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,rejectionRequestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseRejectionRequestTakeAction -> {getRejectionRequestById.postValue(apiResponseRejectionRequestTakeAction);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseRejectionRequestTakeAction> getRejectionRequestTakeActionLiveData() {
        return rejectionRequestTakeActionLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseWeldingRejectionRequestGetRejectionRequestByID> getGetRejectionRequestById() {
        return getRejectionRequestById;
    }
}
