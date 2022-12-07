package com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.ApprovalRejectionRequest_Paint;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
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
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.ApprovalRejectionRequestsListFragmentBinding;

public class ApprovalRejectionRequestsListFragment extends Fragment implements RejectionRequestAdapter.OnRejectionRequestItemClicked {

    public static final String REJECTION_REQUEST_ID = "rejection_request_id";
    private ApprovalRejectionRequestsListViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    public static ApprovalRejectionRequestsListFragment newInstance() {
        return new ApprovalRejectionRequestsListFragment();
    }
    private ApprovalRejectionRequestsListFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ApprovalRejectionRequestsListFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this,provider).get(ApprovalRejectionRequestsListViewModel.class);
        viewModel = new ViewModelProvider(this).get(ApprovalRejectionRequestsListViewModel.class);
        progressDialog = MyMethods.loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        observeGettingRejectionRequestsList();
        observeGettingRejectionRequestsListStatus();
        observeError();
    }

    private void observeError() {
        viewModel.getError().observe(getViewLifecycleOwner(),throwable -> {
            warningDialog(getContext(),getString(R.string.network_issue));
        });
    }

    private void observeGettingRejectionRequestsListStatus() {
        viewModel.getGetRejectionRequestListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                case ERROR:
                    progressDialog.hide();
                    break;
            }
        });
    }

    private void observeGettingRejectionRequestsList() {
        viewModel.getGetRejectionRequestListLiveData().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestGetRejectionRequestList -> {
            if (apiResponseManufacturingRejectionRequestGetRejectionRequestList!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestGetRejectionRequestList.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestGetRejectionRequestList.getResponseStatus().getIsSuccess()) {
                    adapter.setRejectionRequestList(apiResponseManufacturingRejectionRequestGetRejectionRequestList.getRejectionRequestList());
                    binding.noList.setVisibility(View.GONE);
                } else {
                    binding.noList.setText(statusMessage);
                    binding.noList.setVisibility(View.VISIBLE);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private RejectionRequestAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new RejectionRequestAdapter(this);
        binding.rejectionRequestsList.setAdapter(adapter);
    }
    @Override
    public void onRejectionRequestItemClicked(int rejectionRequestId) {
        Bundle bundle = new Bundle();
        bundle.putInt(REJECTION_REQUEST_ID,rejectionRequestId);
        Navigation.findNavController(getView()).navigate(R.id.approval_rejection_requests_list_fragment_to_rejection_request_closing_fragment,bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getRejectionRequests(USER_ID,DEVICE_SERIAL_NO);
    }
}