package com.example.gbsbadrsf.repository;

import android.content.Context;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.UserInfo;

import java.util.ConcurrentModificationException;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Authenticationrepository {
//    @Inject
    ApiInterface apiInterface;
//    @Inject
    private Context context;
    public Authenticationrepository(Context context) {
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        this.context = context;
    }
    public Single<APIResponseSignin> Login(String username , String pass){
        return apiInterface.login(LocaleHelper.getLanguage(context),username, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
