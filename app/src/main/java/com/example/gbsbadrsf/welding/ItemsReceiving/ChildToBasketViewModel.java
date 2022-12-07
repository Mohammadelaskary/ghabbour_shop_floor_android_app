package com.example.gbsbadrsf.welding.ItemsReceiving;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseGetJobOrderIssuedChilds;
import com.example.gbsbadrsf.Model.ApiResponseTransferIssuedChildToBasket;
import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChildToBasketViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetJobOrderIssuedChilds> jobOrdersIssuedChilds;
    MutableLiveData<ApiResponseTransferIssuedChildToBasket> transferIssuedChildToBasketResponse;
    MutableLiveData<Status> status;


//    @Inject
//    Gson gson;
//    @Inject
    public ChildToBasketViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        jobOrdersIssuedChilds = new MutableLiveData<>();
        transferIssuedChildToBasketResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
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
                            Log.d(TAG, "GetJobOrdersIssuedChilds: "+throwable.getMessage());
                        }
                ));
    }
    public void TransferIssuedChildToBasket(PutChildsToBasketData data){
        disposable.add(apiInterface.TransferIssuedChildToBasket(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            transferIssuedChildToBasketResponse.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetJobOrderIssuedChilds> getJobOrdersIssuedChilds() {
        return jobOrdersIssuedChilds;
    }

    public MutableLiveData<ApiResponseTransferIssuedChildToBasket> getTransferIssuedChildToBasketResponse() {
        return transferIssuedChildToBasketResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}