package com.example.gbsbadrsf.Paint.paintwip;

import android.app.Application;
import android.util.Log;

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

public class PaintViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseStationwip<List<StationsWIP>>> paintwipResponse;
    MutableLiveData<Status> status;

//    @Inject
    ApiInterface apiInterface;
//    @Inject
//    Gson gson;
    private CompositeDisposable disposable;
//    @Inject
    public PaintViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        paintwipResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getweldingpaint(int UserID,String DeviceSerialNo){
        disposable.add(apiInterface.getpaintwip(LocaleHelper.getLanguage(getApplication().getApplicationContext()),UserID,DeviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {paintwipResponse.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                            Log.d("PaintWip",throwable.getMessage());
                        }
                ));

    }




    public MutableLiveData<ApiResponseStationwip<List<StationsWIP>>> getpaintsequenceResponse() {
        return paintwipResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}



