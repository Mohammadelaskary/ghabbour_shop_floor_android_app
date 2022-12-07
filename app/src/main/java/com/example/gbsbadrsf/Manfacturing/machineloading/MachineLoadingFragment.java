package com.example.gbsbadrsf.Manfacturing.machineloading;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.productionsequence.ProductionSequence.PPR_KEY;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentMachineLoadingBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;


public class MachineLoadingFragment extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, BarcodeReadListener {

    FragmentMachineLoadingBinding binding;
     private BarcodeReader barcodeReader;
     private SetUpBarCodeReader barCodeReader;
     private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

//    @Inject
//    ViewModelProviderFactory providerFactory;
    MachineloadingViewModel viewModel;
    private ResponseStatus responseStatus;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentMachineLoadingBinding.inflate(inflater, container, false);

        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = loadingProgressDialog(getContext());
//        machineloadingViewModel = ViewModelProviders.of(this, providerFactory).get(MachineloadingViewModel.class);
        viewModel = new ViewModelProvider(this).get(MachineloadingViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();

        responseStatus = new ResponseStatus();
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        handleEditTextFocus(binding.diecodeEdt,binding.machinecodeEdt);
        initObjects();
        //attachListeners();
        addTextWatchers();
        subscribeRequest();
        observeSavingData();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                String dieCode     = binding.newdiecodeEdt.getText().toString().trim();
                String loadingQty  = binding.newloadingqtnEdt.getText().toString().trim();
                if (machineCode.isEmpty())
                    binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code));
                if (dieCode.isEmpty()&&requiredDie)
                    binding.diecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_die_code));
                if (!dieCode.equals(requiredDieCode)&&requiredDie)
                    binding.diecodeEdt.setError(getString(R.string.wrong_die_code));
                if (loadingQty.isEmpty())
                    binding.loadingqtnEdt.setError(getString(R.string.please_enter_a_valid_loading_qty));
                else{
                    if (!containsOnlyDigits(loadingQty))
                        binding.loadingqtnEdt.setError(getString(R.string.please_enter_a_valid_loading_qty));
                    else {
                        if (Integer.parseInt(loadingQty)>remainQty)
                            binding.loadingqtnEdt.setError(getString(R.string.loading_qty_must_be_equal_or_less_than_available_loading_qty));
                        if (Integer.parseInt(loadingQty)==0)
                            binding.loadingqtnEdt.setError(getString(R.string.loading_qty_can_not_be_0));
                    }

                }
                if (
                        !machineCode.isEmpty()&&
                                !(dieCode.isEmpty()&&requiredDie)&&
                                !(!dieCode.equals(requiredDieCode)&&requiredDie)&&
                                !loadingQty.isEmpty()&&
                                containsOnlyDigits(loadingQty)&&
                                Integer.parseInt(loadingQty)<=remainQty&&
                                Integer.parseInt(loadingQty)>0
                ) {
                    viewModel.savefirstloading(USER_ID, DEVICE_SERIAL_NO,loadingSequenceId , machineCode, dieCode, loadingQty);
                }
            }
        });
    }

    private void addTextWatchers() {
        binding.diecodeEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.diecodeEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.diecodeEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.diecodeEdt.setError(null);
            }
        });
        binding.machinecodeEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.machinecodeEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.machinecodeEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.machinecodeEdt.setError(null);
            }
        });
        binding.loadingqtnEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.loadingqtnEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.loadingqtnEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.loadingqtnEdt.setError(null);
            }
        });
    }

    private void observeSavingData() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if ((status.equals(Status.LOADING))) {
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)) {
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }
    int remainQty,loadingSequenceId;
    boolean requiredDie;
    String requiredDieCode;
    private void initObjects() {
        if (getArguments() != null) {
            Ppr ppr = getArguments().getParcelable(PPR_KEY);
            if (!ppr.getDieCode().isEmpty()) {
                binding.diecodeEdt.getEditText().setEnabled(true);
                binding.diecodeEdt.getEditText().setClickable(true);
                requiredDie = true;
            } else {
                binding.diecodeEdt.getEditText().setEnabled(false);
                binding.diecodeEdt.getEditText().setClickable(false);
                requiredDie = false;
            }
            binding.jobordernum.setText(ppr.getJobOrderName());
            binding.Joborderqtn.setText(String.valueOf(ppr.getJobOrderQty()));
            binding.childesc.setText(ppr.getChildDescription());
            binding.loadingqtnEdt.getEditText().setText(String.valueOf(ppr.getAvailableloadingQty()));
            binding.remainingQty.setText(ppr.getAvailableloadingQty().toString());
            binding.operation.setText(ppr.getOperationEnName());
            remainQty = ppr.getAvailableloadingQty();
            loadingSequenceId = ppr.getLoadingSequenceID();
            requiredDieCode = ppr.getDieCode();
        }
    }
    private void subscribeRequest() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), responseStatus -> {
            if (responseStatus!=null) {
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()){
                    showSuccessAlerter(statusMessage, getActivity());
                    back(MachineLoadingFragment.this);
                } else {
                    switch (statusMessage) {
                        case "Saving data successfully":
//                    Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();
                            showSuccessAlerter(statusMessage, getActivity());
                            back(MachineLoadingFragment.this);
                            break;
                        case "The machine has already been used":
//                        Toast.makeText(getContext(), "The machine has already been used", Toast.LENGTH_SHORT).show();
                            binding.machinecodeEdt.setError("The machine has already been used");
                            binding.machinecodeEdt.getEditText().requestFocus();
                            break;

                        case "Wrong machine code":
                            binding.machinecodeEdt.setError("Wrong machine code");
                            binding.machinecodeEdt.getEditText().requestFocus();
                            break;
                        case "Wrong die code for this child":
                            binding.diecodeEdt.setError("Wrong die code for this child");
                            binding.diecodeEdt.getEditText().requestFocus();
                            break;
                        case "There was a server side failure while respond to this transaction":
                            warningDialog(getContext(), "There was a server side failure while respond to this transaction");
                            break;
                        default:
                            warningDialog(getContext(), statusMessage);
                            break;

                    }
                }
            }
        });




    }


