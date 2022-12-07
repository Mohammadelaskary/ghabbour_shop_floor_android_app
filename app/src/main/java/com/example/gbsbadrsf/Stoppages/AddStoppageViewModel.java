package com.example.gbsbadrsf.Stoppages;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.Stoppages.Model.AddStoppageData;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseAddStoppage;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetAvailableDatesForStoppage;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetDiesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetFactoriesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetJigsList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetMachineDetails;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetMachineFamilyList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetMachinesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetNameOfStoppagesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetStationDetails;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetStationsList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetWorkCenterLinesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetWorkCenterSubLinesList;
import com.example.gbsbadrsf.Stoppages.Model.ApiResponse.ApiResponseGetWorkCentersList;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.StoppagesApiInterface;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddStoppageViewModel extends AndroidViewModel {

    private CompositeDisposable disposable;
    private StoppagesApiInterface apiInterface;

    private SingleLiveEvent<Status> stoppagesNamesListStatus;
    private SingleLiveEvent<ApiResponseGetNameOfStoppagesList> stoppagesNamesList;

    private SingleLiveEvent<Status> workCentersListStatus;
    private SingleLiveEvent<ApiResponseGetWorkCentersList> workCentersList;

    private SingleLiveEvent<Status> workCenterLinesListStatus;
    private SingleLiveEvent<ApiResponseGetWorkCenterLinesList> workCenterLinesList;

    private SingleLiveEvent<Status> workCenterSubLinesListStatus;
    private SingleLiveEvent<ApiResponseGetWorkCenterSubLinesList> workCenterSubLinesList;

    private SingleLiveEvent<Status> machineFamilyListStatus;
    private SingleLiveEvent<ApiResponseGetMachineFamilyList> machineFamilyList;

    private SingleLiveEvent<Status> machineListStatus;
    private SingleLiveEvent<ApiResponseGetMachinesList> machineList;

    private SingleLiveEvent<Status> stationsListStatus;
    private SingleLiveEvent<ApiResponseGetStationsList> stationsList;

    private SingleLiveEvent<Status> factoriesListStatus;
    private SingleLiveEvent<ApiResponseGetFactoriesList> factoriesList;

    private SingleLiveEvent<Status> machineDetailsStatus;
    private SingleLiveEvent<ApiResponseGetMachineDetails> machineDetails;

    private SingleLiveEvent<Status> stationDetailsStatus;
    private SingleLiveEvent<ApiResponseGetStationDetails> stationDetails;

    private SingleLiveEvent<Status> availableDatesStatus;
    private SingleLiveEvent<ApiResponseGetAvailableDatesForStoppage> availableDates;

    private SingleLiveEvent<Status> addStoppageStatus;
    private SingleLiveEvent<ApiResponseAddStoppage> addStoppage;

    private SingleLiveEvent<Status> diesListStatus;
    private SingleLiveEvent<ApiResponseGetDiesList> diesList;

    private SingleLiveEvent<Status> jigsListStatus;
    private SingleLiveEvent<ApiResponseGetJigsList> jigsList;

    public AddStoppageViewModel(Application application) {
        super(application);
        disposable = new CompositeDisposable();
        apiInterface = ApiFactory.getClient().create(StoppagesApiInterface.class);
        stoppagesNamesList = new SingleLiveEvent<>();
        stoppagesNamesListStatus = new SingleLiveEvent<>();
        workCentersList = new SingleLiveEvent<>();
        workCentersListStatus = new SingleLiveEvent<>();
        workCenterLinesListStatus = new SingleLiveEvent<>();
        workCenterLinesList = new SingleLiveEvent<>();
        workCenterSubLinesListStatus = new SingleLiveEvent<>();
        workCenterSubLinesList = new SingleLiveEvent<>();
        machineFamilyListStatus = new SingleLiveEvent<>();
        machineFamilyList = new SingleLiveEvent<>();
        machineFamilyListStatus = new SingleLiveEvent<>();
        machineFamilyList = new SingleLiveEvent<>();
        machineListStatus = new SingleLiveEvent<>();
        machineList = new SingleLiveEvent<>();
        stationsListStatus = new SingleLiveEvent<>();
        stationsList = new SingleLiveEvent<>();
        factoriesListStatus = new SingleLiveEvent<>();
        factoriesList = new SingleLiveEvent<>();
        stationDetailsStatus = new SingleLiveEvent<>();
        stationDetails = new SingleLiveEvent<>();
        machineDetailsStatus = new SingleLiveEvent<>();
        machineDetails = new SingleLiveEvent<>();
        availableDatesStatus = new SingleLiveEvent<>();
        availableDates = new SingleLiveEvent<>();
        addStoppageStatus = new SingleLiveEvent<>();
        addStoppage = new SingleLiveEvent<>();
        diesList = new SingleLiveEvent<>();
        diesListStatus = new SingleLiveEvent<>();
        jigsListStatus = new SingleLiveEvent<>();
        jigsList= new SingleLiveEvent<>();
    }
    public void GetStoppagesNamesList(){
            apiInterface.GetNameOfStoppagesList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<ApiResponseGetNameOfStoppagesList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            stoppagesNamesListStatus.postValue(Status.LOADING);
                        }

                        @Override
                        public void onSuccess(ApiResponseGetNameOfStoppagesList apiResponseGetNameOfStoppagesList) {
                            stoppagesNamesListStatus.postValue(Status.SUCCESS);
                            stoppagesNamesList.postValue(apiResponseGetNameOfStoppagesList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            stoppagesNamesListStatus.postValue(Status.ERROR);
                        }
                    });

    }
    public void GetAvailableDatesForStoppage(){
        apiInterface.GetAvailableDatesForStoppage(USER_ID,DEVICE_SERIAL_NO)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetAvailableDatesForStoppage>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        availableDatesStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetAvailableDatesForStoppage apiResponseGetNameOfStoppagesList) {
                        availableDatesStatus.postValue(Status.SUCCESS);
                        availableDates.postValue(apiResponseGetNameOfStoppagesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        availableDatesStatus.postValue(Status.ERROR);
                    }
                });

    }
    public void GetFactoriesList(){
        apiInterface.GetFactoriesList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetFactoriesList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        factoriesListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetFactoriesList apiResponseGetNameOfStoppagesList) {
                        factoriesListStatus.postValue(Status.SUCCESS);
                        factoriesList.postValue(apiResponseGetNameOfStoppagesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        factoriesListStatus.postValue(Status.ERROR);
                    }
                });

    }
    public void GetWorkCentersList(int factoryId){
        apiInterface.GetWorkCentersList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,factoryId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetWorkCentersList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        workCentersListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetWorkCentersList response) {
                        workCentersListStatus.postValue(Status.SUCCESS);
                        workCentersList.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        workCentersListStatus.postValue(Status.ERROR);
                    }
                });

    }
    public void GetWorkCenterLinesList(int workCenterId){
        apiInterface.GetWorkCenterLinesList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,workCenterId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetWorkCenterLinesList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        workCenterLinesListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetWorkCenterLinesList response) {
                        workCenterLinesListStatus.postValue(Status.SUCCESS);
                        workCenterLinesList.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        workCenterLinesListStatus.postValue(Status.ERROR);
                    }
                });

    }
    public void GetWorkCenterSubLinesList(int lineId){
        apiInterface.GetWorkCenterSubLinesList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,lineId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetWorkCenterSubLinesList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        workCenterSubLinesListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetWorkCenterSubLinesList response) {
                        workCenterSubLinesListStatus.postValue(Status.SUCCESS);
                        workCenterSubLinesList.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        workCenterSubLinesListStatus.postValue(Status.ERROR);
                    }
                });

    }
    public void GetMachineFamilyList(int subLineId){
        apiInterface.GetMachineFamilyList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,subLineId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetMachineFamilyList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        machineFamilyListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetMachineFamilyList apiResponseGetNameOfStoppagesList) {
                        machineFamilyListStatus.postValue(Status.SUCCESS);
                        machineFamilyList.postValue(apiResponseGetNameOfStoppagesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        machineFamilyListStatus.postValue(Status.ERROR);
                    }
                });

    }
    public void GetMachineList(int machineFamilyId){
        apiInterface.GetMachinesList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,machineFamilyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetMachinesList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        machineListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetMachinesList response) {
                        machineListStatus.postValue(Status.SUCCESS);
                        machineList.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        machineListStatus.postValue(Status.ERROR);
                    }
                });

    }
    public void GetStationList(int machineFamilyId){
        apiInterface.GetStationsList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,machineFamilyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetStationsList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        stationsListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetStationsList response) {
                        stationsListStatus.postValue(Status.SUCCESS);
                        stationsList.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        stationsListStatus.postValue(Status.ERROR);
                    }
                });

    }

    public void GetDiesList(){
        apiInterface.GetDiesList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetDiesList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        diesListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetDiesList response) {
                        diesListStatus.postValue(Status.SUCCESS);
                        diesList.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        diesListStatus.postValue(Status.ERROR);
                    }
                });

    }

    public void GetJigsList(){
        apiInterface.GetJigsList(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetJigsList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        jigsListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetJigsList response) {
                        jigsListStatus.postValue(Status.SUCCESS);
                        jigsList.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        jigsListStatus.postValue(Status.ERROR);
                    }
                });

    }

    public void GetMachineDetails(String machineCode){
        apiInterface.GetMachineDetails(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,machineCode)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetMachineDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        machineDetailsStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetMachineDetails apiResponseGetNameOfStoppagesList) {
                        machineDetailsStatus.postValue(Status.SUCCESS);
                        machineDetails.postValue(apiResponseGetNameOfStoppagesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        machineDetailsStatus.postValue(Status.ERROR);
                    }
                });

    }

    public void GetStationDetails(String stationCode){
        apiInterface.GetStationDetails(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,stationCode)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseGetStationDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        stationDetailsStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseGetStationDetails apiResponseGetNameOfStoppagesList) {
                        stationDetailsStatus.postValue(Status.SUCCESS);
                        stationDetails.postValue(apiResponseGetNameOfStoppagesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        stationDetailsStatus.postValue(Status.ERROR);
                    }
                });

    }



    public SingleLiveEvent<Status> getStoppagesNamesListStatus() {
        return stoppagesNamesListStatus;
    }

    public SingleLiveEvent<ApiResponseGetNameOfStoppagesList> getStoppagesNamesList() {
        return stoppagesNamesList;
    }

    public SingleLiveEvent<Status> getWorkCentersListStatus() {
        return workCentersListStatus;
    }

    public SingleLiveEvent<ApiResponseGetWorkCentersList> getWorkCentersList() {
        return workCentersList;
    }

    public SingleLiveEvent<Status> getWorkCenterLinesListStatus() {
        return workCenterLinesListStatus;
    }

    public SingleLiveEvent<ApiResponseGetWorkCenterLinesList> getWorkCenterLinesList() {
        return workCenterLinesList;
    }

    public SingleLiveEvent<Status> getWorkCenterSubLinesListStatus() {
        return workCenterSubLinesListStatus;
    }

    public SingleLiveEvent<ApiResponseGetWorkCenterSubLinesList> getWorkCenterSubLinesList() {
        return workCenterSubLinesList;
    }

    public SingleLiveEvent<Status> getMachineFamilyListStatus() {
        return machineFamilyListStatus;
    }

    public SingleLiveEvent<ApiResponseGetMachineFamilyList> getMachineFamilyList() {
        return machineFamilyList;
    }

    public SingleLiveEvent<Status> getMachineListStatus() {
        return machineListStatus;
    }

    public SingleLiveEvent<ApiResponseGetMachinesList> getMachineList() {
        return machineList;
    }

    public SingleLiveEvent<Status> getStationsListStatus() {
        return stationsListStatus;
    }

    public SingleLiveEvent<ApiResponseGetStationsList> getStationsList() {
        return stationsList;
    }

    public SingleLiveEvent<Status> getFactoriesListStatus() {
        return factoriesListStatus;
    }

    public SingleLiveEvent<ApiResponseGetFactoriesList> getFactoriesList() {
        return factoriesList;
    }

    public SingleLiveEvent<Status> getMachineDetailsStatus() {
        return machineDetailsStatus;
    }

    public SingleLiveEvent<ApiResponseGetMachineDetails> getMachineDetails() {
        return machineDetails;
    }

    public SingleLiveEvent<Status> getStationDetailsStatus() {
        return stationDetailsStatus;
    }

    public SingleLiveEvent<ApiResponseGetStationDetails> getStationDetails() {
        return stationDetails;
    }

    public SingleLiveEvent<Status> getAvailableDatesStatus() {
        return availableDatesStatus;
    }

    public SingleLiveEvent<ApiResponseGetAvailableDatesForStoppage> getAvailableDates() {
        return availableDates;
    }

    public void AddStoppage(AddStoppageData data) {
        apiInterface.AddStoppage(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseAddStoppage>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addStoppageStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseAddStoppage apiResponseGetNameOfStoppagesList) {
                        addStoppageStatus.postValue(Status.SUCCESS);
                        addStoppage.postValue(apiResponseGetNameOfStoppagesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        addStoppageStatus.postValue(Status.ERROR);
                    }
                });
    }

    public SingleLiveEvent<Status> getAddStoppageStatus() {
        return addStoppageStatus;
    }

    public SingleLiveEvent<ApiResponseAddStoppage> getAddStoppage() {
        return addStoppage;
    }

    public SingleLiveEvent<Status> getDiesListStatus() {
        return diesListStatus;
    }

    public SingleLiveEvent<ApiResponseGetDiesList> getDiesList() {
        return diesList;
    }

    public SingleLiveEvent<Status> getJigsListStatus() {
        return jigsListStatus;
    }

    public SingleLiveEvent<ApiResponseGetJigsList> getJigsList() {
        return jigsList;
    }
}