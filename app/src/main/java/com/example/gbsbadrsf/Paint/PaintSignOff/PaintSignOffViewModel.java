package com.example.gbsbadrsf.Paint.PaintSignOff;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ApiResponseProductionSignOff_Painting;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintSignOffViewModel extends AndroidViewModel {
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseProductionSignOff_Painting> ProductionSignOff_Painting;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintSignOffViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        ProductionSignOff_Painting = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void ProductionSignOff_Painting(int userId,String deviceSerialNo,int loadingSequenceId){
        disposable.add(apiInterface.ProductionSignOff_Painting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,loadingSequenceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            ProductionSignOff_Painting.postValue(response);
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

    public MutableLiveData<ApiResponseProductionSignOff_Painting> getProductionSignOff_Painting() {
        return ProductionSignOff_Painting;
    }
}