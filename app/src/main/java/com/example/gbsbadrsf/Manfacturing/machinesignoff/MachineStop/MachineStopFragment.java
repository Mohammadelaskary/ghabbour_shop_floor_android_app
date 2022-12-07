package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineHoldFragment.MACHINE_CODE;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.FragmentMachineStopBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class MachineStopFragment extends Fragment implements MachineHoldBottomSheet.OnMachineHoldBottomSheetSaveClicked, MachineTransferBottomSheet.OnMachineTransferBottomSheetSaveClicked, View.OnClickListener, BarcodeReadListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    public static final String MACHINE_DATA = "machine_data";
    public static final String STOP_REASONS = "stop_reasons";
    private MachineStopViewModel viewModel;

    public static MachineStopFragment newInstance() {
        return new MachineStopFragment();
    }
    private FragmentMachineStopBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMachineStopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private MachineTransferBottomSheet machineTransferBottomSheet;
    private MachineHoldBottomSheet machineHoldBottomSheet;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MachineStopViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        setUpMachineTransferBottomSheet();

        machineHoldBottomSheet = new MachineHoldBottomSheet(getContext(),getActivity(),this,loadingQty);
        progressDialog = loadingProgressDialog(getContext());
    }

    private void setUpMachineTransferBottomSheet() {
        machineTransferBottomSheet = new MachineTransferBottomSheet(getContext(),getActivity(),this);
        machineTransferBottomSheet.setOnDismissListener(dialog -> barCodeReader.onResume());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.machineHold.setOnClickListener(this);
        binding.transferMachine.setOnClickListener(this);
        handleEditTextFocus(binding.machinecodeEdt);
        viewModel.getStopReasons();
        observeGettingStopReason();
        observeGettingMachineData();
        observeMachineTransfer();
        observeStatus();
        binding.machinecodeEdt.getEditText().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                String machineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();
                if (!machineCode.isEmpty()) {
                    viewModel.getMachineData(machineCode);
                    binding.machinecodeEdt.setError(null);
                } else {
                    binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code_and_press_enter));
                }
                return true;
            }
            return false;
        });
    }

    private void observeMachineTransfer() {
        viewModel.getMachineTransfer().observe(getViewLifecycleOwner(),apiResponseTransferMachineLoading -> {
            if (apiResponseTransferMachineLoading!=null){
                String statusMessage = apiResponseTransferMachineLoading.getResponseStatus().getStatusMessage();
                if (apiResponseTransferMachineLoading.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(this);
                    machineTransferBottomSheet.dismiss();
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
                    progressDialog.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.hide();
                    break;
            }
        });
    }
    ArrayList<StopReason> stopReasons = new ArrayList<>();
    private void observeGettingStopReason() {
        viewModel.getStoppageReasons().observe(getViewLifecycleOwner(),apiResponseGetStopageReasonsList -> {
            if (apiResponseGetStopageReasonsList!=null){
                String statusMessage = apiResponseGetStopageReasonsList.getResponseStatus().getStatusMessage();
                if (apiResponseGetStopageReasonsList.getResponseStatus().getIsSuccess()){
                    stopReasons = (ArrayList<StopReason>) apiResponseGetStopageReasonsList.getStoppagesReasonsList();
                    machineTransferBottomSheet.setStopReasons(apiResponseGetStopageReasonsList.getStoppagesReasonsList());
                } else {
                    warningDialog(getContext(),statusMessage);
                }
            } else
                warningDialog(getContext(), getString(R.string.error_in_getting_data));
        });
    }
    private int loadingQty;
    private List<String> relatedMachine = new ArrayList<>();
    private MachineData machineData;
    private void observeGettingMachineData() {
        viewModel.getMachineData().observe(getViewLifecycleOwner(),apiResponseGetMachineData -> {
            if (apiResponseGetMachineData!=null){
                String statusMessage = apiResponseGetMachineData.getResponseStatus().getStatusMessage();
                if (apiResponseGetMachineData.getResponseStatus().getIsSuccess()){
                    machineData = apiResponseGetMachineData.getMachineData();
                    fillData(apiResponseGetMachineData.getMachineData());
                    loadingQty = apiResponseGetMachineData.getMachineData().getLoadingQty();
                    relatedMachine = apiResponseGetMachineData.getRelatedMachines();
                    machineTransferBottomSheet.setRelatedMachines(relatedMachine);
                    binding.dataLayout.setVisibility(View.VISIBLE);
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

    private void fillData(MachineData machineData) {
        binding.childesc.setText(machineData.getChildDescription());
        binding.jobordernum.setText(machineData.getJobOrderName());
        binding.Joborderqtn.setText(String.valueOf(machineData.getJobOrderQty()));
        binding.operation.setText(machineData.getOperationEnName());
        binding.loadingQty.setText(String.valueOf(machineData.getLoadingQty()));
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.machinecodeEdt.getEditText().setText(scannedText);
            viewModel.getMachineData(scannedText);
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
            binding.machinecodeEdt.getEditText().setText(scannedText);
            viewModel.getMachineData(scannedText);
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
            case R.id.machine_hold:
                String machineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putParcelable(MACHINE_DATA,machineData);
                bundle.putParcelableArrayList(STOP_REASONS,stopReasons);
                bundle.putString(MACHINE_CODE,machineCode);
                Navigation.findNavController(v).navigate(R.id.action_fragment_machine_stop_fragment_to_fragment_machine_hold2,bundle);
                break;
            case R.id.transfer_machine:
                if (!relatedMachine.isEmpty()) {
                    barCodeReader.onPause();
                    barCodeReaderInterMec.onPause();
                    machineTransferBottomSheet.show();
                } else {
                    warningDialog(getContext(),getString(R.string.there_is_no_related_machines));
                }
                break;
        }
    }

    @Override
    public void onMachineTransferSaveClicked(String machineCode, int reasonId) {
        String oldMachineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();
        viewModel.transferMachine(oldMachineCode,machineCode,reasonId);
    }

    @Override
    public void onMachineHoldSaveClicked(String okBasketCode, int reasonId, int okQty) {
        String oldMachineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();
        viewModel.machineHold(oldMachineCode,okBasketCode,reasonId,okQty);
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }
}