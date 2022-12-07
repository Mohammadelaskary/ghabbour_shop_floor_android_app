package com.example.gbsbadrsf.welding.machineloadingwe;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.WeldingSignInBasketsDialog;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingweBinding;
import com.example.gbsbadrsf.weldingsequence.InfoForSelectedStationViewModel;
import com.example.gbsbadrsf.weldingsequence.StationSignIn;
import com.example.gbsbadrsf.weldingsequence.WeldingSequence;
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


public class MachineloadingweFragment extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, BarcodeReadListener {
    FragmentMachineloadingweBinding binding;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

//    @Inject
//    ViewModelProviderFactory providerFactory;
    InfoForSelectedStationViewModel infoForSelectedStationViewModel;
    SaveweldingViewModel viewModel;
    private ResponseStatus responseStatus;
    List<String> basketCodes = new ArrayList<>();
    List<String> addedBasketCodes = new ArrayList<>();
    BasketCodesAdapter adapter;
    private ProgressDialog progressDialog;
    private WeldingSignInBasketsDialog dialog;

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
        binding = FragmentMachineloadingweBinding.inflate(inflater, container, false);
//        viewModel = ViewModelProviders.of(this, providerFactory).get(SaveweldingViewModel.class);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SaveweldingViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        infoForSelectedStationViewModel = WeldingSequence.infoForSelectedStationViewModel;
        dialog = new WeldingSignInBasketsDialog(getContext());

//        initObjects();
        subscribeRequest();
        getdata();
        addTextWatcher();
        setUpRecyclerView();
        handleEditTextFocus(binding.childbasketcodeEdt,binding.stationcodeEdt);
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationCode = binding.stationcodeEdt.getEditText().getText().toString().trim();
                if (!stationCode.isEmpty()) {
                    if (!addedBasketCodes.isEmpty()) {
                        if (stationCode.equals(currentStationCode)) {
                            if (addedBasketCodes.size()==basketCodes.size()) {
                                StationSignIn stationSignIn = new StationSignIn(USER_ID,DEVICE_SERIAL_NO, ppr.getLoadingSequenceID(), ppr.getProductionStationCode(), ppr.getLoadingQty(),addedBasketCodes, LocaleHelper.getLanguage(getContext()));
                                viewModel.saveweldingloading(stationSignIn);
                            } else {
                                binding.childbasketcodeEdt.setError(getString(R.string.please_scan_or_enter_all_baskets));
                                binding.childbasketcodeEdt.getEditText().requestFocus();
                            }
                        } else {
                            binding.stationcodeEdt.setError(getString(R.string.scanned_station_code_doesnt_match_please_scan_the_correct_station_code));
                            binding.stationcodeEdt.getEditText().requestFocus();
                        }
                    } else {
                        binding.childbasketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_child_basket_code));
                        binding.childbasketcodeEdt.getEditText().requestFocus();
                    }
                } else {
                    binding.stationcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_station_code));
                    binding.stationcodeEdt.getEditText().requestFocus();
                }

            }
        });
        binding.signInBaskets.setOnClickListener(v-> {
            dialog.setAddedBaskets(addedBasketCodes);
            dialog.show();
        });

    }

    private void setUpRecyclerView() {
        adapter = new BasketCodesAdapter(addedBasketCodes);
        binding.baskets.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.baskets.setLayoutManager(manager);
    }

    private void addTextWatcher() {
        clearInputLayoutError(binding.stationcodeEdt);
        clearInputLayoutError(binding.childbasketcodeEdt);
        binding.childbasketcodeEdt.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.childbasketcodeEdt.getEditText().getText().toString().trim();
                    if (!basketCodes.contains(basketCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    if (addedBasketCodes.contains(basketCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.basket_added_previously));
                    if (basketCodes.contains(basketCode)&&!addedBasketCodes.contains(basketCode)){
                        addedBasketCodes.add(basketCode);
                        adapter.notifyItemInserted(addedBasketCodes.size());
                        binding.childbasketcodeEdt.getEditText().setText("");
                    }

                    return true;
                }
                return false;
            }
        });
    }


