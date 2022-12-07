package com.example.gbsbadrsf.signin;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.MyMethods.SingleLiveEvent;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.repository.Authenticationrepository;
import com.google.gson.Gson;


import io.reactivex.disposables.CompositeDisposable;

public class SignInViewModel extends AndroidViewModel {
    Gson gson;
    Authenticationrepository repository;
    private SingleLiveEvent<APIResponseSignin> responseLiveData ;
    private MutableLiveData<ResponseStatus> responseStatus;
    private MutableLiveData<Status> status;
    private MutableLiveData<Usertype> usertype;
    private MutableLiveData<Integer>  userId;
    private MutableLiveData<String>  userName;
    private MutableLiveData<Throwable> signInError;

    private CompositeDisposable disposable = new CompositeDisposable();
    String pass;

//    @Inject
    public SignInViewModel(Application application) {
        super(application);
        responseLiveData = new SingleLiveEvent<>();
        status = new MutableLiveData<>();
        usertype = new MutableLiveData<>();
        userId = new MutableLiveData<>();
        userName = new MutableLiveData<>();
        this.repository= new Authenticationrepository(application.getApplicationContext());
        responseStatus = new MutableLiveData<>();
        signInError = new MutableLiveData<>();
    }

    public MutableLiveData<ResponseStatus> getResponseStatus() {
        return responseStatus;
    }

    void login(String Username, String pass){
        disposable.add(repository.Login(Username,pass).doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(userInfoAPIResponseSignin -> {
//            if (userInfoAPIResponseSignin != null) {
//                if (userInfoAPIResponseSignin.getData().getIsPlanningUser() &&
//                        userInfoAPIResponseSignin.getData().getIsProductionUser() &&
//                        userInfoAPIResponseSignin.getData().getIsQualityControlUser() &&
//                        userInfoAPIResponseSignin.getData().getIsProductionManufaturing() &&
//                        userInfoAPIResponseSignin.getData().getIsProductionWelding() &&
//                        userInfoAPIResponseSignin.getData().getIsProductionPainting() &&
//                        userInfoAPIResponseSignin.getData().getIsQcmanufaturing() &&
//                        userInfoAPIResponseSignin.getData().getIsQcpainting()
//                ) {
//                    usertype.postValue(Usertype.All);
//                } else if (userInfoAPIResponseSignin.getData().getIsPlanningUser()) {
//                    usertype.postValue(Usertype.PlanningUser);
//
//                } else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
//                        && userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
//                        && userInfoAPIResponseSignin.getData().getIsProductionWelding()
//                        && userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
//                    //admin10 pass 123
//                    //direct to main production
//                    usertype.postValue(Usertype.ProductionUser);
//                }
//                else if ((userInfoAPIResponseSignin.getResponseStatus().getStatusMessage().equals("Wrong username or password!"))) {
//                    usertype.postValue(Usertype.wrongusernameorpassword);
//                } else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
//                        && userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
//                        && userInfoAPIResponseSignin.getData().getIsQcwelding()
//                        && userInfoAPIResponseSignin.getData().getIsQcpainting()) {
//                    usertype.postValue(Usertype.QualityControlUser);
//                }else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
//                        && userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
//                        && !userInfoAPIResponseSignin.getData().getIsProductionWelding()
//                        && !userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
//                    usertype.postValue(Usertype.ProductionManufaturing);
//                }
//                else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
//                        && !userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
//                        && userInfoAPIResponseSignin.getData().getIsProductionWelding()
//                        && !userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
//                    usertype.postValue(Usertype.ProductionWelding);
//                }else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
//                        && !userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
//                        && !userInfoAPIResponseSignin.getData().getIsProductionWelding()
//                        && userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
//                    usertype.postValue(Usertype.ProductionPainting);
//                }else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
//                        && userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
//                        && !userInfoAPIResponseSignin.getData().getIsQcwelding()
//                        && !userInfoAPIResponseSignin.getData().getIsQcpainting()) {
//                    usertype.postValue(Usertype.Qcmanufaturing);
//                }else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
//                        && !userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
//                        && userInfoAPIResponseSignin.getData().getIsQcwelding()
//                        && !userInfoAPIResponseSignin.getData().getIsQcpainting()) {
//                    usertype.postValue(Usertype.Qcwelding);
//                } else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
//                        && !userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
//                        && !userInfoAPIResponseSignin.getData().getIsQcwelding()
//                        && userInfoAPIResponseSignin.getData().getIsQcpainting()) {
//                    usertype.postValue(Usertype.Qcpainting);
//                } else if (userInfoAPIResponseSignin.getData().getIsWarehouseUser()){
//                    usertype.postValue(Usertype.WAREHOUSE_USER);
//                } else if (userInfoAPIResponseSignin.getData().getHandlingUser()){
//                    usertype.postValue(Usertype.HANDLING_USER);
//                }
//                status.postValue(Status.SUCCESS);
//                userId.postValue(userInfoAPIResponseSignin.getData().getUserId());
//                userName.postValue(userInfoAPIResponseSignin.getData().getEmployeeName());
//            } else {
//                usertype.postValue(Usertype.CONNECTION_ERROR);
//            }
                    status.postValue(Status.SUCCESS);
                    responseLiveData.postValue(userInfoAPIResponseSignin);
                },throwable -> {
                    signInError.postValue(throwable);
            status.postValue(Status.ERROR);
        }));


    }



    public SingleLiveEvent<APIResponseSignin> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Usertype> getUsertype() {
        return usertype;
    }

    public MutableLiveData<Integer> getUserId() {
        return userId;
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public MutableLiveData<Throwable> getSignInError() {
        return signInError;
    }
}
