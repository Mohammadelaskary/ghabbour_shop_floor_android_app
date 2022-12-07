package com.example.gbsbadrsf.Quality.welding.RejectionRequestsList;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.welding.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionRejectionRequestsListQualityBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class WeldingRejectionRequestsListQualityFragment extends Fragment implements OnClick {

    FragmentProductionRejectionRequestsListQualityBinding binding;
    WeldingRejectionListAdapter adapter;
    WeldingRejectionRequestsListQualityViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductionRejectionRequestsListQualityBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initViewModel();
        setUpProgressDialog();
        observeGettingRejectionRequestsList();
        getRejectionRequestsList();
    }

    ProgressDialog progressDialog;
    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeGettingRejectionRequestsList() {
        viewModel.getGetRejectionRequestListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status== Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(WeldingRejectionRequestsListQualityViewModel.class);
        viewModel = new ViewModelProvider(this).get(WeldingRejectionRequestsListQualityViewModel.class);

    }
    int userId = USER_ID;
    String deviceSerialNo = DEVICE_SERIAL_NO;
    private void getRejectionRequestsList() {
        viewModel.getRejectionRequests(userId,deviceSerialNo);
        viewModel.getGetRejectionRequestListLiveData().observe(getViewLifecycleOwner(),apiResponseGetRejectionRequestList -> {
            if (apiResponseGetRejectionRequestList!=null){
                String statusMessage = apiResponseGetRejectionRequestList.getResponseStatus().getStatusMessage();
                if (apiResponseGetRejectionRequestList.getResponseStatus().getIsSuccess()) {
                    Log.d(TAG, "getRejectionRequestsList: "+apiResponseGetRejectionRequestList.getRejectionRequestList().size());
                    adapter.setRejectionRequests(apiResponseGetRejectionRequestList.getRejectionRequestList());
                    binding.noList.setVisibility(View.GONE);
                } else {
                    binding.noList.setText(statusMessage);
                    binding.noList.setVisibility(View.VISIBLE);
                    adapter.setRejectionRequests(new ArrayList<>());
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void initViews() {
        adapter = new WeldingRejectionListAdapter();
        binding.productionscrapscrapRv.setAdapter(adapter);
    }

    @Override
    public void OnItemClickedListener(int position) {
        Navigation.findNavController(getView()).navigate(R.id.action_productionscraplistinqualityFragment_to_productionscraprequestqcFragment);

    }
}