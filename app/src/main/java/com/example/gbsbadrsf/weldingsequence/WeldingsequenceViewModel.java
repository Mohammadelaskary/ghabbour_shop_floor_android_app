package com.example.gbsbadrsf.weldingsequence;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ApiResponseweldingbyjoborder;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingsequenceViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseweldingbyjoborder<List<PprWelding>>> weldingsequenceResponse;
    MutableLiveData<Status> status;

//    @Inject
    ApiInterface apiInterface;
//    @Inject
//    Gson gson;
    private CompositeDisposable disposable;
//    @Inject
    public WeldingsequenceViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        weldingsequenceResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getWeldingsequence(int Userid,String Deviceserialnumber){
        disposable.add(apiInterface.getweldingsequence(LocaleHelper.getLanguage(getApplication().getApplicationContext()),Userid,Deviceserialnumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {weldingsequenceResponse.postValue(cuisines);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<ApiResponseweldingbyjoborder<List<PprWelding>>> getWeldingsequenceResponse() {
        return weldingsequenceResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}
