package com.example.gbsbadrsf.welding.weldingsignoff;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;


import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ApiWeldingsignoff;
import com.example.gbsbadrsf.data.response.Apiinfoforstationcode;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody_Partial;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.WeldingSignoffrepository;

import java.nio.channels.SelectableChannel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SignoffweViewModel extends AndroidViewModel {
//    @Inject
//    Gson gson;
//@Inject
    ApiInterface apiInterface;
    WeldingSignoffrepository repository;

    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Stationcodeloading>stationcodeloadingMutableLiveData;
    private MutableLiveData<Status> status;
    private MutableLiveData<Weldingsignoffcases>weldingsignoffcases;
    private MutableLiveData<ResponseStatus> checkBasketEmpty ;
    private MutableLiveData<Apiinfoforstationcode<Stationcodeloading>> getStationData;
    private MutableLiveData<ApiWeldingsignoff<ResponseStatus>> saveSignOffResponse;


    private CompositeDisposable disposable;
//    @Inject
    public SignoffweViewModel(Application application) {
        super(application);
        stationcodeloadingMutableLiveData= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        responseLiveData = new MutableLiveData<>();
        weldingsignoffcases=new MutableLiveData<>();
        status = new MutableLiveData<>();
        getStationData = new MutableLiveData<>();
        saveSignOffResponse = new MutableLiveData<>();
        checkBasketEmpty = new MutableLiveData<>();
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        this.repository=new WeldingSignoffrepository();



    }
    void getstationcodedata(int userid,String deviceserialnum,String productionstationcode){
        disposable.add(apiInterface.getinfoforstationcode(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userid,deviceserialnum,productionstationcode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((getinfoforstationcode, throwable) -> {
//                    if (getinfoforstationcode.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
//                    {
//                        weldingsignoffcases.postValue(Weldingsignoffcases.gettingsuccesfully);
//                        stationcodeloadingMutableLiveData.postValue(getinfoforstationcode.getData());
//
//                    }
//                    else if(getinfoforstationcode.getResponseStatus().getStatusMessage().equals("Wrong production station name")){
//                        weldingsignoffcases.postValue(Weldingsignoffcases.Wrongproductionstatname);
//
//
//                    }
//
                    getStationData.postValue(getinfoforstationcode);
                    status.postValue(Status.SUCCESS);
                }));

    }
    void checkBasketEmpty(String basketCode,String parentId,String childId,String jobOrderId,String operationId){
        disposable.add(apiInterface.checkBasketStatus(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode,parentId,childId,jobOrderId,operationId,0)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribeOn(Schedulers.io())
                .subscribe((response, throwable) -> {
                    checkBasketEmpty.postValue(response.getResponseStatus());
                    status.postValue(Status.SUCCESS);
                }));

    }
    public void getweldingsignoff(WeldingSignoffBody object){
        disposable.add(
                repository.Weldingsignoff(object)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribe((Weldingsignoffresponse, throwable) -> {
                            saveSignOffResponse.postValue(Weldingsignoffresponse);
                            status.postValue(Status.SUCCESS);
                        }));
    }

    public MutableLiveData<ResponseStatus> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Weldingsignoffcases> getWeldingignoffcases() {
        return weldingsignoffcases;
    }
    public MutableLiveData<Stationcodeloading> getdatadforstationcodecode() {
        return stationcodeloadingMutableLiveData;
    }

    public MutableLiveData<Apiinfoforstationcode<Stationcodeloading>> getGetStationData() {
        return getStationData;
    }

    public MutableLiveData<ApiWeldingsignoff<ResponseStatus>> getSaveSignOffResponse() {
        return saveSignOffResponse;
    }
    public MutableLiveData<ResponseStatus> getCheckBasketEmpty() {
        return checkBasketEmpty;
    }

    public void StationSignOff_Partial(WeldingSignoffBody_Partial weldingSignoffBody) {
        disposable.add(
                repository.StationSignOff_Partial(weldingSignoffBody)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribe((Weldingsignoffresponse, throwable) -> {
                            saveSignOffResponse.postValue(Weldingsignoffresponse);
                            status.postValue(Status.SUCCESS);
                        }));
    }
}
