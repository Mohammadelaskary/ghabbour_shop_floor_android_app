package com.example.gbsbadrsf.machinewip;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentMainMachineWipBinding;

import java.util.ArrayList;
import java.util.List;


public class MachineWipMainFragment extends Fragment {
    FragmentMainMachineWipBinding binding;

    public RecyclerView recyclerView;
//    @Inject
//    ViewModelProviderFactory provider;
    CheckBox checkBox;

//    @Inject
//    Gson gson;
    MachinewipAdapter adapter;
    List<MachinesWIP> machinesWIPList;
    MachinewipViewModel viewModel;
    ProgressDialog progressDialog;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainMachineWipBinding.inflate(inflater, container, false);
//        viewModel = ViewModelProviders.of(this,provider).get(MachinewipViewModel.class);


        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MachinewipViewModel.class);
        viewModel.getmachinewip(USER_ID,DEVICE_SERIAL_NO);
        progressDialog = loadingProgressDialog(getContext());
        observeStatus();
        setUpRecyclerView();
        attachListeners();
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else if (status.equals(Status.SUCCESS))
                progressDialog.hide();
            else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void setUpRecyclerView() {
        machinesWIPList = new ArrayList<>();
        adapter = new MachinewipAdapter(machinesWIPList);
        binding.defectqtnRv.setAdapter(adapter);
    }
    private void attachListeners() {

        viewModel.getProductionsequenceResponse().observe(getViewLifecycleOwner(), response->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    if (!response.getData().isEmpty()) {
                        adapter.setMachinesWIPList(response.getData());
                        binding.noList.setVisibility(View.GONE);
                    } else {
                        binding.noList.setText(R.string.no_machines_in_wip);
                        binding.noList.setVisibility(View.VISIBLE);
                    }
                } else {
                    warningDialog(getContext(),statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }
}