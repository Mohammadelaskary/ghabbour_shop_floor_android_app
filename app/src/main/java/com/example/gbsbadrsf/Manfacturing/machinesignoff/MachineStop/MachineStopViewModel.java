package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MachineStopViewModel extends AndroidViewModel {
    private ApiInterface apiInterface;
    private MutableLiveData<Status> status;
    private MutableLiveData<ApiResponseGetStopageReasonsList> stoppageReasons;
    private MutableLiveData<ApiResponseGetMachineData> machineData;
    private MutableLiveData<ApiResponseTransferMachineLoading> machineTransfer;
    private CompositeDisposable disposable;

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MachineStopViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        status = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        stoppageReasons = new MutableLiveData<>();
        machineData = new MutableLiveData<>();
        machineTransfer = new MutableLiveData<>();
    }

    public void getMachineData(String machineCode) {
        disposable.add(apiInterface.GetMachineData(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,machineCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            machineData.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public void transferMachine(String oldMachineCode,String machineCode, int reasonId) {
        disposable.add(apiInterface.TransferMachineLoading(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,oldMachineCode,machineCode,reasonId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            machineTransfer.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public void getStopReasons() {
        disposable.add(apiInterface.GetStoppagesReasonsList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            stoppageReasons.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public void machineHold(String machineCode, String okBasketCode, int reasonId, int okQty) {

    }

    public MutableLiveData<ApiResponseGetStopageReasonsList> getStoppageReasons() {
        return stoppageReasons;
    }

    public MutableLiveData<ApiResponseGetMachineData> getMachineData() {
        return machineData;
    }

    public MutableLiveData<ApiResponseTransferMachineLoading> getMachineTransfer() {
        return machineTransfer;
    }
}