package com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.ApprovalRejectionRequest_Paint;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingRejectionRequest.ApprovalRejectionRequest_Manufacturing.ApprovalRejectionRequestsListFragment.REJECTION_REQUEST_ID;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.RejectionRequestClosingFragmentBinding;

public class RejectionRequestClosingFragment extends Fragment implements View.OnClickListener {

    private RejectionRequestClosingViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    public static RejectionRequestClosingFragment newInstance() {
        return new RejectionRequestClosingFragment();
    }
    private RejectionRequestClosingFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RejectionRequestClosingFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this,provider).get(RejectionRequestClosingViewModel.class);
        viewModel = new ViewModelProvider(this).get(RejectionRequestClosingViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            int rejectionRequestId = getArguments().getInt(REJECTION_REQUEST_ID);
            viewModel.getRejectionRequestData(USER_ID,DEVICE_SERIAL_NO,rejectionRequestId);
        }
        observeGettingRejectionRequestData();
        observeClosingRejectionRequest();
        observeRequestStatus();
        binding.closeRequest.setOnClickListener(this);
    }

    private void observeClosingRejectionRequest() {
        viewModel.getGetCloseRejectionRequestResponse().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestCloseRequest -> {
            if (apiResponseManufacturingRejectionRequestCloseRequest!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestCloseRequest.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestCloseRequest.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(RejectionRequestClosingFragment.this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeRequestStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show(); break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    progressDialog.hide();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
            }
        });
    }

    private RejectionRequest rejectionRequest;
    private void observeGettingRejectionRequestData() {
        viewModel.getGetRejectionRequestData().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestGetRejectionRequestByID -> {
            if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getIsSuccess()){
                    rejectionRequest = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getRejectionRequest();
                    fillData();
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void fillData() {
        binding.childesc.setText(rejectionRequest.getParentDescription());
        binding.jobOrderData.jobordernum.setText(rejectionRequest.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(rejectionRequest.getJobOrderQty().toString());
        binding.rejectedQtyData.rejectedQty.setText(rejectionRequest.getRejectionQty().toString());
        binding.responspileDep.setText(rejectionRequest.getDepartmentEnName());
        binding.reason.setText(rejectionRequest.getRejectionReasonName());
        binding.locator.setText(rejectionRequest.getLocatorCode());
        binding.subInventory.setText(rejectionRequest.getSubInventoryDesc());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_request:
                String certificateNo = binding.certificationNo.getEditText().getText().toString().trim();
                if (!certificateNo.isEmpty()) {
                    if (containsOnlyDigits(certificateNo)) {
                        viewModel.closeRejectionRequest(USER_ID, DEVICE_SERIAL_NO, rejectionRequest.getRejectionRequestId(),certificateNo);
                    } else binding.certificationNo.setError(getString(R.string.certificate_no_must_contains_only_digits));
                } else
                    binding.certificationNo.setError(getString(R.string.please_enter_certificate_no));
                break;
        }
    }
}