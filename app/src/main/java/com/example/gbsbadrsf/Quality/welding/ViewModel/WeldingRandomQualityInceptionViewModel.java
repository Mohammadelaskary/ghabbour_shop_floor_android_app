package com.example.gbsbadrsf.Quality.welding.ViewModel;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDeleteManufacturingDefect_Online;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.FullInspectionQuality_OnlineData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseDeleteWeldingDefect_Online;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetInfoForQualityRandomInpection_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetRandomQualityInception_Welding;
import com.example.gbsbadrsf.Quality.welding.RandomQualityInception.FullInspectionQuality_Online_Welding_Data;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingRandomQualityInceptionViewModel extends AndroidViewModel {
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    SingleLiveEvent<ApiResponseGetInfoForQualityRandomInpection_Welding> infoForQualityRandomInspectionLiveData;
    SingleLiveEvent<Status> status;

    SingleLiveEvent<ApiResponseSaveRandomQualityInception> saveRandomQualityInceptionMutableLiveData;
    private SingleLiveEvent<ApiResponseDeleteWeldingDefect_Online> deleteWeldingDefectResponse;
    private String stationCode;
    //    @Inject
//    Gson gson;
//    @Inject
    public WeldingRandomQualityInceptionViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        infoForQualityRandomInspectionLiveData = new SingleLiveEvent<>();
        status = new SingleLiveEvent<>();
        saveRandomQualityInceptionMutableLiveData = new SingleLiveEvent<>();
        deleteWeldingDefectResponse = new SingleLiveEvent<>();
    }

    public void getInfoForQualityRandomInspection(
            String Code
    ){
        disposable.add(apiInterface.GetInfoForQualityRandomInpection_Welding(
                        LocaleHelper.getLanguage(getApplication().getApplicationContext()),
                        USER_ID,
                        DEVICE_SERIAL_NO,
                        Code
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
    public void DeleteWeldingDefects(int userId, String deviceSerialNo, int DefectGroupId,int mainDefectId){
        disposable.add(apiInterface.DeleteWeldingDefect_Online(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,DefectGroupId,mainDefectId)
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
    public void SaveQualityRandomInspection(FullInspectionQuality_Online_Welding_Data data){
        disposable.add(apiInterface.FullInspectionQuality_Welding_Online(data)
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

    public SingleLiveEvent<ApiResponseGetInfoForQualityRandomInpection_Welding> getInfoForQualityRandomInspectionLiveData() {
        return infoForQualityRandomInspectionLiveData;
    }

    public SingleLiveEvent<Status> getStatus() {
        return status;
    }

    public SingleLiveEvent<ApiResponseSaveRandomQualityInception> getSaveRandomQualityInceptionMutableLiveData() {
        return saveRandomQualityInceptionMutableLiveData;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public SingleLiveEvent<ApiResponseDeleteWeldingDefect_Online> getDeleteWeldingDefectResponse() {
        return deleteWeldingDefectResponse;
    }
}
