package com.example.gbsbadrsf.Stoppages;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetStationDetails;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetStoppagesList;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.StoppagesApiInterface;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StoppagesListViewModel extends AndroidViewModel {

    private StoppagesApiInterface apiInterface;
    private SingleLiveEvent<ApiResponseGetStoppagesList> stoppagesList;
    private SingleLiveEvent<Status> stoppagesListStatus;
    public StoppagesListViewModel(@NonNull Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(StoppagesApiInterface.class);
        stoppagesList = new SingleLiveEvent<>();
        stoppagesListStatus = new SingleLiveEvent<>();
    }
    public void GetStoppagesList(){
        apiInterface.GetStoppagesList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetStoppagesList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        stoppagesListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetStoppagesList apiResponseGetNameOfStoppagesList) {
                        stoppagesListStatus.postValue(Status.SUCCESS);
                        stoppagesList.postValue(apiResponseGetNameOfStoppagesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        stoppagesListStatus.postValue(Status.ERROR);
                    }
                });

    }

    public SingleLiveEvent<ApiResponseGetStoppagesList> getStoppagesList() {
        return stoppagesList;
    }

    public SingleLiveEvent<Status> getStoppagesListStatus() {
        return stoppagesListStatus;
    }
}