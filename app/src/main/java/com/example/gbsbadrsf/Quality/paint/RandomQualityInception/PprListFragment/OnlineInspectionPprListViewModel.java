package com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OnlineInspectionPprListViewModel extends AndroidViewModel {
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseGetStationInfoForQuality_Painting> paintStationWIP;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private String stationCode;


//    @Inject
//    Gson gson;
//    @Inject
    public OnlineInspectionPprListViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        paintStationWIP = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void GetStationInfoForQuality_Painting(String stationCode){
        disposable.add(apiInterface.GetStationInfoForQuality_Painting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,stationCode)
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

    public MutableLiveData<ApiResponseGetStationInfoForQuality_Painting> getPaintStationWIP() {
        return paintStationWIP;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
}
