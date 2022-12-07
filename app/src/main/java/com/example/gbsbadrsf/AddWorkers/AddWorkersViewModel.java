package com.example.gbsbadrsf.AddWorkers;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddWorkersViewModel extends AndroidViewModel {
    private ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private SingleLiveEvent<ApiResponseGetWorkersList> getWorkersList;
    private SingleLiveEvent<Status> status;
    private SingleLiveEvent<ApiResponseGetMachineWorkers> getMachineWorkers;
    private SingleLiveEvent<ApiResponseAddWorkers_Machine> addWorkersResponse;

    public AddWorkersViewModel(@NonNull Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        getWorkersList = new SingleLiveEvent<>();
        status = new SingleLiveEvent<>();
        getMachineWorkers = new SingleLiveEvent<>();
        addWorkersResponse = new SingleLiveEvent<>();
    }

    public void getWorkers(){
                apiInterface.GetWorkersList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponseGetWorkersList>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                status.postValue(Status.LOADING);
                            }

                            @Override
                            public void onNext(ApiResponseGetWorkersList apiResponseGetWorkersList) {
                                getWorkersList.postValue(apiResponseGetWorkersList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                status.postValue(Status.ERROR);
                            }

                            @Override
                            public void onComplete() {
                                status.postValue(Status.SUCCESS);
                            }
                        });

    }
    public void getMachineWorkers(String machineCode){
        apiInterface.GetMachineWorkers(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,machineCode)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ApiResponseGetMachineWorkers>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        status.postValue(Status.LOADING);
                    }

                    @Override
                    public void onNext(ApiResponseGetMachineWorkers apiResponseGetMachineWorkers) {
                        getMachineWorkers.postValue(apiResponseGetMachineWorkers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                    }

                    @Override
                    public void onComplete() {
                        status.postValue(Status.SUCCESS);
                    }
                });
    }
    public void addMachineWorkers(AddWorkers_MachineData data){
        apiInterface.AddWorkers_Machine(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponseAddWorkers_Machine>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        status.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseAddWorkers_Machine apiResponse) {
                        status.postValue(Status.SUCCESS);
                        addWorkersResponse.postValue(apiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                    }
                });

    }



    public SingleLiveEvent<ApiResponseGetWorkersList> getGetWorkersList() {
        return getWorkersList;
    }

    public SingleLiveEvent<Status> getStatus() {
        return status;
    }

    public SingleLiveEvent<ApiResponseGetMachineWorkers> getGetMachineWorkers() {
        return getMachineWorkers;
    }

    public SingleLiveEvent<ApiResponseAddWorkers_Machine> getAddWorkersResponse() {
        return addWorkersResponse;
    }
}