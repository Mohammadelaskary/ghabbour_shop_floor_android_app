package com.example.gbsbadrsf.Manfacturing.machineloading;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Apigetbasketcode;
import com.example.gbsbadrsf.data.response.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import io.reactivex.disposables.CompositeDisposable;

public class ContinueLoadingViewModel extends AndroidViewModel {
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Apigetbasketcode<LastMoveManufacturingBasketInfo>>lastmanfacturingbasketinfo;
    private MutableLiveData<Basketcases>basketcases;




    private MutableLiveData<Status> status;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();
//    @Inject
    public ContinueLoadingViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        lastmanfacturingbasketinfo=new MutableLiveData<>();
        basketcases=new MutableLiveData<>();

        status = new MutableLiveData<>();
    }
    void getbasketedata(String basketcode){
        disposable.add(apiInterface.getbasketcodedata(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketcode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(getbasketcode -> {
                lastmanfacturingbasketinfo.postValue(getbasketcode);
            status.postValue(Status.SUCCESS);
        },throwable -> {
            status.postValue(Status.ERROR);
        }));

    }
    void savecontinueloading(ContinueLoadingData data){
        disposable.add(apiInterface.savecontinueloading(data)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(responseStatusApiContinuetloading -> {
//            if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Saving data successfully"))
//            {
//                basketcases.postValue(Basketcases.Savingdatasuccessfully);
//
//
//            }
//            else if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("The machine has already been used")){
//                basketcases.postValue(Basketcases.machinealreadyused);
//
//
//
//            }
//            else if(responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Wrong machine code")){
//                basketcases.postValue(Basketcases.wrongmachinecode);
//
//
//            }
//            else if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Wrong die code for this child"))
//            {
//                basketcases.postValue(Basketcases.wrongdie);
//
//
//            }
//
//            else if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Wrong basket code"))
//            {
//                basketcases.postValue(Basketcases.wrongbasket);
//
//
//            }
                    responseLiveData.postValue(responseStatusApiContinuetloading.getResponseStatus());
            status.postValue(Status.SUCCESS);
        },throwable -> status.postValue(Status.ERROR)));

    }

    public MutableLiveData<ResponseStatus> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Basketcases> getBasketcases() {
        return basketcases;
    }

    public MutableLiveData<Apigetbasketcode<LastMoveManufacturingBasketInfo>> getLastmanfacturingbasketinfo() {
        return lastmanfacturingbasketinfo;
    }
}
