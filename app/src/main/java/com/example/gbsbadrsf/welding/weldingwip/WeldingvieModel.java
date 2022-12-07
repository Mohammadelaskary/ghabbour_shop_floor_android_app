package com.example.gbsbadrsf.welding.weldingwip;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ApiResponseStationwip;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingvieModel extends AndroidViewModel {
    MutableLiveData<ApiResponseStationwip<List<StationsWIP>>> weldingwipResponse;
    MutableLiveData<Status> status;

//    @Inject
    ApiInterface apiInterface;
//    @Inject
//    Gson gson;
    private CompositeDisposable disposable;
//    @Inject
    public WeldingvieModel(Application application) {
        super(application);
        weldingwipResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);

    }

    void getweldingwip(int UserID,String DeviceSerialNo){
        disposable.add(apiInterface.getstationwip(LocaleHelper.getLanguage(getApplication().getApplicationContext()),UserID,DeviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {weldingwipResponse.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<ApiResponseStationwip<List<StationsWIP>>> getweldingsequenceResponse() {
        return weldingwipResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}


