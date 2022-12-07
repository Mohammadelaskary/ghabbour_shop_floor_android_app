package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MachineHoldViewModel extends AndroidViewModel {
    private ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private SingleLiveEvent<ResponseStatus> checkBasketEmpty ;
    private SingleLiveEvent<ApiResponseMachineHold> saveMachineHold ;
    private MutableLiveData<Status> status;

    public MachineHoldViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        checkBasketEmpty = new SingleLiveEvent<>();
        status = new MutableLiveData<>();
        saveMachineHold = new SingleLiveEvent<>();
    }
    void checkBasketEmpty(String basketCode, String parentId, String childId, String jobOrderId, String operationId){
        disposable.add(apiInterface.checkBasketStatus(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode,parentId,childId,jobOrderId,operationId,0)
//                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribeOn(Schedulers.io())
                .subscribe((response, throwable) -> {
                    checkBasketEmpty.postValue(response.getResponseStatus());
//                    status.postValue(Status.SUCCESS);
                }));

    }
    void saveMachineHold(MachineHoldData data){
        disposable.add(apiInterface.MachineHold(data)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribeOn(Schedulers.io())
                .subscribe(apiResponseMachineHold -> {
                    saveMachineHold.postValue(apiResponseMachineHold);
                    status.postValue(Status.SUCCESS);
                },throwable -> status.postValue(Status.ERROR)));

    }

    public SingleLiveEvent<ResponseStatus> getCheckBasketEmpty() {
        return checkBasketEmpty;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public SingleLiveEvent<ApiResponseMachineHold> getSaveMachineHold() {
        return saveMachineHold;
    }
}