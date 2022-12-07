package com.example.gbsbadrsf.Quality.paint.RejectionRequestsList;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest.DisplayDefectsListAdapter;
import com.example.gbsbadrsf.Quality.paint.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintRejectionRequestDetailsBinding;
import com.example.gbsbadrsf.databinding.FragmentRejectionRequestBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintRejectionRequestDetailsFragment extends Fragment implements View.OnClickListener {
    FragmentPaintRejectionRequestDetailsBinding binding;
    PaintRejectionRequestDetailsViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    private BottomSheetBehavior defectsBottomSheet;
    public PaintRejectionRequestDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RejectionRequest rejectionRequest;
    private void getReceivedData() {
        if (getArguments() != null){
            rejectionRequest = getArguments().getParcelable("rejectionRequest");
        }
    }

    private void fillData() {
        String parentCode        = rejectionRequest.getParentCode();
        String parentDescription = rejectionRequest.getParentDescription();
        String jobOrderName     = rejectionRequest.getJobOrderName();
        int rejectedQty         = rejectionRequest.getRejectionQty();
        String department       = rejectionRequest.getDepartmentEnName();
        binding.parentDesc.setText(parentDescription);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.rejectedQtyData.rejectedQty.setText(String.valueOf(rejectedQty));
        binding.responspileDep.setText(department);
        binding.reason.setText(rejectionRequest.getRejectionReasonName());
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(PaintRejectionRequestDetailsViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintRejectionRequestDetailsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintRejectionRequestDetailsBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupProgressDialog();
        initViewModel();
        getReceivedData();
        fillData();
        attachButtonsToListener();
        observeRejectionRequestTakeAction();
        observeRejectionRequestTakeActionStatus();
        setUpDefectsBottomSheet();
    }
    private void setUpDefectsBottomSheet() {
        defectsBottomSheet = BottomSheetBehavior.from(binding.defectsListBottomSheet.getRoot());
        hideDefectsBottomSheet();
        setUpDefectsRecyclerView();
    }
    private DisplayDefectsListAdapter adapter;
    private void setUpDefectsRecyclerView() {
        adapter = new DisplayDefectsListAdapter();
        binding.defectsListBottomSheet.defectsCheckList.setAdapter(adapter);
    }

    private void hideDefectsBottomSheet() {
        defectsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    private void showDefectsBottomSheet() {
        defectsBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void attachButtonsToListener() {
        binding.acceptBtn.setOnClickListener(this);
        binding.declineBtn.setOnClickListener(this);
        binding.displayDefectBtn.setOnClickListener(this);
    }

    private ProgressDialog progressDialog;
    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeRejectionRequestTakeActionStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeRejectionRequestTakeAction() {
        NavController navController = NavHostFragment.findNavController(this);
        viewModel.rejectionRequestTakeActionLiveData.observe(getViewLifecycleOwner(),apiResponseRejectionRequestTakeAction -> {
            if (apiResponseRejectionRequestTakeAction!=null) {
                String statusMessage = apiResponseRejectionRequestTakeAction.getResponseStatus().getStatusMessage();
                if (apiResponseRejectionRequestTakeAction.getResponseStatus().getIsSuccess()) {
//                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    showSuccessAlerter(statusMessage,getActivity());
                    navController.popBackStack();
                } else
                    warningDialog(getContext(),statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_saving_data));
            }
        });
    }

    int userId = 1 ;
    int rejectionRequestId;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        rejectionRequestId = rejectionRequest.getRejectionRequestId();
        switch (id){
            case R.id.accept_btn:{
                viewModel.saveRejectionRequestTakeAction(USER_ID,rejectionRequestId,true);
            } break;
            case R.id.decline_btn:{
                viewModel.saveRejectionRequestTakeAction(USER_ID,rejectionRequestId,false);
            } break;
            case R.id.display_defect_btn:
                getDefects(rejectionRequest.getRejectionRequestId());
                break;
        }
    }
    private void getDefects(Integer rejectionRequestId) {
        viewModel.getDefects(USER_ID,DEVICE_SERIAL_NO,rejectionRequestId);
        viewModel.getGetRejectionRequestById().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestGetRejectionRequestByID -> {
            if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getIsSuccess()){
                    adapter.setDefects(apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getDefectsList());
                    showDefectsBottomSheet();
                }else
                    MyMethods.warningDialog(getContext(),statusMessage);
            } else
                MyMethods.warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }
}