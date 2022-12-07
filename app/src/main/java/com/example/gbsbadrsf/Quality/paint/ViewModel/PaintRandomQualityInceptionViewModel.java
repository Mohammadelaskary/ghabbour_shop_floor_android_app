package com.example.gbsbadrsf.Quality.paint.ViewModel;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDeleteManufacturingDefect_Online;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveRandomQualityInception;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.ApiResponseDeletePaintingDefect_Online;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.ApiResponseGetStationPPRInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.FullInspectionQuality_Online_Painting_Data;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintRandomQualityInceptionViewModel extends AndroidViewModel {
//    @Inject
ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    SingleLiveEvent<ApiResponseGetStationPPRInfoForQuality_Painting> infoForQualityRandomInspectionLiveData;
    SingleLiveEvent<Status> status;

    SingleLiveEvent<ApiResponseSaveRandomQualityInception> saveRandomQualityInceptionMutableLiveData;
    private SingleLiveEvent<ApiResponseDeletePaintingDefect_Online> deletePaintingDefectResponse;
    private String machineCode;
    //    @Inject
//    Gson gson;
//    @Inject
    public PaintRandomQualityInceptionViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        infoForQualityRandomInspectionLiveData = new SingleLiveEvent<>();
        status = new SingleLiveEvent<>();
        saveRandomQualityInceptionMutableLiveData = new SingleLiveEvent<>();
        deletePaintingDefectResponse = new SingleLiveEvent<>();
    }

    public void getInfoForQualityRandomInspection(
            int pprId,
            String stationCode
    ){
        disposable.add(apiInterface.GetStationPPRInfoForQuality_Painting(
                        LocaleHelper.getLanguage(getApplication().getApplicationContext()),
                        USER_ID,
                        DEVICE_SERIAL_NO,
                        pprId,
                        stationCode
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            infoForQualityRandomInspectionLiveData.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void DeletePaintingDefects(int userId, String deviceSerialNo, int DefectGroupId, int mainDefectId){
        disposable.add(apiInterface.DeletePaintingDefect_Online(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,DefectGroupId,mainDefectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            deletePaintingDefectResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void SaveQualityRandomInspection(FullInspectionQuality_Online_Painting_Data data){
        disposable.add(apiInterface.FullInspectionQuality_Painting_Online(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            saveRandomQualityInceptionMutableLiveData.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public SingleLiveEvent<ApiResponseGetStationPPRInfoForQuality_Painting> getInfoForQualityRandomInspectionLiveData() {
        return infoForQualityRandomInspectionLiveData;
    }

    public SingleLiveEvent<Status> getStatus() {
        return status;
    }

    public SingleLiveEvent<ApiResponseSaveRandomQualityInception> getSaveRandomQualityInceptionMutableLiveData() {
        return saveRandomQualityInceptionMutableLiveData;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public SingleLiveEvent<ApiResponseDeletePaintingDefect_Online> getDeletePaintingDefectResponse() {
        return deletePaintingDefectResponse;
    }
}
