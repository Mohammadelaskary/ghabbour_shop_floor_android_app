package com.example.gbsbadrsf.Manfacturing.machineloading;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.hideKeyboard;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Quality.Data.LastMoveManufacturing;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentContinueLoadingBinding;
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


public class ContinueLoading extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, BarcodeReadListener {

//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentContinueLoadingBinding binding;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private ContinueLoadingViewModel viewModel;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_continue_loading, container, false);
        binding = FragmentContinueLoadingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        continueLoadingViewModel = ViewModelProviders.of(this, providerFactory).get(ContinueLoadingViewModel.class);
        viewModel = new ViewModelProvider(this).get(ContinueLoadingViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        setUpBasketCodesRecyclerView();
        handleEditTextFocus(binding.basketcodeEdt,binding.diecodeEdt,binding.machinecodeEdt);
        binding.basketcodeEdt.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
                    binding.basketcodeEdt.getEditText().setText(basketCode);
                    if (basketCodes.isEmpty()){
                        viewModel.getbasketedata(basketCode);
                    } else {
                        if (relatedBasketList.contains(basketCode)) {
                            if (!basketCodes.contains(basketCode)) {
                                basketCodes.add(basketCode);
                                adapter.notifyDataSetChanged();

                                if (relatedBasketList.size()==basketCodes.size())
                                    binding.machinecodeEdt.getEditText().requestFocus();
                            } else {
                                binding.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                            }
                        } else {
                            binding.basketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                        }
                    }
                    return true;
                }
                return false;
            }
        }); //{
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////                continueLoadingViewModel.getbasketedata("1", "S123", fragmentContinueLoadingBinding.newbasketcodeEdt.getText().toString());
////
////            }
////
////
////            @Override
////            public void afterTextChanged(Editable s) {
////
////
////            }
//
//        });

        getdata();
        // initViews();
        addTextWatchers();
//        subscribeRequest();
        observeGettingData();
        observeSavingData();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                String dieCode     = binding.diecodeEdt.getEditText().getText().toString().trim();
//                String loadingQty  = binding.loadingQty.getEditText().getText().toString().trim();
                Log.d("=====qty",qty+"");
                if (!basketCodes.isEmpty()){
                    if (!machineCode.isEmpty()){
//                        if (!(dieCode.isEmpty()&&requiredDieId!=0)){
//                            if (basketCodes.size()==relatedBasketList.size()){
                                ContinueLoadingData data;
                                if (!dieCode.isEmpty())
                                    data = new ContinueLoadingData(
                                            USER_ID,
                                            DEVICE_SERIAL_NO,
                                            machineCode,
                                            dieCode,
                                            qty,
                                            basketCodes,
                                            LocaleHelper.getLanguage(getContext())
                                    );
                                else
                                    data = new ContinueLoadingData(
                                            USER_ID,
                                            DEVICE_SERIAL_NO,
                                            machineCode,
                                            qty,
                                            basketCodes,
                                            LocaleHelper.getLanguage(getContext())
                                    );
                                viewModel.savecontinueloading(data);
//                            } else
//                                binding.basketcodeEdt.setError(getString(R.string.please_scan_all_related_baskets));
//                        } else
//                            binding.diecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_die_code));
                    } else
                        binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code));
                } else
                    binding.basketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));