//    private void attachListeners() {
//        fragmentMachineLoadingBinding.addworkersBtn.setOnClickListener(__ -> {
//            Navigation.findNavController(getActivity(), R.id.myNavhostfragment).navigate(R.id.action_machineLoadingFragment_to_addworkersFragment);
//
//        });
//
//    }
@Override
public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
        @Override
        public void run() {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (binding.machinecodeNewedttxt.isFocused()) {
                binding.machinecodeNewedttxt.setText(scannedText);
                if (requiredDie)
                    binding.newdiecodeEdt.requestFocus();
                else
                    binding.loadingqtnEdt.requestFocus();
            }
            else if (binding.newdiecodeEdt.isFocused()){
                binding.newdiecodeEdt.setText(scannedText);
                binding.loadingqtnEdt.requestFocus();
            }

        }
    });


}

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
            }
        });
    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);

    }
    @Override
    public void onResume() {
        super.onResume();
        binding.machinecodeEdt.getEditText().requestFocus();
        barCodeReader.onResume();
        }
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    // handle back button's click listener
//                  Navigation.findNavController(getView()).popBackStack(R.id.productionSequence,true);
//
//
//                    return true;
//                }
//                return false;
//            }
//        });


    @Override
    public void onPause() {
        super.onPause();
       barCodeReader.onPause();
//       barCodeReaderInterMec.onPause();
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                if (binding.machinecodeNewedttxt.isFocused()) {
                    binding.machinecodeNewedttxt.setText(scannedText);
                    if (requiredDie)
                        binding.newdiecodeEdt.requestFocus();
                    else
                        binding.loadingqtnEdt.requestFocus();
                }
                else if (binding.newdiecodeEdt.isFocused()){
                    binding.newdiecodeEdt.setText(scannedText);
                    binding.loadingqtnEdt.requestFocus();
                }

            }
        });
    }
}