//    private void initObjects() {
//        binding.parentcode.setText(getArguments().getString("parentcode"));
//        binding.parentdesc.setText(getArguments().getString("parentdesc"));
//        binding.operation.setText(getArguments().getString("operationrname"));
//        binding.loadingqtns.setText(getArguments().getString("loadingqty"));
//        binding.childqtn.setText(getArguments().getString("basketcode"));
//    }
    PprWelding  ppr;
    String currentStationCode;
    public void getdata() {
        infoForSelectedStationViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                ppr = response.getData();
                binding.jobOrderData.jobordernum.setText(ppr.getJobOrderName());
                binding.jobOrderData.Joborderqtn.setText(ppr.getJobOrderQty().toString());
                binding.parentdesc.setText(ppr.getParentDescription());
                binding.operation.setText(ppr.getOperationEnName());
                binding.pprQty.setText(String.valueOf(ppr.getPprQty()));
                String signedOffText = ppr.getIncrementalSignOutQty()+" / "+ppr.getPprQty();
                binding.signedOffQty.setText(signedOffText);
                binding.signOffQty.setText(String.valueOf(ppr.getSignOutQty()));
                currentStationCode = ppr.getProductionStationCode();
//                binding.childqtn.setText(response.getBaskets().get(0).getBasketCode());
                binding.signInBaskets.setEnabled(true);
                dialog.setBaskets(response.getBaskets());
                for(Baskets baskets: response.getBaskets()){
                    basketCodes.add(baskets.getBasketCode());
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));

        });
    }

    private void subscribeRequest() {
        viewModel.getSaveFirstLoadingResponse().observe(getViewLifecycleOwner(), response -> {
//            switch (typesofsavewelding)
//            {
//                case savedsucessfull: {
//                    Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();
//                    back(MachineloadingweFragment.this);
//                }break;
//                case wrongjoborderorparentid:
//                    warningDialog(getContext(),"Wrong job order or parent id");
//                    break;
//
//                case wrongbasketcode:
//                    binding.childbasketcodeEdt.setError("Wrong basket code");
//                    break;
//                case server:
//                    warningDialog(getContext(),"There was a server side failure while respond to this transaction");
//                    break;
//            }
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess() ) {
                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();
                    back(MachineloadingweFragment.this);
                } else {
                    warningDialog(getContext(), statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_saving_data));
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
                String scannedCode = barCodeReader.scannedData(barcodeReadEvent);
                if (binding.stationcodeNewedttxt.isFocused()) {
                    binding.stationcodeNewedttxt.setText(scannedCode);
                    binding.childbasketcodeEdt.getEditText().requestFocus();
                }
                else if (binding.childbasketcodeNewedttxt.isFocused()){
                    binding.childbasketcodeNewedttxt.setText(scannedCode);
                    if (!basketCodes.contains(scannedCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    if (addedBasketCodes.contains(scannedCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.basket_added_previously));
                    if (basketCodes.contains(scannedCode)&&!addedBasketCodes.contains(scannedCode)){
                        addedBasketCodes.add(scannedCode);
                        adapter.notifyItemInserted(addedBasketCodes.size());
                        binding.childbasketcodeEdt.getEditText().setText("");
                        binding.childbasketcodeEdt.getEditText().requestFocus();
                    }

                }


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
            changeTitle(getString(R.string.welding),(MainActivity) getActivity());
            binding.stationcodeEdt.getEditText().requestFocus();
//            getView().setFocusableInTouchMode(true);
//            getView().requestFocus();
//            getView().setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                        // handle back button's click listener
//                        Navigation.findNavController(getView()).popBackStack(R.id.weldingSequence,true);
//
//
//                        return true;
//                    }
//                    return false;
//                }
//            });
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
                String scannedCode = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                if (binding.stationcodeNewedttxt.isFocused()) {
                    binding.stationcodeNewedttxt.setText(scannedCode);
                    binding.childbasketcodeEdt.getEditText().requestFocus();
                }
                else if (binding.childbasketcodeNewedttxt.isFocused()){
                    if (!basketCodes.contains(scannedCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    if (addedBasketCodes.contains(scannedCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.basket_added_previously));
                    if (basketCodes.contains(scannedCode)&&!addedBasketCodes.contains(scannedCode)){
                        addedBasketCodes.add(scannedCode);
                        adapter.notifyItemInserted(addedBasketCodes.size());
                        binding.childbasketcodeEdt.getEditText().setText("");
                        binding.childbasketcodeEdt.getEditText().requestFocus();
                    }

                }


            }
        });
    }
}