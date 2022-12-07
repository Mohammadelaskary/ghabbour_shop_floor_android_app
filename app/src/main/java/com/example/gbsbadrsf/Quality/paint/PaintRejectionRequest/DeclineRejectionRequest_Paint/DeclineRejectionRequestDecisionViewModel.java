package com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.DeclineRejectionRequest_Paint;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRejectionReasonsList;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DeclineRejectionRequestDecisionViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseDepartmentsList> apiResponseDepartmentsListLiveData;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseGetRejectionReasonsList> apiResponseReasonsList;
    MutableLiveData<ApiResponsePaintingRejectionRequestGetRejectionRequestByID> getRejectionRequestData;
    MutableLiveData<ApiResponsePaintingRejectionRequestCloseDeclinedRequest> saveCommitteeDecision;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public DeclineRejectionRequestDecisionViewModel(Application application) {
        super(application);
        disposable = new CompositeDisposable();
        apiResponseDepartmentsListLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        apiResponseReasonsList = new MutableLiveData<>();
        getRejectionRequestData = new MutableLiveData<>();
        saveCommitteeDecision = new MutableLiveData<>();
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
    }

    public void getDepartmentsList(int userId){
        disposable.add(apiInterface.getDepartmentsList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseDepartmentsListLiveData.postValue(response);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
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

    public void getRejectionRequestData(int userId,String deviceSerialNo,int rejectionRequestId){
        disposable.add(apiInterface.PaintingRejectionRequestGetRejectionRequestByID(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,rejectionRequestId)
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
    public void setSaveCommitteeDecision(
            int userId,
            String deviceSerialNo,
            int rejectionRequestId,
            String OkQty,
            String OkBasketCode,
            String RejectedQty,
            int DepartmentId,
            int RejectionReasonID,
            String Notes){
        disposable.add(apiInterface.saveCommitteeDecision_Paint(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,rejectionRequestId,OkQty,OkBasketCode,RejectedQty,DepartmentId,RejectionReasonID,Notes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {
                            saveCommitteeDecision.postValue(apiResponseGetRejectionRequestList);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseDepartmentsList> getApiResponseDepartmentsListLiveData() {
        return apiResponseDepartmentsListLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseGetRejectionReasonsList> getApiResponseReasonsList() {
        return apiResponseReasonsList;
    }

    public MutableLiveData<ApiResponsePaintingRejectionRequestGetRejectionRequestByID> getGetRejectionRequestData() {
        return getRejectionRequestData;
    }

    public MutableLiveData<ApiResponsePaintingRejectionRequestCloseDeclinedRequest> getSaveCommitteeDecision() {
        return saveCommitteeDecision;
    }
}