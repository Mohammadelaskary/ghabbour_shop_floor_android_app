package com.example.gbsbadrsf.Manfacturing.machineloading;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.Productionsequencerepository;

import io.reactivex.disposables.CompositeDisposable;

public class MachineloadingViewModel extends AndroidViewModel {
//    Gson gson;
    Productionsequencerepository repository;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<typesosavedloading> typesosavedloading;
    private MutableLiveData<Status> status;



    private CompositeDisposable disposable = new CompositeDisposable();
    String pass;
//    @Inject
    public MachineloadingViewModel(Application application) {
        super(application);
        responseLiveData = new MutableLiveData<>();
        typesosavedloading = new MutableLiveData<>();

        status = new MutableLiveData<>();

        this.repository=new Productionsequencerepository(application.getApplicationContext());
    }
    void savefirstloading(int UserId,String DeviceSerialNo,int LoadingSequenceID,String MachineCode,String DieCode,String  LoadingQtyMobile){
        disposable.add(repository.SaveLoadingsequenceinfo(UserId,DeviceSerialNo,LoadingSequenceID,MachineCode,DieCode,LoadingQtyMobile)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(responseStatusApiSavefirstloading -> {
                    responseLiveData.postValue(responseStatusApiSavefirstloading.getResponseStatus());
//            if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Saving data successfully"))
//            {
//                typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.Savedsuccessfully);
//
//
//            }
//            else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("The machine has already been used")){
//                typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.machinealreadyused);
//
//
//
//            }
//            else if(responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong machine code")){
//                typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.wromgmachinecode);
//
//
//            }
//           else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong die code for this child"))
//           {
//               typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.wrongdiecode);
//
//
//            }
//            else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction"))
//            {
//                typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.servererror);
//
//
//            }
            status.postValue(Status.SUCCESS);
        },throwable -> status.postValue(Status.ERROR)));

    }
    public MutableLiveData<ResponseStatus> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<typesosavedloading> gettypesofsavedloading() {
        return typesosavedloading;
    }

}