//                if (!loadingQty.isEmpty()){
//                    if (containsOnlyDigits(loadingQty)){
//                        if (Integer.parseInt(loadingQty)>qty||Integer.parseInt(loadingQty)<=0)
//                            binding.loadingQty.setError("Loading Quantity must be equal or less than basket Quantity and bigger than 0!");
//                    } else binding.loadingQty.setError("Loading Quantity must contain only digits!");
//
//                } else
//                    binding.loadingQty.setError("Please set loading quantity!");
            }
        });


    }

    private void setUpBasketCodesRecyclerView() {
        adapter = new BasketCodesAdapter(basketCodes);
        binding.basketCodes.setAdapter(adapter);
        adapter.setOnBasketCodeRemoved(position ->{
            if (basketCodes.isEmpty()){
                binding.dataLayout.setVisibility(View.GONE);
                binding.basketcodeEdt.getEditText().setText("");
            }
        });
    }

    private void observeSavingData() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), responseStatus -> {
            if (responseStatus!=null) {
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    showSuccessAlerter(statusMessage, getActivity());
                    back(ContinueLoading.this);
                } else {
                    switch (statusMessage) {
                        case "Saving data successfully":
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                            showSuccessAlerter(statusMessage, getActivity());
                            back(ContinueLoading.this);
                            break;
                        case "The machine has already been used":
                        case "Wrong machine code":
                            binding.machinecodeEdt.setError(statusMessage);
                            binding.machinecodeEdt.getEditText().requestFocus();
                            break;
                        case "Wrong die code for this child":
                            binding.diecodeEdt.setError(statusMessage);
                            binding.diecodeEdt.getEditText().requestFocus();
                            break;
                        case "Wrong basket code":
                            binding.basketcodeEdt.setError(statusMessage);
                            binding.basketcodeEdt.getEditText().requestFocus();
                            break;
                        default:
                            warningDialog(getContext(), statusMessage);
                            break;
                    }
                }
            }
        });
    }


    private void addTextWatchers() {
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
        binding.basketcodeEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.basketcodeEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.basketcodeEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.basketcodeEdt.setError(null);
            }
        });
    }

    private void observeGettingData() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status.equals(Status.LOADING)) {
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }
    String childCode;
    int qty;
    int requiredDieId = 0;
    private List<String> basketCodes = new ArrayList<>();
    private List<String> relatedBasketList = new ArrayList<>();
    private BasketCodesAdapter adapter;
    private LastMoveManufacturingBasketInfo basketInfo;
    public void getdata() {
        viewModel.getLastmanfacturingbasketinfo().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getData()!=null) {
                    childCode = response.getData().getChildCode();
                    basketInfo = response.getData();
                    binding.diecodeEdt.setEnabled(response.getData().getDieId() != 0);
                    qty = response.getData().getQty();
                    binding.childesc.setText(response.getData().getChildDescription());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    binding.Joborderqtn.setText(response.getData().getJobOrderQty().toString());
                    binding.operation.setText(response.getData().getNextOperationName());
                    binding.loadingQty.setText(String.valueOf(qty));
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    relatedBasketList = response.getData().getRelatedBasketCodes();
                    if (response.getData().getDieId()!=null)
                        requiredDieId = response.getData().getDieId();
                    if (requiredDieId==0)
                        binding.diecodeEdt.getEditText().setEnabled(false);
                    String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
                    basketCodes.add(basketCode);
                    if (relatedBasketList.size()==basketCodes.size())
                        binding.machinecodeEdt.getEditText().requestFocus();
                } else {
                    binding.basketcodeEdt.setError(statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                    qty=0;
                    basketCodes.clear();
                }

            } else {
                binding.dataLayout.setVisibility(View.GONE);
                qty=0;
                warningDialog(getContext(), getString(R.string.error_in_getting_data));
                basketCodes.clear();
            }
            adapter.notifyDataSetChanged();
        });
    }
    private void subscribeRequest() {
        viewModel.getBasketcases().observe(getViewLifecycleOwner(), new Observer<Basketcases>() {
            @Override
            public void onChanged(Basketcases basketcases) {
                switch (basketcases) {
                    case datagettingsuccesfully:
                       // Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        binding.machinecodeEdt.getEditText().requestFocus();

                        break;

                    case wrongbasketcode:

//                        Toast.makeText(getContext(), "Wrong basket code", Toast.LENGTH_SHORT).show();
                        binding.basketcodeEdt.setError("Wrong basket code");
                       binding.dataLayout.setVisibility(View.GONE);
                        break;
                    case
                            Savingdatasuccessfully:

                        Toast.makeText(getContext(), "Saving Data successfully", Toast.LENGTH_SHORT).show();
                         back(ContinueLoading.this);
                        break;
                        case wrongbasket:

//                        Toast.makeText(getContext(), "Wrong basket ", Toast.LENGTH_SHORT).show();
                            binding.basketcodeEdt.setError("Wrong basket");
                            binding.dataLayout.setVisibility(View.GONE);
                        break;
                        case wrongdie:
                            binding.diecodeEdt.setError("Wrong die code");
                        break;
                        case machinealreadyused:
                            binding.machinecodeEdt.setError("The machine has already been used");
                        break;
                    case wrongmachinecode:
                        binding.machinecodeEdt.setError("Wrong machine code");
                        break;



                }
            }
        });

    }
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (binding.machinecodeNewedttxt.isFocused()) {

                    binding.machinecodeNewedttxt.setText(String.valueOf(barCodeReader.scannedData(barcodeReadEvent)));
                    if (basketInfo.getDieId()!=0)
                        binding.diecodeEdt.getEditText().requestFocus();
                }
                else if (binding.newdiecodeEdt.isFocused()){
                    binding.newdiecodeEdt.setText(String.valueOf(barCodeReader.scannedData(barcodeReadEvent)));

                }
                else if (binding.basketcodeEdt.getEditText().isFocused()){
                    String scannedText = barCodeReader.scannedData(barcodeReadEvent);
                    binding.basketcodeEdt.getEditText().setText(scannedText);
                    if (!basketCodes.isEmpty()) {
                        if (relatedBasketList.contains(scannedText)) {
                            if (!basketCodes.contains(scannedText)) {
                                basketCodes.add(scannedText);
                                adapter.notifyDataSetChanged();
                                Log.d(TAG, "basketCodes: "+basketCodes.size());
                                Log.d(TAG, "basketRelated: "+relatedBasketList.size());
                                if (relatedBasketList.size()==basketCodes.size())
                                    binding.machinecodeEdt.getEditText().requestFocus();
                            } else {
                                binding.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                            }
                        } else {
                            binding.basketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                        }
                    } else
                        viewModel.getbasketedata( binding.basketcodeEdt.getEditText().getText().toString());
                }
                hideKeyboard(getActivity());
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
        binding.basketcodeEdt.getEditText().requestFocus();
        barCodeReader.onResume();
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
       barCodeReader.onPause();
       barCodeReaderInterMec.onPause();
    }


    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            if (binding.machinecodeNewedttxt.isFocused()) {

                binding.machinecodeNewedttxt.setText(String.valueOf(barCodeReaderInterMec.scannedData(barcodeReadEvent)));
                if (basketInfo.getDieId()!=0)
                    binding.diecodeEdt.getEditText().requestFocus();
            }
            else if (binding.newdiecodeEdt.isFocused()){
                binding.newdiecodeEdt.setText(String.valueOf(barCodeReaderInterMec.scannedData(barcodeReadEvent)));

            }
            else if (binding.basketcodeEdt.getEditText().isFocused()){
                String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                binding.basketcodeEdt.getEditText().setText(scannedText);
                if (!basketCodes.isEmpty()) {
                    if (relatedBasketList.contains(scannedText)) {
                        if (!basketCodes.contains(scannedText)) {
                            basketCodes.add(scannedText);
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "basketCodes: "+basketCodes.size());
                            Log.d(TAG, "basketRelated: "+relatedBasketList.size());
                            if (relatedBasketList.size()==basketCodes.size())
                                binding.machinecodeEdt.getEditText().requestFocus();
                        } else {
                            binding.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                        }
                    } else {
                        binding.basketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    }
                } else
                    viewModel.getbasketedata( binding.basketcodeEdt.getEditText().getText().toString());
            }
            hideKeyboard(getActivity());
        });
    }
}