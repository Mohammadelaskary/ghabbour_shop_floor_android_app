package com.example.gbsbadrsf.Paint.paintstation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintstationViewModel extends AndroidViewModel {

    MutableLiveData<List<Pprpaint>> paintsequenceResponse;
    MutableLiveData<Status> status;

//    @Inject
    ApiInterface apiInterface;
//    @Inject
//    Gson gson;
    private CompositeDisposable disposable;
//    @Inject
    public PaintstationViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        paintsequenceResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getpaintsequence(int Userid,String Deviceserialnumber){
        disposable.add(apiInterface.getpaintsequence(LocaleHelper.getLanguage(getApplication().getApplicationContext()),Userid,Deviceserialnumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {paintsequenceResponse.postValue(cuisines.getPprList());
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<List<Pprpaint>> getPaintsequenceResponse() {
        return paintsequenceResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

}
