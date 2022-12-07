package com.example.gbsbadrsf.Quality.paint.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintAddDefectsViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> defectsPaintingListLiveData;
    MutableLiveData<Status> defectsPaintingListStatus;
    MutableLiveData<ApiResponseAddPaintingDefectedChildToBasket> addPaintingDefectsToNewBasket;
    MutableLiveData<Status> addPaintingDefectsToNewBasketStatus;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;

    String newBasketCode;

//    @Inject
//    Gson gson;
//    @Inject
    public PaintAddDefectsViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        defectsPaintingListLiveData = new MutableLiveData<>();
        defectsPaintingListStatus = new MutableLiveData<>();

        addPaintingDefectsToNewBasket = new MutableLiveData<>();
        addPaintingDefectsToNewBasketStatus = new MutableLiveData<>();
    }



    public void getPaintingDefects(int userId, String deviceSerialNo, String basketCode){
        disposable.add(apiInterface.getPaintingDefectedQtyByBasketCode(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsPaintingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            defectsPaintingListLiveData.postValue(response);
                            defectsPaintingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsPaintingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addPaintingDefectsToNewBasketViewModel(int userId,
                                                       String deviceSerialNo,
                                                       int jobOrderId,
                                                       int parentId,
                                                       String basketCode,
                                                       String newBasketCode){

        disposable.add(apiInterface.addPaintingDefectedParentToBasket(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,jobOrderId,parentId,basketCode,newBasketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addPaintingDefectsToNewBasketStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addPaintingDefectsToNewBasket.postValue(response);
                            addPaintingDefectsToNewBasketStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addPaintingDefectsToNewBasketStatus.postValue(Status.ERROR);
                        }
                ));
    }


    public MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> getDefectsPaintingListLiveData() {
        return defectsPaintingListLiveData;
    }

    public MutableLiveData<Status> getDefectsPaintingListStatus() {
        return defectsPaintingListStatus;
    }

    public MutableLiveData<ApiResponseAddPaintingDefectedChildToBasket> getAddPaintingDefectsToNewBasket() {
        return addPaintingDefectsToNewBasket;
    }

    public MutableLiveData<Status> getAddPaintingDefectsToNewBasketStatus() {
        return addPaintingDefectsToNewBasketStatus;
    }

    public String getNewBasketCode() {
        return newBasketCode;
    }

    public void setNewBasketCode(String newBasketCode) {
        this.newBasketCode = newBasketCode;
    }
}
