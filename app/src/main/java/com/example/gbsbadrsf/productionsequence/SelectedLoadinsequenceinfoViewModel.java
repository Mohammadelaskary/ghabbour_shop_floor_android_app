package com.example.gbsbadrsf.productionsequence;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.google.gson.Gson;

import io.reactivex.disposables.CompositeDisposable;

public class SelectedLoadinsequenceinfoViewModel extends AndroidViewModel {
    Gson gson;
    Productionsequencerepository repository;
    private MutableLiveData<LoadingSequenceInfo> responseLiveData ;
    private MutableLiveData<Status> status;
    private MutableLiveData<Loadingstatus> loadingstatustype;

    private CompositeDisposable disposable = new CompositeDisposable();

//    @Inject
    public SelectedLoadinsequenceinfoViewModel(Application application) {
        super(application);
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        loadingstatustype = new MutableLiveData<>();

        this.repository=new Productionsequencerepository(application.getApplicationContext());
    }
    void getselectedloadingsequence(int UserID,String DeviceSerialNo,int LoadingSequenceID){
        disposable.add(repository.Loadingsequenceinfo(UserID,DeviceSerialNo,LoadingSequenceID)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((loadingSequenceInfoAPIResponseLoadingsequenceinfo, throwable) -> {
//                    if (loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getLoadingSequenceStatus()== 1)
//                    {
//                        loadingstatustype.postValue(Loadingstatus.Loadingstatusbusy);
//
//
//                    }
//                    else
                        if (
//                            loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getLoadingSequenceStatus()==0 &&
                                    loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getRequiredDie()==true){
                        //loadingstatus=0&&requirdie=true
                        loadingstatustype.postValue(Loadingstatus.Loadingstatusfreeandrequiredietrue);

                    }
                    else if(
//                            loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getLoadingSequenceStatus()==0 &&
                                    loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getRequiredDie()==false)
                    {
                       //loadingstatus=0&&requirdie=false
                        loadingstatustype.postValue(Loadingstatus.Loadingstatusfreeandrequirediefalse);


                    }
                    status.postValue(Status.SUCCESS);
                }
                ));


    }
    public MutableLiveData<LoadingSequenceInfo> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Loadingstatus> getLoadingstatustype() {
        return loadingstatustype;
    }




}
