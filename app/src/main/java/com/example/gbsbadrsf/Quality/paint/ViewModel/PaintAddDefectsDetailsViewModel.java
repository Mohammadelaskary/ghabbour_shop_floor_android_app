package com.example.gbsbadrsf.Quality.paint.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseAddingPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseUpdatePaintingDefects;
import com.example.gbsbadrsf.Quality.paint.Model.UpdatePaintingDefectsData;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintAddDefectsDetailsViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddingPaintingDefect> addPaintingDefectsResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseUpdatePaintingDefects> updatePaintingDefectsResponse;

//    @Inject
//    Gson gson;
//    @Inject
    public PaintAddDefectsDetailsViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addPaintingDefectsResponse = new MutableLiveData<>();
        updatePaintingDefectsResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getDefectsListViewModel(int operationId){
        disposable.add(apiInterface.getDefectsListPerOperation(LocaleHelper.getLanguage(getApplication().getApplicationContext()),operationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->defectsListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            defectsListLiveData.postValue(response);
                            defectsListStatus.postValue(Status.SUCCESS);},
                        throwable -> {
                            defectsListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addPaintingDefectResponseViewModel(AddPaintingDefectData addPaintingDefectData){
        disposable.add(apiInterface.AddPaintingDefect(addPaintingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsWelding -> {
                            status.postValue(Status.SUCCESS);
                            addPaintingDefectsResponse.postValue(apiResponseAddingDefectsWelding);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public void updateWeldingDefectResponseViewModel(UpdatePaintingDefectsData data){
        disposable.add(apiInterface.UpdatePaintingDefect(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseUpdatingDefectsManufacturing -> {
                            status.postValue(Status.SUCCESS);
                            updatePaintingDefectsResponse.postValue(apiResponseUpdatingDefectsManufacturing);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddingPaintingDefect> getAddPaintingDefectsResponse() {
        return addPaintingDefectsResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseUpdatePaintingDefects> getUpdatePaintingDefectsResponse() {
        return updatePaintingDefectsResponse;
    }
}

