package com.example.gbsbadrsf.Quality.paint.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseDeletePaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityOk_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityPass_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityOperationViewModel extends AndroidViewModel {
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> basketDataLiveData;
    MutableLiveData<Status> basketDataStatus;
//    MutableLiveData<ApiResponseDefectsPainting> defectsManufacturingListLiveData;
//    MutableLiveData<Status> defectsManufacturingListStatus;
//    MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectsToNewBasket;
//    MutableLiveData<Status> addManufacturingDefectsToNewBasketStatus;
//    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
//    MutableLiveData<Status> defectsListStatus;

//    MutableLiveData<ApiResponseAddingManufacturingDefect> addManufacturingDefectsResponse;
//    MutableLiveData<Status> addManufacturingDefectsStatus;
    MutableLiveData<ApiResponseQualityOk_Painting> qualityOkResponse;
    MutableLiveData<ApiResponseQualityPass_Painting> qualityPassResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseDeletePaintingDefect> deleteWeldingDefectResponse;
    LastMovePaintingBasket basketData;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintQualityOperationViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        basketDataLiveData = new MutableLiveData<>();
        basketDataStatus = new MutableLiveData<>();
        disposable = new CompositeDisposable();
//        defectsManufacturingListLiveData = new MutableLiveData<>();
//        defectsManufacturingListStatus = new MutableLiveData<>();
//
//        addManufacturingDefectsToNewBasket = new MutableLiveData<>();
//        addManufacturingDefectsToNewBasketStatus = new MutableLiveData<>();
//        defectsListLiveData = new MutableLiveData<>();
//        defectsListStatus = new MutableLiveData<>();
//        addManufacturingDefectsResponse = new MutableLiveData<>();
//        addManufacturingDefectsStatus = new MutableLiveData<>();

        qualityOkResponse = new MutableLiveData<>();
        qualityPassResponse = new MutableLiveData<>();
        deleteWeldingDefectResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerialNo,int loadingPaintingSignOutTransactionId){
        disposable.add(apiInterface.getPprInfoForQuality_Painting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,loadingPaintingSignOutTransactionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> basketDataStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            basketDataLiveData.postValue(response);
                            basketDataStatus.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            basketDataStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public void qualityOk(int userId,String deviceSerialNo,int PprLoadingId,String dt,int sampleQty){
        disposable.add(apiInterface.QualityOk_Painting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,PprLoadingId,dt,sampleQty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            qualityOkResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void qualityPass(int userId,String deviceSerialNo,int loadingPaintingSignOutTransactionId,String dt,int sampleQty){
        disposable.add(apiInterface.QualityPass_Painting(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,loadingPaintingSignOutTransactionId,dt
//                ,sampleQty
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            qualityPassResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void DeleteWeldingDefects(int userId,String deviceSerialNo,int mainDefectsId,int DefectGroupId){
        disposable.add(apiInterface.DeletePaintingDefect(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,mainDefectsId,DefectGroupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            deleteWeldingDefectResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

//    public MutableLiveData<ApiResponseDefectsManufacturing> getDefectsManufacturingListLiveData() {
//        return defectsManufacturingListLiveData;
//    }
//
//    public MutableLiveData<Status> getDefectsManufacturingListStatus() {
//        return defectsManufacturingListStatus;
//    }

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> getBasketDataResponse() {
        return basketDataLiveData;
    }

    public MutableLiveData<Status> getBasketDataStatus() {
        return basketDataStatus;
    }


    public LastMovePaintingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMovePaintingBasket basketData) {
        this.basketData = basketData;
    }

    public MutableLiveData<ApiResponseQualityOk_Painting> getQualityOkResponse() {
        return qualityOkResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseDeletePaintingDefect> getDeleteWeldingDefectResponse() {
        return deleteWeldingDefectResponse;
    }

    public MutableLiveData<ApiResponseQualityPass_Painting> getQualityPassResponse() {
        return qualityPassResponse;
    }
}
