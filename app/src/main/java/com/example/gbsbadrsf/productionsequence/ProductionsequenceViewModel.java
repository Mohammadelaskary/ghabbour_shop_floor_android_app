package com.example.gbsbadrsf.productionsequence;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.APIResponse;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProductionsequenceViewModel extends AndroidViewModel {
    private static final String TAG = "ProductionsequenceViewModel";
    MutableLiveData<APIResponse<List<Ppr>>> productionsequenceResponse;
    MutableLiveData<Status> status;

//    @Inject
    ApiInterface apiInterface;
//    @Inject
//    Gson gson;
    private CompositeDisposable disposable;
    @Inject
    public ProductionsequenceViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        productionsequenceResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

      void getProductionsequence(String Joborddername){
        disposable.add(apiInterface.getproductionsequence(LocaleHelper.getLanguage(getApplication().getApplicationContext()),Joborddername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {productionsequenceResponse.postValue(cuisines);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }





    public MutableLiveData<APIResponse<List<Ppr>>> getProductionsequenceResponse() {
        return productionsequenceResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }


}
