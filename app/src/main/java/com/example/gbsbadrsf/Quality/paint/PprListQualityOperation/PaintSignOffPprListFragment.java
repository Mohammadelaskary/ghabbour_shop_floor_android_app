package com.example.gbsbadrsf.Quality.paint.PprListQualityOperation;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.adapters.AbsSpinnerBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityOperationBinding;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityOperationPprListBinding;
import com.example.gbsbadrsf.databinding.FragmentPaintSignOffPprListBinding;

import java.util.ArrayList;


public class PaintSignOffPprListFragment extends Fragment {

    public PaintSignOffPprListViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
//
//    @Inject
//    Gson gson;


    public PaintSignOffPprListFragment() {
        // Required empty public constructor
    }


    public static PaintSignOffPprListFragment newInstance() {
        return new PaintSignOffPprListFragment();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this,provider).get(PaintSignOffPprListViewModel.class);

        progressDialog = loadingProgressDialog(getContext());
    }
    private FragmentPaintQualityOperationPprListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintQualityOperationPprListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PaintSignOffPprListViewModel.class);
        setUpRecyclerView();
        observeGettingPprList();
        observeGettingPprListStatus();

    }

    private void observeGettingPprListStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.dismiss();
                    break;
                case SUCCESS:
                    progressDialog.dismiss();
                    break;
            }
        });
    }

    private PaintSignOffPprAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new PaintSignOffPprAdapter();
        binding.pprList.setAdapter(adapter);
    }

    private void observeGettingPprList() {
        viewModel.getPaintStationWIP().observe(getViewLifecycleOwner(),apiResponseGetWIP_painting -> {
            if (apiResponseGetWIP_painting!=null){
                if (apiResponseGetWIP_painting.getResponseStatus().getIsSuccess()){
                    adapter.setPprWipPaintList(apiResponseGetWIP_painting.getPprList());
                    binding.noPprList.setVisibility(View.GONE);
                } else {
                    warningDialog(getContext(), apiResponseGetWIP_painting.getResponseStatus().getStatusMessage());
                    binding.noPprList.setText(apiResponseGetWIP_painting.getResponseStatus().getStatusMessage());
                    binding.noPprList.setVisibility(View.VISIBLE);
                    adapter.setPprWipPaintList(new ArrayList<>());
                }
                } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.GetPaintQualityPprList(USER_ID,DEVICE_SERIAL_NO);
        changeTitle(getString(R.string.quality_ppr_list),(MainActivity) getActivity());
    }
}