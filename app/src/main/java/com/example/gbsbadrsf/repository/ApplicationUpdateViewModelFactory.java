package com.example.gbsbadrsf.repository;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.ApplicationUpdateViewModel;

public class ApplicationUpdateViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;


    public ApplicationUpdateViewModelFactory(Application application) {
        mApplication = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ApplicationUpdateViewModel(mApplication);
    }
}
