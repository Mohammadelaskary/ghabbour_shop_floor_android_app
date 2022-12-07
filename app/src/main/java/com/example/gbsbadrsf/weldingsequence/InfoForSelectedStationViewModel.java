package com.example.gbsbadrsf.weldingsequence;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ApiGetweldingloadingstartloading;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.disposables.CompositeDisposable;

public class InfoForSelectedStationViewModel extends AndroidViewModel {
//    Gson gson;
//    @Inject
    ApiInterface apiInterface;
    private MutableLiveData<ApiGetweldingloadingstartloading<PprWelding>> responseLiveData ;
    private MutableLiveData<Baskets> baskets;

    private MutableLiveData<Status> status;

    private MutableLiveData<Staustype> statustype;

    private CompositeDisposable disposable = new CompositeDisposable();

//    @Inject
    public InfoForSelectedStationViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        baskets= new MutableLiveData<>();
        statustype = new MutableLiveData<>();
    }
    void getselectedweldingsequence(int UserID,String DeviceSerialNo,String loadingsequenceid){
        disposable.add(apiInterface.getweldingloadingsequence(LocaleHelper.getLanguage(getApplication().getApplicationContext()),UserID,DeviceSerialNo,loadingsequenceid)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((getinfoforselectedstationloading, throwable) -> {
//            if (getinfoforselectedstationloading.getResponseStatus().getStatusMessage().equals("Data sent successfully")&&getinfoforselectedstationloading.getBaskets().getBasketCode()!=null)
//            {
//                statustype.postValue(Staustype.gettingdatasuccesfully);
//               //baskets.postValue(getBaskets().getValue());
//
//
//
//
//
//            }
//            else if (getinfoforselectedstationloading.getResponseStatus().getStatusMessage().equals("Wrong Loading sequence ID!")){
//                statustype.postValue(Staustype.noloadingquantityformachine);
//
//            }
                    responseLiveData.postValue(getinfoforselectedstationloading);
            status.postValue(Status.SUCCESS);
        }));



    }
    public MutableLiveData<ApiGetweldingloadingstartloading<PprWelding>> getResponseLiveData() {
        return responseLiveData;
    }


    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Staustype> getstaustype() {
        return statustype;
    }
    public MutableLiveData<Baskets> getBaskets() {
        return baskets;
    }


}
