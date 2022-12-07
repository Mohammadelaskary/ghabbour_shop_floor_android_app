package com.example.gbsbadrsf.Quality.Data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseGetBasketInfoLocateInQuality;
import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ManufacturingAddDefectsViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseGetBasketInfoLocateInQuality> defectsManufacturingListLiveData;
    MutableLiveData<Status> defectsManufacturingListStatus;
    MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectsToNewBasket;
    MutableLiveData<Status> addManufacturingDefectsToNewBasketStatus;
    String newBasketCode;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public ManufacturingAddDefectsViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        defectsManufacturingListLiveData = new MutableLiveData<>();
        defectsManufacturingListStatus = new MutableLiveData<>();

        addManufacturingDefectsToNewBasket = new MutableLiveData<>();
        addManufacturingDefectsToNewBasketStatus = new MutableLiveData<>();
    }


    public void getDefectsManufacturingViewModel(String basketCode){
        disposable.add(apiInterface.GetBasketInfoLocateInQuality(LocaleHelper.getLanguage(getApplication().getApplicationContext()),basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsManufacturingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {defectsManufacturingListLiveData.postValue(response);
                            defectsManufacturingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsManufacturingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addManufacturingDefectsToNewBasketViewModel(int jobOrderId,
                                                            int parentId,
                                                            int childId,
                                                            String basketCode,
                                                            String newBasketCode){

        disposable.add(apiInterface.addManufacturingDefectedChildToBasket(
                        LocaleHelper.getLanguage(getApplication().getApplicationContext()),
                jobOrderId,
                parentId,
                childId,
                basketCode,
                newBasketCode
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addManufacturingDefectsToNewBasketStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {addManufacturingDefectsToNewBasket.postValue(response);
                            addManufacturingDefectsToNewBasketStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addManufacturingDefectsToNewBasketStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetBasketInfoLocateInQuality> getDefectsManufacturingListLiveData() {
        return defectsManufacturingListLiveData;
    }

    public MutableLiveData<Status> getDefectsManufacturingListStatus() {
        return defectsManufacturingListStatus;
    }

    public MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> getAddManufacturingDefectsToNewBasket() {
        return addManufacturingDefectsToNewBasket;
    }

    public MutableLiveData<Status> getAddManufacturingDefectsToNewBasketStatus() {
        return addManufacturingDefectsToNewBasketStatus;
    }

    public String getNewBasketCode() {
        return newBasketCode;
    }

    public void setNewBasketCode(String newBasketCode) {
        this.newBasketCode = newBasketCode;
    }
}
