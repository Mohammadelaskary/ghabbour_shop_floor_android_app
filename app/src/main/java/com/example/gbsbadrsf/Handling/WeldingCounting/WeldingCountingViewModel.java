package com.example.gbsbadrsf.Handling.WeldingCounting;

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

public class WeldingCountingViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<ApiResponseGetBasketInfo_WeldingProductionCounting> basketData;
    private MutableLiveData<Status> status;

    private MutableLiveData<ApiResponseSaveWeldingProductionCounting> saveWeldingCount;


//    @Inject
//    Gson gson;
//    @Inject
    public WeldingCountingViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        basketData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        saveWeldingCount = new MutableLiveData<>();
    }

    public void getBasketData(String basketCode){
        disposable.add(apiInterface.GetBasketInfo_WeldingProductionCounting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode)
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
        disposable.add(apiInterface.SaveWeldingProductionCounting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode,productionCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponse -> {
                            status.postValue(Status.SUCCESS);
                            saveWeldingCount.postValue(apiResponse);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseGetBasketInfo_WeldingProductionCounting> getBasketData() {
        return basketData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseSaveWeldingProductionCounting> getSaveWeldingCount() {
        return saveWeldingCount;
    }
}