package com.example.gbsbadrsf.Manfacturing.machineloading;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.BasketInfo.BasketWipData;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class ReloadHoldFragment extends Fragment implements View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener, BarcodeReadListener {

    private ReloadHoldViewModel viewModel;

    public static ReloadHoldFragment newInstance() {
        return new ReloadHoldFragment();
    }
    private com.example.gbsbadrsf.databinding.FragmentReloadHoldBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = com.example.gbsbadrsf.databinding.FragmentReloadHoldBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ReloadHoldViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeBasketData();
        binding.basketcodeEdt.getEditText().requestFocus();
        checkFocusedEditText();
        setUpBasketCodesRecyclerView();
        binding.saveBtn.setOnClickListener(this);
        handleEditTextFocus(binding.basketcodeEdt,binding.diecodeEdt,binding.machinecodeEdt);
        observeMachineReload();
        clearInputLayoutError(binding.basketcodeEdt);
        clearInputLayoutError(binding.machinecodeEdt);
        clearInputLayoutError(binding.diecodeEdt);
        binding.basketcodeEdt.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
                    if (basketCodes.isEmpty()){
                        binding.basketcodeEdt.getEditText().setText(basketCode);
                        viewModel.getBasketData(basketCode);
                    } else {
                        if (relatedBaskets.contains(basketCode)) {
                            if (!basketCodes.contains(basketCode)) {
                                basketCodes.add(basketCode);
                                adapter.notifyDataSetChanged();
                                if (relatedBaskets.size()==basketCodes.size())
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
        });
    }

    private void observeMachineReload() {
        viewModel.getMachineReload().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private BasketCodesAdapter adapter;
    private List<String> basketCodes = new ArrayList<>();
    private List<String> relatedBaskets = new ArrayList<>();
    private void setUpBasketCodesRecyclerView() {
        adapter = new BasketCodesAdapter(basketCodes);
        binding.basketCodes.setAdapter(adapter);
        adapter.setOnBasketCodeRemoved(position -> {
            if (basketCodes.isEmpty()){
                binding.dataLayout.setVisibility(View.GONE);
                binding.basketcodeEdt.getEditText().setText("");
            }
        });
    }

    private void checkFocusedEditText() {
        binding.basketcodeEdt.getEditText().setOnFocusChangeListener((v, hasFocus) -> basketCodeFocused = hasFocus);
        binding.machinecodeEdt.getEditText().setOnFocusChangeListener((v, hasFocus) -> machineCodeFocused = hasFocus);
        binding.diecodeEdt.getEditText().setOnFocusChangeListener((v, hasFocus) -> dieCodeFocused = hasFocus);
    }

    boolean basketCodeFocused,machineCodeFocused,dieCodeFocused;
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketcodeEdt.getEditText().setText(scannedText);
            if (basketCodeFocused){
                if (basketCodes.isEmpty()){
                    viewModel.getBasketData(scannedText);
                } else {
                    if (relatedBaskets.contains(scannedText)) {
                        if (!basketCodes.contains(scannedText)) {
                            basketCodes.add(scannedText);
                            adapter.notifyDataSetChanged();
                            if (relatedBaskets.size()==basketCodes.size())
                                binding.machinecodeEdt.getEditText().requestFocus();
                        } else {
                            binding.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                        }
                    } else {
                        binding.basketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    }
                }
            } else if (machineCodeFocused){
                binding.machinecodeEdt.getEditText().setText(scannedText);
            } else if (dieCodeFocused){
                binding.diecodeEdt.getEditText().setText(scannedText);
            }
        });
    }
    private String machineCode,dieCode;
    int loadingQty;
    private void observeBasketData() {
        viewModel.getBasketData().observe(getViewLifecycleOwner(), apiResponseGetMachineData -> {
            if (apiResponseGetMachineData!=null){
                String statusMessage = apiResponseGetMachineData.getResponseStatus().getStatusMessage();
                if (apiResponseGetMachineData.getResponseStatus().getIsSuccess()){
                    fillData(apiResponseGetMachineData.getBasketWipData());
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    machineCode = apiResponseGetMachineData.getBasketWipData().getMachineCode();
                    dieCode = apiResponseGetMachineData.getBasketWipData().getDieCode();
                    Log.d(TAG, "observeBasketData: "+dieCode);
                    loadingQty= apiResponseGetMachineData.getBasketWipData().getBasketQty();
                    relatedBaskets = apiResponseGetMachineData.getRelatedBaskets();
                    String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
                    basketCodes.add(basketCode);
                    if (relatedBaskets.size()==basketCodes.size())
                        binding.machinecodeEdt.getEditText().requestFocus();
                } else {
                    binding.basketcodeEdt.setError(statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                    basketCodes.clear();
                }
            } else {
                warningDialog(getContext(), getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
                basketCodes.clear();
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void fillData(BasketWipData basketWipData) {
        binding.childesc.setText(basketWipData.getChildDescription());
        binding.jobordernum.setText(basketWipData.getJobOrderName());
        binding.Joborderqtn.setText(String.valueOf(basketWipData.getJobOrderQty()));
        binding.operation.setText(basketWipData.getOperationEnName());
        binding.loadingQty.setText(String.valueOf(basketWipData.getBasketQty()));
//        binding.machinecodeEdt.getEditText().requestFocus();
//        binding.diecodeEdt.getEditText().setEnabled(dieCode != null && !dieCode.isEmpty());
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
            binding.basketcodeEdt.getEditText().setText(scannedText);
            if (basketCodeFocused){
                if (basketCodes.isEmpty()){
                    viewModel.getBasketData(scannedText);
                } else {
                    if (relatedBaskets.contains(scannedText)) {
                        if (!basketCodes.contains(scannedText)) {
                            basketCodes.add(scannedText);
                            adapter.notifyDataSetChanged();
                        } else {
                            binding.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                        }
                    } else {
                        binding.basketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    }
                }
            } else if (machineCodeFocused){
                binding.machinecodeEdt.getEditText().setText(scannedText);
                if (dieCode!=null&&!dieCode.isEmpty())
                    binding.diecodeEdt.getEditText().requestFocus();
            } else if (dieCodeFocused){
                binding.diecodeEdt.getEditText().setText(scannedText);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_btn:
                String machineCodeScanned = binding.machinecodeEdt.getEditText().getText().toString().trim();
                if (!machineCode.isEmpty()&&machineCodeScanned.equals(machineCode)){
                    if (!basketCodes.isEmpty()){
                        MachineReloadData data;
                        if (dieCode==null||dieCode.isEmpty()) {
                            data = new MachineReloadData(
                                    USER_ID,
                                    DEVICE_SERIAL_NO,
                                    machineCode,
                                    loadingQty,
                                    basketCodes
                            );
                        } else {
                            data = new MachineReloadData(
                                    USER_ID,
                                    DEVICE_SERIAL_NO,
                                    machineCode,
                                    dieCode,
                                    loadingQty,
                                    basketCodes,
                                    LocaleHelper.getLanguage(getContext())
                            );
                        }
                        viewModel.machineReload(data);
                    } else {
                        binding.basketcodeEdt.setError(getString(R.string.please_add_at_least_1_basket));
                    }
                } else {
                    binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code));
                }
                break;
        }
    }
}