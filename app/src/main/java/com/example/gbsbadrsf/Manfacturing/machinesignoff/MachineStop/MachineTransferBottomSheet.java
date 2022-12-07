package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.MachineTransactionBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.List;

public class MachineTransferBottomSheet extends BottomSheetDialog implements View.OnClickListener, BarcodeReadListener, BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {
    private List<StopReason> stopReasons;
    private Activity activity;
    private OnMachineTransferBottomSheetSaveClicked onMachineTransferBottomSheetSaveClicked;
    private List<String> relatedMachines;
    public MachineTransferBottomSheet(@NonNull Context context, Activity activity,OnMachineTransferBottomSheetSaveClicked onMachineTransferBottomSheetSaveClicked) {
        super(context);
        this.activity =activity;
        this.onMachineTransferBottomSheetSaveClicked = onMachineTransferBottomSheetSaveClicked;
    }

    public void setRelatedMachines(List<String> relatedMachines) {
        this.relatedMachines = relatedMachines;
    }

    public void setStopReasons(List<StopReason> stopReasons) {
        this.stopReasons = stopReasons;
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, stopReasons);
    }
    private MachineTransactionBottomSheetBinding binding;
    private ArrayAdapter<StopReason> adapter;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MachineTransactionBottomSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        handleEditTextFocus(binding.machinecodeEdt);
        setUpStopReasonSpinner();
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        barCodeReader.onResume();
        binding.saveBtn.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);
        clearInputLayoutError(binding.machinecodeEdt);
        clearInputLayoutError(binding.stopReason);
    }
    private int selectedReasonId = -1 ;
    private void setUpStopReasonSpinner() {
        binding.stopReasonSpinner.setAdapter(adapter);
        binding.stopReasonSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedReasonId = stopReasons.get(position).getReasonId();
        });
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        activity.runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (relatedMachines.contains(scannedText)) {
                binding.machinecodeEdt.getEditText().setText(scannedText);
            } else
                binding.machinecodeEdt.setError(activity.getString(R.string.the_scanned_machine_is_not_related_to_the_original_machine));
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
        activity.runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            if (relatedMachines.contains(scannedText)) {
                binding.machinecodeEdt.getEditText().setText(scannedText);
            } else
                binding.machinecodeEdt.setError(activity.getString(R.string.the_scanned_machine_is_not_related_to_the_original_machine));
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.save_btn:
                String machineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();
                if (!machineCode.isEmpty()){
                    if (selectedReasonId!=-1){
                        onMachineTransferBottomSheetSaveClicked.onMachineTransferSaveClicked(machineCode,selectedReasonId);
                    } else {
                        binding.stopReason.setError(activity.getString(R.string.please_select_stop_reason));
                    }
                } else {
                    binding.machinecodeEdt.setError(getContext().getString(R.string.please_scan_or_enter_a_valid_machine_code));
                }
                break;
        }
    }

    interface OnMachineTransferBottomSheetSaveClicked{
        void onMachineTransferSaveClicked(String machineCode,int reasonId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }


}
