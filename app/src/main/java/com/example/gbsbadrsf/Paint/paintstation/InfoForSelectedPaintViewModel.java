package com.example.gbsbadrsf.Paint.paintstation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ApiGetPaintingLoadingSequenceStartLoading;
import com.example.gbsbadrsf.data.response.Pprpaintcontainbaskets;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.weldingsequence.Staustype;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class InfoForSelectedPaintViewModel extends AndroidViewModel {
//    Gson gson;
//    @Inject
    ApiInterface apiinterface;
    private MutableLiveData<ApiGetPaintingLoadingSequenceStartLoading<Pprpaintcontainbaskets>> responseLiveData ;
    private MutableLiveData<List<Basket>> baskets;

    private MutableLiveData<Status> status;

    private MutableLiveData<Staustype> statustype;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public InfoForSelectedPaintViewModel(Application application) {
        super(application);
        apiinterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        baskets= new MutableLiveData<>();
        statustype = new MutableLiveData<>();


    }
    void getselectedpaintsequence(int UserID,String DeviceSerialNo,String loadingsequenceid){
        disposable.add(apiinterface.getpaintloadingsequence(LocaleHelper.getLanguage(getApplication().getApplicationContext()),UserID,DeviceSerialNo,loadingsequenceid)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((getinfoforselectedstationloading, throwable) -> {
//                    if (getinfoforselectedstationloading.getResponseStatus().getStatusMessage().equals("Data sent successfully")&&getinfoforselectedstationloading.getBaskets().getBasketCode()!=null)
//                    {
//                        statustype.postValue(Staustype.gettingdatasuccesfully);
//                        //baskets.postValue(getBaskets().getValue());
//
//
//
//
//
//                    }
//                    else if (getinfoforselectedstationloading.getResponseStatus().getStatusMessage().equals("Wrong Loading sequence ID!")){
//                        statustype.postValue(Staustype.noloadingquantityformachine);
//
//                    }
                    baskets.postValue(getinfoforselectedstationloading.getBaskets());
                    responseLiveData.postValue(getinfoforselectedstationloading);
                    status.postValue(Status.SUCCESS);
                }));
    }
    public MutableLiveData<ApiGetPaintingLoadingSequenceStartLoading<Pprpaintcontainbaskets>> getResponseLiveData() {
        return responseLiveData;
    }


    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Staustype> getstaustype() {
        return statustype;
    }
    public MutableLiveData<List<Basket>> getBaskets() {
        return baskets;
    }


}



