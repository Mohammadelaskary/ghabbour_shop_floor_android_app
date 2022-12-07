package com.example.gbsbadrsf.BasketInfo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import io.reactivex.disposables.CompositeDisposable;

public class BasketInfoViewModel extends AndroidViewModel {
    Gson gson;
    private MutableLiveData<ApiResponseBasketsWIP> apiResponseBasketsWIP;
    private MutableLiveData<Status> status;




//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

//    @Inject
    public BasketInfoViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        apiResponseBasketsWIP = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    void getBasketWIP(int userID,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.GetManufacturingBasketWIP(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userID,deviceSerialNo,basketCode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(response -> {
                    apiResponseBasketsWIP.postValue(response);
                    status.postValue(Status.SUCCESS);
                },throwable -> {
                    status.postValue(Status.ERROR);
                }));

    }

    public MutableLiveData<ApiResponseBasketsWIP> getApiResponseBasketsWIP() {
        return apiResponseBasketsWIP;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}