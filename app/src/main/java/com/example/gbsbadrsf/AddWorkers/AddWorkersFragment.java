package com.example.gbsbadrsf.AddWorkers;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.databinding.FragmentAddWorkersBinding;
import com.google.android.material.snackbar.Snackbar;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class AddWorkersFragment extends Fragment implements View.OnClickListener, AllWorkersAdapter.WorkerItemClicked, BarcodeReadListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    private AddWorkersViewModel viewModel;

    public static AddWorkersFragment newInstance() {
        return new AddWorkersFragment();
    }
    private FragmentAddWorkersBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddWorkersBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private AllWorkersAdapter allWorkersAdapter;
    private SelectedWorkersAdapter selectedWorkersAdapter;
    private List<Worker> selectedWorkers = new ArrayList<>();
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddWorkersViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        progressDialog = loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getWorkers();
        observeGettingWorkersList();
        observeGettingMachineData();
        setUpAllWorkersAdapter();
        setUpSelectedWorkersAdapter();
        observeStatus();
        observeAddingWorkers();
        binding.workerNameCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                allWorkersAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.save.setOnClickListener(this);
    }

    private void observeAddingWorkers() {
        viewModel.getAddWorkersResponse().observe(getViewLifecycleOwner(),apiResponseAddWorkers_machine -> {
            if (apiResponseAddWorkers_machine!=null){
                String statusMessage = apiResponseAddWorkers_machine.getResponseStatus().getStatusMessage();
                if (apiResponseAddWorkers_machine.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.dismiss();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    warningDialog(getContext(),getString(R.string.error_in_getting_data));
                    break;
            }
        });
    }

    private MachineData machineData;
    private List<Integer> selectedWorkersIds = new ArrayList<>();
    private void observeGettingMachineData() {
        viewModel.getGetMachineWorkers().observe(getViewLifecycleOwner(),apiResponseGetMachineWorkers -> {
            if (apiResponseGetMachineWorkers!=null){
                String statusMessage = apiResponseGetMachineWorkers.getResponseStatus().getStatusMessage();
                if (apiResponseGetMachineWorkers.getResponseStatus().getIsSuccess()){
                    machineData = apiResponseGetMachineWorkers.getMachineData();
                    fillData(apiResponseGetMachineWorkers);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    handleTitleLineSelectedWorkers();
                    if (!apiResponseGetMachineWorkers.getGetWorkerTransactions().isEmpty()) {
                        selectedWorkers.clear();
                        selectedWorkers.addAll(apiResponseGetMachineWorkers.getGetWorkerTransactions());
                        for (Worker worker:selectedWorkers){
                            selectedWorkersIds.add(worker.getWorkerId());
                        }
                        selectedWorkersAdapter.notifyDataSetChanged();
                        handleTitleLineSelectedWorkers();
                    }
                } else {
                    warningDialog(getContext(),statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                }
            } else {
                warningDialog(getContext(), getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
            }
        });
    }

    private void fillData(ApiResponseGetMachineWorkers response) {
        binding.childesc.setText(response.getChildDescription());
        binding.jobordernum.setText(response.getGetMachineData().getJobOrderName());
        binding.Joborderqtn.setText(response.getJobOrderQty().toString());
    }

    private void observeGettingWorkersList() {
        viewModel.getGetWorkersList().observe(getViewLifecycleOwner(),apiResponseGetWorkersList -> {
            if (apiResponseGetWorkersList!=null){
                String statusMessage = apiResponseGetWorkersList.getResponseStatus().getStatusMessage();
                if (apiResponseGetWorkersList.getResponseStatus().getIsSuccess()){
                    allWorkersAdapter.setWorkersList(apiResponseGetWorkersList.getWorkers());
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void setUpSelectedWorkersAdapter() {
        selectedWorkersAdapter = new SelectedWorkersAdapter(selectedWorkers);
        binding.selectedWorkers.setAdapter(selectedWorkersAdapter);
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final Worker item = selectedWorkers.get(position);
                selectedWorkersAdapter.removeItem(position);
                selectedWorkersIds.remove(Integer.valueOf(item.getWorkerId()));
                handleTitleLineSelectedWorkers();
                Snackbar snackbar = Snackbar
                        .make(binding.getRoot(), R.string.worker_was_removed, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedWorkersAdapter.restoreItem(item, position);
                        selectedWorkersIds.add(item.getWorkerId());
                        binding.selectedWorkers.scrollToPosition(position);
                        handleTitleLineSelectedWorkers();
                    }
                });
                snackbar.setActionTextColor(Color.GREEN);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(binding.selectedWorkers);
    }

    private void setUpAllWorkersAdapter() {
        allWorkersAdapter = new AllWorkersAdapter(this);
        binding.allWorkers.setAdapter(allWorkersAdapter);
    }
    private void handleTitleLineSelectedWorkers (){
        if (!selectedWorkers.isEmpty()){
            binding.selectedWorkersTitle.setVisibility(View.VISIBLE);
            binding.line.setVisibility(View.VISIBLE);
        } else {
            binding.selectedWorkersTitle.setVisibility(View.GONE);
            binding.line.setVisibility(View.GONE);
        }
    }
    @Override
    public void OnWorkerItemClicked(Worker worker) {
        if (!selectedWorkers.contains(worker)) {
            selectedWorkers.add(worker);
            selectedWorkersAdapter.notifyItemInserted(selectedWorkers.size());
            selectedWorkersIds.add(worker.getWorkerId());
        } else
            warningDialog(getContext(),getString(R.string.worker_added_before));
        handleTitleLineSelectedWorkers();
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (binding.dataLayout.getVisibility()==View.GONE) {
                binding.machineCode.getEditText().setText(scannedText);
                viewModel.getMachineWorkers(scannedText);
            } else {
                binding.workerNameCode.getEditText().setText(scannedText);
            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            if (binding.dataLayout.getVisibility()==View.GONE) {
                binding.machineCode.getEditText().setText(scannedText);
                viewModel.getMachineWorkers(scannedText);
            } else {
                binding.workerNameCode.getEditText().setText(scannedText);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReaderInterMec.onPause();
        barCodeReader.onPause();
    }

    @Override
    public void onClick(View v) {
        if (!selectedWorkersIds.isEmpty()){
            AddWorkers_MachineData data = new AddWorkers_MachineData(
                    USER_ID,
                    DEVICE_SERIAL_NO,
                    binding.machineCode.getEditText().getText().toString().trim(),
                    selectedWorkersIds,
                    LocaleHelper.getLanguage(getContext())
            );
            viewModel.addMachineWorkers(data);
        } else {
            warningDialog(getContext(),getString(R.string.please_select_at_least_one_worker));
        }
    }
}