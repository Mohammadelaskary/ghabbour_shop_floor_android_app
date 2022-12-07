package com.example.gbsbadrsf.Handling.WarehouseCounting;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.data.response.Status.ERROR;
import static com.example.gbsbadrsf.data.response.Status.LOADING;
import static com.example.gbsbadrsf.data.response.Status.SUCCESS;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.SaveRejectionRequestBody;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.ApiGetCountingData;
import com.example.gbsbadrsf.data.response.CountingData;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentCountingBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.intermec.aidc.BarcodeReadListener;

import java.util.HashMap;
import java.util.Map;


public class CountingFragment extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, BarcodeReadListener {
//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentCountingBinding binding;
    ProgressDialog progressDialog;
    private CountingViewModel viewModel;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCountingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        viewModel = ViewModelProviders.of(this, providerFactory).get(CountingViewModel.class);
        viewModel = new ViewModelProvider(this).get(CountingViewModel.class);
        handleEditTextFocus(binding.barcodecodeEdt);
        progressDialog = loadingProgressDialog(getContext());
        binding.barcodenewEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    viewModel.getbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodecodeEdt.getEditText().getText().toString());
                    return true;
                }
                return false;
            }
        }); //{
        observeStatus();

//        fragmentCountingBinding.barcodenewEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                countingViewModel.getbarcodecodedata("1", "S123", fragmentCountingBinding.barcodenewEdt.getText().toString());
//
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        getdata();

        // initViews();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = binding.barcodecodeEdt.getEditText().getText().toString().trim();
                String qty     = binding.qty.getEditText().getText().toString().trim();

                if (!barcode.isEmpty()){
                    if (!qty.isEmpty()){
                        if (containsOnlyDigits(qty)) {
//                            if (Integer.parseInt(qty)<=loadingQty-countingQty) {
                            viewModel.setbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, barcode, qty);
//                            } else {
//                                binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
//                                binding.qty.getEditText().requestFocus();
//                            }
                        } else {
                            binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
                            binding.qty.getEditText().requestFocus();
                        }
                    } else {
                        binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
                        binding.qty.getEditText().requestFocus();
                    }
                } else {
                    binding.barcodecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_barcode));
                    binding.barcodecodeEdt.getEditText().requestFocus();
                }

            }
        });

        subscribeRequest();
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), response -> {
            Status status = (Status) response;
            switch (status) {
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.dismiss();
                    break;
            }
        });
    }
    int basketSignOff,loadingQty;
    public void getdata() {
        viewModel.getdataforrbarcode().observe(getViewLifecycleOwner(), apiResponse -> {
            ApiGetCountingData<CountingData>  response =(ApiGetCountingData<CountingData>) apiResponse;
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    binding.parentdesc.setText(response.getData().getParentDescription());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    binding.totalPaintSignOffQty.setText(response.getData().getBasketSignOutQty().toString());
//                    binding.currentSignOffQty.setText(response.getData().getSignOutQty().toString());
                    binding.jobOrderQty.setText(response.getData().getJobOrderQty().toString());
                    String basketQtyPerTotalSignOffQty = response.getData().getBasketSignOutQty()+" / "+response.getData().getTotalHandlingSignOutQty();
                    binding.qty.getEditText().setText(basketQtyPerTotalSignOffQty);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    basketSignOff = response.getData().getCountingQty();
                    if (response.getData().getCountingQty()==0)
                        binding.countedBefore.setVisibility(View.GONE);
                    else
                        binding.countedBefore.setVisibility(View.VISIBLE);
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.barcodecodeEdt.setError(statusMessage);
                }
            } else {
                binding.dataLayout.setVisibility(View.GONE);
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

//    private void initViews() {
//        fragmentProductionSignoffBinding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*Constant c = new Constant();
//                try {
//                    if (c.getTotalQty().equals(null)){
//                        c.setTotalQty(0);
//                    }
//                }catch (Exception e){
//                    c.setTotalQty(0);
//                }*/
//                Bundle args = new Bundle();
//                args.putString("childdesc", fragmentProductionSignoffBinding.childesc.getText().toString());
//                args.putString("loadingqty", fragmentProductionSignoffBinding.loadingqtn.getText().toString());
//
//                Signoffitemsdialog dialog = new Signoffitemsdialog();
//                dialog.setArguments(args);
//                dialog.setTargetFragment(ProductionSignoffFragment.this, 1);
//                dialog.show(getFragmentManager(), "MyCustomDialog");
//
//
//            }
//        });
//        fragmentProductionSignoffBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
//
//                machineSignoffBody.setMachineCode(fragmentProductionSignoffBinding.machinecodeEdt.getText().toString());
//                //  machineSignoffBody.setSignOutQty(passedtext);
//                machineSignoffBody.setBasketLst(passedinput);
//                machinesignoffViewModel.getmachinesignoff(machineSignoffBody, getContext());
//
//
//            }
//        });
//
//
//    }

    private void subscribeRequest() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
//            switch (machinsignoffcases) {
//                case Donesuccessfully:
//                    Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
//
//
//                    break;
//
//                case wrongmachinecode: {
//                    binding.barcodenewEdt.requestFocus();
//                    warningDialog(getContext(),"Wrong Barcode or No data found!");
//                } break;
//                case Updatedsuccessfully: {
//                    Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
//                } break;
//
//            }
            ResponseStatus response = (ResponseStatus) apiResponse;
            if (response!=null){
                String statusMessage = response.getStatusMessage();
                if (response.getIsSuccess()) {

                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    back(CountingFragment.this);

                } else
                        binding.barcodecodeEdt.setError(statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String scannedText = barCodeReader.scannedData(barcodeReadEvent);
                binding.barcodenewEdt.setText(scannedText);
                viewModel.getbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());


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
    public void onResume () {
        super.onResume();
       barCodeReader.onResume();
        changeTitle(getString(R.string.handling),(MainActivity) requireActivity());
    }

    @Override
    public void onPause () {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                binding.barcodenewEdt.setText(scannedText);
                viewModel.getbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());


            }
        });
    }
}

