package com.example.gbsbadrsf;

import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.databinding.FragmentApplicationUpdateBinding;
import com.example.gbsbadrsf.repository.ApplicationUpdateViewModelFactory;

public class ApplicationUpdateFragment extends Fragment {

    private ApplicationUpdateViewModel viewModel;
    private FragmentApplicationUpdateBinding binding;
    public static ApplicationUpdateFragment newInstance() {
        return new ApplicationUpdateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentApplicationUpdateBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ApplicationUpdateViewModelFactory(requireActivity().getApplication())).get(ApplicationUpdateViewModel.class);


    }

    private void observeDownloadStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case SUCCESS:
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            observeDownloadStatus();
            observeDownloadProgress();
            observeGetApkDataResponse();
            observeError();
            binding.loadErrorMessage.setText(R.string.updating);


        }

    private void observeError() {
        viewModel.getGetMobileVersionError().observe(getViewLifecycleOwner(), error -> warningDialog(requireContext(),error));
    }

    private String apkData;
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void observeGetApkDataResponse() {
        viewModel.getGetMobileVersion().observe(getViewLifecycleOwner(),apiResponseGetMobileVersion -> {
            if (apiResponseGetMobileVersion!=null){
                if (apiResponseGetMobileVersion.getResponseStatus().getIsSuccess()){
                    apkData = apiResponseGetMobileVersion.getApkData();
                    viewModel.writeFileOnInternalStorage("my_apk.apk",apkData);
                    viewModel.installApk();
                } else warningDialog(getContext(),apiResponseGetMobileVersion.getResponseStatus().getStatusMessage());
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private void observeDownloadProgress() {
        binding.progressBar.show();
        viewModel.getProgress().observe(getViewLifecycleOwner(),progress -> {
            binding.progressBar.setProgress(progress);
            String progressText = progress+" %";
            binding.progressText.setText(progressText);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.update),(MainActivity) requireActivity());
    }
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        viewModel.GetMobileVersion();
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }
            }
    );

}
