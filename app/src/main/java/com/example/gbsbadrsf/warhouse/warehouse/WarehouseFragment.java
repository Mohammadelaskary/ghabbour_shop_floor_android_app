package com.example.gbsbadrsf.warhouse.warehouse;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.FragmentWarehouseBinding;
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


public class WarehouseFragment extends Fragment implements BarcodeReadListener, BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {

//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentWarehouseBinding binding;
    private WarehouseViewModel viewModel;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }

    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWarehouseBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = loadingProgressDialog(getContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //        viewModel = ViewModelProviders.of(this, providerFactory).get(WarehouseViewModel.class);
        viewModel = new ViewModelProvider(this).get(WarehouseViewModel.class);
        handleEditTextFocus(binding.barcodecodeEdt);
        binding.barcodenewEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    viewModel.getrecivingbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());

                    return true;
                }
                return false;
            }
        });
//        fragmentWarehouseBinding.barcodenewEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                warehouseViewModel.getrecivingbarcodecodedata("1", "S123", fragmentWarehouseBinding.barcodenewEdt.getText().toString());
//
//            }





        getdata();

        // initViews();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = binding.barcodenewEdt.getText().toString().trim();
                String qty = binding.qty.getEditText().getText().toString().trim();
                boolean addedBefore = receivingQty != 0;
                binding.saveBtn.setEnabled(false);
                if (!barcode.isEmpty()) {
                    if (!qty.isEmpty()) {
                        if (containsOnlyDigits(qty)) {
                            if (!addedBefore) {
                                if (Integer.parseInt(qty) <= availableReceiving) {
                                    viewModel.setrecivingbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, barcode, qty);
                                } else {
                                    binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
                                    binding.qty.getEditText().requestFocus();
                                }
                            } else
                                warningDialog(getContext(), getString(R.string.please_contact_backoffice_to_update_qty));
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

    int receivingQty,loadingQty,availableReceiving;
    public void getdata() {
        viewModel.getdataforrbarcode().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {

                    binding.dataLayout.setVisibility(View.VISIBLE);
                    binding.locator.setText(response.getData().getLocatorDesc());
                    binding.parentdesc.setText(response.getData().getParentDescription());
                    binding.subInventory.setText(response.getData().getSubInventoryDesc());
                    binding.receivedQtyOracle.setText(response.getData().getTotalReceivingQty().toString());
//                    binding.currentSignOutQty.setText(response.getData().getSignOutQty().toString());
                    binding.subInventory.setText(response.getData().getSubInventoryDesc());
                    binding.Joborderqtn.setText(response.getData().getJobOrderQty().toString());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    if (response.getData().getCountingQty()!=0) {
                        binding.handlingQty.setText(response.getData().getCountingQty().toString());
                    } else {
                        binding.handlingQty.setText("");
                    }
                    receivingQty = response.getData().getReceivingQty();
                    availableReceiving = response.getData().getAvailableReceiving();
                    if (!response.getData().getReceivingQty().equals(0)){
                        binding.qty.getEditText().setText(response.getData().getReceivingQty().toString());
                        binding.qty.getEditText().setEnabled(false);
                        binding.qty.getEditText().setClickable(false);
                    } else {
                        binding.qty.getEditText().setText("");
                    }
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.barcodecodeEdt.setError(statusMessage);
                }
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
            }
            //fragmentCountingBinding.paintqtn.setText(response.getLoadingQty().toString());


        });
    }



    private void subscribeRequest() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
//            switch (machinsignoffcases) {
//                case Donesuccessfully:
//                    Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
//
//
//                    break;
//
//                case wrongmachinecode: {
//                    warningDialog(getContext(),"Wrong Barcode or No data found!");
//                    binding.barcodenewEdt.requestFocus();
//                }  break;
//                case Updatedsuccessfully:
//                    Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
//            }
            if (response!=null) {
                String statusMessage = response.getStatusMessage();
                if (response.getIsSuccess()) {
                        showSuccessAlerter(statusMessage, getActivity());
    //                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                        back(WarehouseFragment.this);
                    } else  {
                        binding.barcodecodeEdt.setError(statusMessage);
                        binding.barcodenewEdt.requestFocus();
                        binding.saveBtn.setEnabled(true);
                    }

                } else {
                warningDialog(getContext(),getString(R.string.error_in_saving_data));
                binding.saveBtn.setEnabled(true);
            }
        });
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;

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
                viewModel.getrecivingbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());


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
        changeTitle(getString(R.string.warehouse),(MainActivity) requireActivity());
    }

    @Override
    public void onPause () {
        super.onPause();
        barCodeReaderInterMec.onPause();
        barCodeReader.onPause();
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                binding.barcodenewEdt.setText(scannedText);
                viewModel.getrecivingbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());


            }
        });
    }
}







