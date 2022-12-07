package com.example.gbsbadrsf.Quality.paint.SignOffBaskets;

import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseFullInspection_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.FullInspectionData_Painting;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintSignOffBasketsViewModel extends ViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<Status> status;
    private MutableLiveData<ApiResponseFullInspection_Painting> fullInspectionResponse;

//    @Inject
//    Gson gson;
//    @Inject
    public PaintSignOffBasketsViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();
        fullInspectionResponse = new MutableLiveData<>();
    }
    public void saveFullInspectionData(FullInspectionData_Painting data){
        disposable.add(apiInterface.SaveFullInspectionData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {fullInspectionResponse.postValue(response);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseFullInspection_Painting> getFullInspectionResponse() {
        return fullInspectionResponse;
    }
}