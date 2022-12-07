package com.example.gbsbadrsf.Quality.paint.PprListQualityOperation;

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

public class PaintSignOffPprListViewModel extends AndroidViewModel {
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseGetPaintQualityPprList> paintStationWIP;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintSignOffPprListViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        paintStationWIP = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void GetPaintQualityPprList(int userId,String deviceSerialNo){
        disposable.add(apiInterface.GetPaintQualityPprList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            paintStationWIP.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseGetPaintQualityPprList> getPaintStationWIP() {
        return paintStationWIP;
    }
}
