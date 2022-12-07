package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.Apigetmachinecode;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.google.gson.Gson;

import io.reactivex.disposables.CompositeDisposable;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

public class MachinesignoffViewModel extends AndroidViewModel {
    Gson gson;
    Productionsequencerepository repository;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Apigetmachinecode<MachineLoading>>apiResponseMachineLoadingData;

    private MutableLiveData<Machinsignoffcases>machinesignoffcases;
    private MutableLiveData<ResponseStatus> checkBasketEmpty ;

    private MutableLiveData<Status> status;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();
//    @Inject
    public MachinesignoffViewModel(Application application) {
        super(application);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        apiResponseMachineLoadingData=new MutableLiveData<>();
        machinesignoffcases=new MutableLiveData<>();

        status = new MutableLiveData<>();
        checkBasketEmpty = new MutableLiveData<>();
        this.repository=new Productionsequencerepository(application.getApplicationContext());
    }
    public void getmachinesignoff(MachineSignoffBody object){

        disposable.add(
                repository.Machinesignoff(object)
                        .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribe((Machinesignoffresponse, throwable) -> {
                            responseLiveData.postValue(Machinesignoffresponse.getResponseStatus());
                            status.postValue(Status.SUCCESS);
                        }));

    }
    public void MachineSignOff_Partial(MachineSignoffBody object){

        disposable.add(
                repository.Machinesignoff_Partial(object)
                        .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribe((Machinesignoffresponse, throwable) -> {
                            responseLiveData.postValue(Machinesignoffresponse.getResponseStatus());
                            status.postValue(Status.SUCCESS);
                        }));

    }
    void getmachinecodedata(int userid,String deviceserialnum,String machinecode){
        disposable.add(apiInterface.getmachinecodedata(LocaleHelper.getLanguage(getApplication().getApplicationContext()),userid,deviceserialnum,machinecode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((getmachinecode, throwable) -> {
                    apiResponseMachineLoadingData.postValue(getmachinecode);
//                    if (getmachinecode.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
//                    {
//                       machinesignoffcases.postValue(Machinsignoffcases.datagettingsuccesfully);
//                       machineloadingformachinecode.postValue(getmachinecode.getData());
//
//                    }
//                    else if(getmachinecode.getResponseStatus().getStatusMessage().equals("Wrong machine code")){
//                        machinesignoffcases.postValue(Machinsignoffcases.wrongmachinecode);
//
//
//                    }
//                    else if (getmachinecode.getResponseStatus().getStatusMessage().equals("There is no loading quantity on the machine!"))
//                    {
//                        machinesignoffcases.postValue(Machinsignoffcases.noloadingquantityonthemachine);
//
//
//                    }

                status.postValue(Status.SUCCESS);
                }));

    }
    void checkBasketEmpty(String basketCode,String parentId,String childId,String jobOrderId,String operationId,int sequenceNo){
        disposable.add(apiInterface.checkBasketStatus(LocaleHelper.getLanguage(getApplication().getApplicationContext()),USER_ID,DEVICE_SERIAL_NO,basketCode,parentId,"0",jobOrderId,operationId,sequenceNo)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((response, throwable) -> {
                    checkBasketEmpty.postValue(response.getResponseStatus());
                    status.postValue(Status.SUCCESS);
                }));

    }



    public MutableLiveData<ResponseStatus> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Machinsignoffcases> getMachinesignoffcases() {
        return machinesignoffcases;
    }

    public MutableLiveData<Apigetmachinecode<MachineLoading>> getApiResponseMachineLoadingData() {
        return apiResponseMachineLoadingData;
    }

    public MutableLiveData<ResponseStatus> getCheckBasketEmpty() {
        return checkBasketEmpty;
    }
}
