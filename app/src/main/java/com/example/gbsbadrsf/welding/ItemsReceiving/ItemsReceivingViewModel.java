package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseGetJobOrderIssuedChilds;
import com.example.gbsbadrsf.Model.ApiResponseGetJobOrdersForIssue;
import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ItemsReceivingViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<ApiResponseGetJobOrdersForIssue> jobOrdersForIssue;
    private MutableLiveData<ApiResponseGetJobOrderIssuedChilds> jobOrdersIssuedChilds;
    private MutableLiveData<Status> status;


//    @Inject
//    Gson gson;
//    @Inject
    public ItemsReceivingViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        jobOrdersForIssue = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }
    public void GetJobOrdersForIssue(int userId,String deviceSerialNo){
        disposable.add(apiInterface.GetJobOrdersForIssue(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            jobOrdersForIssue.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void GetJobOrdersIssuedChilds(int userId,String deviceSerialNo,int entityId){
        disposable.add(apiInterface.GetJobOrderIssuedChilds(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,entityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            jobOrdersIssuedChilds.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetJobOrdersForIssue> getJobOrdersForIssue() {
        return jobOrdersForIssue;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseGetJobOrderIssuedChilds> getJobOrdersIssuedChilds() {
        return jobOrdersIssuedChilds;
    }
}