package com.example.gbsbadrsf.productionsequence;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.hideKeyboard;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionSequenceBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionSequence extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener,productionsequenceadapter.onCheckedChangedListener
        ,BarcodeReadListener
{
    public static final String PPR_KEY = "ppr";
    FragmentProductionSequenceBinding binding;
    public RecyclerView recyclerView;

    private String childQty;
    productionsequenceadapter adapter;
    List<Ppr> productionsequenceresponse;
    ProductionsequenceViewModel viewModel;
    SelectedLoadinsequenceinfoViewModel selectedLoadinsequenceinfoViewModel;
    List<String> selectedsequence;
    Ppr clickedPpr;
    ProgressDialog progressDialog;
    private SetUpBarCodeReader barcodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        barcodeReader = new SetUpBarCodeReader(this,this);
        viewModel = new ViewModelProvider(this).get(ProductionsequenceViewModel.class);
        selectedLoadinsequenceinfoViewModel = new ViewModelProvider(this).get(SelectedLoadinsequenceinfoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionSequenceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleEditTextFocus(binding.jobOrderName);
        progressDialog = loadingProgressDialog(getContext());
        observeGettingProductionSequence();
        clearInputLayoutError(binding.jobOrderName);
        addTextWatcher();
        setUpRecyclerView();
        attachListeners();
        selectedsequence = new ArrayList<>();
        recyclerView = binding.defectqtnRv;
    }



    private void addTextWatcher() {
        binding.jobOrderName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.jobOrderName.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.jobOrderName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.jobOrderName.setError(null);
            }
        });
    }

    private void observeGettingProductionSequence() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }





    private void setUpRecyclerView() {
         productionsequenceresponse = new ArrayList<>();
        adapter = new productionsequenceadapter(productionsequenceresponse,this);
        binding.defectqtnRv.setAdapter(adapter);
        binding.defectqtnRv.setNestedScrollingEnabled(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.defectqtnRv.setLayoutManager(manager);



    }
    private void attachListeners() {
        binding.firstloadingBtn.setOnClickListener(v->{
            if (clickedPpr!=null) {
                if (clickedPpr.getAvailableloadingQty()==Integer.parseInt(childQty)) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(PPR_KEY, clickedPpr);
                    Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_machineLoadingFragment, bundle);
                } else {
                    warningDialog(getContext(),getString(R.string.the_child_calculated_from_scanned_raw_material_must_be_equal_ppr_quantity_please_contact_back_office));
                }
            } else
                warningDialog(getContext(),getString(R.string.please_select_1_production_sequence));

        });
        viewModel.getProductionsequenceResponse().observe(getViewLifecycleOwner(), apiResponse->{
            if (apiResponse!=null) {
                String statusMessage = apiResponse.getResponseStatus().getStatusMessage();
                List<Ppr> pprList = apiResponse.getData();
                if (apiResponse.getResponseStatus().getIsSuccess()){
                    if (pprList.isEmpty())
                        binding.jobOrderName.setError(getString(R.string.no_ppr_list_for_this_job_order_name));
                    adapter.setPprList(pprList);// today 23/11
                    adapter.notifyDataSetChanged();
                } else
                    binding.jobOrderName.setError(statusMessage);

            }
        });


    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                hideKeyboard(getActivity());
                String scannedText = barcodeReader.scannedData(barcodeReadEvent);
                String[] scannedTextSubStrings = scannedText.split(",");
                if (scannedTextSubStrings.length==3) {
                    childQty = scannedTextSubStrings[1];
                    Log.d(TAG, "scannedText " + scannedTextSubStrings[0]);
                    if (scannedText.isEmpty())
                        binding.jobOrderName.setError(getString(R.string.please_enter_or_scan_a_valid_job_order_name));
                    else {
                        binding.jobOrderName.getEditText().setText(scannedTextSubStrings[0]);
                        viewModel.getProductionsequence(scannedTextSubStrings[0]);
                    }
                } else binding.jobOrderName.setError(getString(R.string.please_enter_or_scan_a_valid_barcode));
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
        barcodeReader.onTrigger(triggerStateChangeEvent);
    }
    @Override
    public void onResume() {
        super.onResume();
       barcodeReader.onResume();
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Navigation.findNavController(getView()).popBackStack(R.id.mainmachineloading,true);
                   // Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_mainmachineLoadingFragment);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }

    @Override
    public void onCheckedChanged(Ppr item) {
        Log.d("======id",item.getOperationEnName().toString());
            clickedPpr = item;
    }


    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                hideKeyboard(getActivity());
                String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                String[] scannedTextSubStrings = scannedText.split(",");
                if (scannedTextSubStrings.length==3) {
                    childQty = scannedTextSubStrings[1];
                    Log.d(TAG, "scannedText " + scannedTextSubStrings[0]);
//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){
                    if (scannedText.isEmpty())
                        binding.jobOrderName.setError(getString(R.string.please_enter_or_scan_a_valid_job_order_name));
                    else {
                        binding.jobOrderName.getEditText().setText(scannedTextSubStrings[0]);
                        viewModel.getProductionsequence(scannedTextSubStrings[0]);
                    }
                } else binding.jobOrderName.setError(getString(R.string.please_enter_or_scan_a_valid_barcode));
            }
        });
    }
}