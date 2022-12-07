package com.example.gbsbadrsf.Manfacturing.machineloading;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.BasketInfo.ApiResponseBasketsWIP;
import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ReloadHoldViewModel extends AndroidViewModel {
    private CompositeDisposable disposable;
    private ApiInterface apiInterface;
    private SingleLiveEvent<Status> status;
    private SingleLiveEvent<ApiResponseBasketsWIP> basketData;
    private SingleLiveEvent<ApiResponseMachineReload> machineReload;

    public ReloadHoldViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        status = new SingleLiveEvent<>();
        basketData = new SingleLiveEvent<>();
        machineReload = new SingleLiveEvent<>();
    }

    public SingleLiveEvent<Status> getStatus() {
        return status;
    }

    public SingleLiveEvent<ApiResponseBasketsWIP> getBasketData() {
        return basketData;
    }

    public void getBasketData(String basketCode) {
        disposable.add(apiInterface.GetManufacturingBasketWIP(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    basketData.postValue(response);
                    status.postValue(Status.SUCCESS);
                },throwable -> status.postValue(Status.ERROR))
        );
    }

    public void machineReload(MachineReloadData data) {
        disposable.add(apiInterface.MachineReload(data)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    machineReload.postValue(response);
                    status.postValue(Status.SUCCESS);
                },throwable -> status.postValue(Status.ERROR))
        );
    }

    public SingleLiveEvent<ApiResponseMachineReload> getMachineReload() {
        return machineReload;
    }
}