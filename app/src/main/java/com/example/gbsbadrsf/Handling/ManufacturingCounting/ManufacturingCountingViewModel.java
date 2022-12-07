package com.example.gbsbadrsf.Handling.ManufacturingCounting;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

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

public class ManufacturingCountingViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<ApiResponseGetBasketInfo_ManufacturingProductionCounting> basketData;
    private MutableLiveData<Status> status;

    private MutableLiveData<ApiResponseSaveManufacturingProductionCounting> saveManufacturingCount;


//    @Inject
//    Gson gson;
//    @Inject
    public ManufacturingCountingViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        basketData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        saveManufacturingCount = new MutableLiveData<>();
    }

    public void getBasketData(String basketCode){
        disposable.add(apiInterface.GetBasketInfo_ManufacturingProductionCounting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            basketData.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void setSaveManufacturingCount(String basketCode,int productionCount){
        disposable.add(apiInterface.SaveManufacturingProductionCounting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode,productionCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponse -> {
                            status.postValue(Status.SUCCESS);
                            saveManufacturingCount.postValue(apiResponse);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseGetBasketInfo_ManufacturingProductionCounting> getBasketData() {
        return basketData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseSaveManufacturingProductionCounting> getSaveManufacturingCount() {
        return saveManufacturingCount;
    }
}