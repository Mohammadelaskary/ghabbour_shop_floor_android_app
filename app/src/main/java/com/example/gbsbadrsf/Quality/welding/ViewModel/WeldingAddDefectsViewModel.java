package com.example.gbsbadrsf.Quality.welding.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingAddDefectsViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> defectsWeldingListLiveData;
    MutableLiveData<Status> defectsWeldingListStatus;
    MutableLiveData<ApiResponseAddWeldingDefectedChildToBasket> addWeldingDefectsToNewBasket;
    MutableLiveData<Status> addWeldingDefectsToNewBasketStatus;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    String newBasketCode;

//    @Inject
//    Gson gson;
//    @Inject
    public WeldingAddDefectsViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        defectsWeldingListLiveData = new MutableLiveData<>();
        defectsWeldingListStatus = new MutableLiveData<>();

        addWeldingDefectsToNewBasket = new MutableLiveData<>();
        addWeldingDefectsToNewBasketStatus = new MutableLiveData<>();
    }


    public void getWeldingDefects(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getWeldingDefectedQtyByBasketCode(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsWeldingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {defectsWeldingListLiveData.postValue(response);
                            defectsWeldingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsWeldingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addWeldingDefectsToNewBasketViewModel(int userId,
                                                            String deviceSerialNo,
                                                            int jobOrderId,
                                                            int parentId,
                                                            String basketCode,
                                                            String newBasketCode){

        disposable.add(apiInterface.addWeldingDefectedParentToBasket(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,jobOrderId,parentId,basketCode,newBasketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addWeldingDefectsToNewBasketStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addWeldingDefectsToNewBasket.postValue(response);
                            addWeldingDefectsToNewBasketStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addWeldingDefectsToNewBasketStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> getDefectsWeldingListLiveData() {
        return defectsWeldingListLiveData;
    }

    public MutableLiveData<Status> getDefectsWeldingListStatus() {
        return defectsWeldingListStatus;
    }

    public MutableLiveData<ApiResponseAddWeldingDefectedChildToBasket> getAddWeldingDefectsToNewBasket() {
        return addWeldingDefectsToNewBasket;
    }

    public MutableLiveData<Status> getAddWeldingDefectsToNewBasketStatus() {
        return addWeldingDefectsToNewBasketStatus;
    }

    public String getNewBasketCode() {
        return newBasketCode;
    }

    public void setNewBasketCode(String newBasketCode) {
        this.newBasketCode = newBasketCode;
    }
}
