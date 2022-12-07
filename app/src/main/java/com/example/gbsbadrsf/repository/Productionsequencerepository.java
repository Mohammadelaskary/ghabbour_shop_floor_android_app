package com.example.gbsbadrsf.repository;

import android.content.Context;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.APIResponseLoadingsequenceinfo;
import com.example.gbsbadrsf.data.response.ApiMachinesignoff;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.ResponseStatus;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Productionsequencerepository {
//    @Inject
    ApiInterface apiInterface;

//    @Inject
    private Context context;
    public Productionsequencerepository(Context context) {
        this.context = context;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
    }

    //    public Single<APIResponse<List<Ppr>>> Getproductionsequence(String userid, String deviceserialnumber){
//        return apiInterface.getproductionsequence(userid, deviceserialnumber)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
    public Single<APIResponseLoadingsequenceinfo<LoadingSequenceInfo>> Loadingsequenceinfo(int userid, String deviceserialnumber, int loadingsequenceid) {
        return apiInterface.loadingswquenceinfo(LocaleHelper.getLanguage(context),userid, deviceserialnumber, loadingsequenceid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiSavefirstloading<ResponseStatus>> SaveLoadingsequenceinfo(int userid, String deviceserialnumber, int loadingsequenceid, String machinecode, String diecode, String loadingqtymobile) {
        return apiInterface.savefirstloading(LocaleHelper.getLanguage(context),userid, deviceserialnumber, loadingsequenceid, machinecode, diecode, loadingqtymobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    //machinesignoff
    public Single<ApiMachinesignoff<ResponseStatus>> Machinesignoff(MachineSignoffBody body){
        return apiInterface.machinesignoff(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<ApiMachinesignoff<ResponseStatus>> Machinesignoff_Partial(MachineSignoffBody body){
        return apiInterface.MachineSignOff_Partial(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}



