package com.example.gbsbadrsf.Quality.Data;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RandomQualityInceptionViewModel extends AndroidViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    SingleLiveEvent<ApiResponseGetRandomQualityInception> infoForQualityRandomInspectionLiveData;
    SingleLiveEvent<Status> status;

    SingleLiveEvent<ApiResponseSaveRandomQualityInception> saveRandomQualityInceptionMutableLiveData;
    private SingleLiveEvent<ApiResponseDeleteManufacturingDefect_Online> deleteManufacturingDefectResponse;
    private String machineCode;
    //    @Inject
//    Gson gson;
//    @Inject
    public RandomQualityInceptionViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        infoForQualityRandomInspectionLiveData = new SingleLiveEvent<>();
        status = new SingleLiveEvent<>();
        saveRandomQualityInceptionMutableLiveData = new SingleLiveEvent<>();
        deleteManufacturingDefectResponse = new SingleLiveEvent<>();
    }

    public void getInfoForQualityRandomInspection(
            String Code
    ){
        disposable.add(apiInterface.GetInfoForQualityRandomInspection(
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
    public void DeleteManufacturingDefects(int userId,String deviceSerialNo,int DefectGroupId,int MainDefectsId){
        disposable.add(apiInterface.DeleteManufacturingDefect_Online(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userId,deviceSerialNo,DefectGroupId,MainDefectsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            deleteManufacturingDefectResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void SaveQualityRandomInspection(FullInspectionQuality_OnlineData data){
        disposable.add(apiInterface.FullInspectionQuality_Online(data)
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

    public SingleLiveEvent<ApiResponseGetRandomQualityInception> getInfoForQualityRandomInspectionLiveData() {
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

    public SingleLiveEvent<ApiResponseDeleteManufacturingDefect_Online> getDeleteManufacturingDefectResponse() {
        return deleteManufacturingDefectResponse;
    }
}
