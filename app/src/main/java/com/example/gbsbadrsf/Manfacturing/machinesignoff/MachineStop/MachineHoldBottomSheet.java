package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.MachineHoldBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.List;

public class MachineHoldBottomSheet extends BottomSheetDialog implements View.OnClickListener, BarcodeReadListener, BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {
    private List<StopReason> stopReasons;
    private Activity activity;
    private MachineHoldBottomSheet.OnMachineHoldBottomSheetSaveClicked onMachineHoldBottomSheetSaveClicked;
    private int loadingQty;
    public MachineHoldBottomSheet(@NonNull Context context, Activity activity, MachineTransferBottomSheet.OnMachineTransferBottomSheetSaveClicked onMachineTransferBottomSheetSaveClicked,int loadingQty) {
        super(context);
        this.activity =activity;
        this.onMachineHoldBottomSheetSaveClicked = onMachineHoldBottomSheetSaveClicked;
        this.loadingQty = loadingQty;
    }

    public void setStopReasons(List<StopReason> stopReasons) {
        this.stopReasons = stopReasons;
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, stopReasons);
    }
    private MachineHoldBottomSheetBinding binding;
    private ArrayAdapter<StopReason> adapter;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MachineHoldBottomSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUpStopReasonSpinner();
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        handleEditTextFocus(binding.okBasketCode);
        barCodeReader.onResume();
        binding.saveBtn.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);
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
            binding.okBasketCode.getEditText().setText(scannedText);
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
            binding.okBasketCode.getEditText().setText(scannedText);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.save_btn:
                String basketCode = binding.okBasketCode.getEditText().getText().toString().trim();
                String okQty = binding.okQty.getEditText().getText().toString().trim();
                    if (!okQty.isEmpty()) {
                        if (containsOnlyDigits(okQty)) {
                            if (Integer.parseInt(okQty)>0||Integer.parseInt(okQty)<=loadingQty) {
                                if (!basketCode.isEmpty()) {
                                    if (selectedReasonId != -1) {
                                        onMachineHoldBottomSheetSaveClicked.onMachineHoldSaveClicked(basketCode, selectedReasonId, Integer.parseInt(okQty));
                                    } else {
                                        binding.stopReason.setError(activity.getString(R.string.please_select_stop_reason));
                                    }
                                } else {
                                        binding.okBasketCode.setError(getContext().getString(R.string.please_scan_or_enter_a_valid_machine_code));
                                    }
                            } else
                                warningDialog(getContext(),getContext().getString(R.string.please_enter_a_valid_qty));
                        } else
                            warningDialog(getContext(),getContext().getString(R.string.please_enter_a_valid_qty));
                    } else {
                        if (selectedReasonId != -1) {
                            onMachineHoldBottomSheetSaveClicked.onMachineHoldSaveClicked(basketCode, selectedReasonId, Integer.parseInt(okQty));
                        } else {
                            binding.stopReason.setError(activity.getString(R.string.please_select_stop_reason));
                        }
                    }
                break;
        }
    }

    interface OnMachineHoldBottomSheetSaveClicked{
        void onMachineHoldSaveClicked(String okBasketCode,int reasonId,int okQty);
    }

    @Override
    protected void onStop() {
        super.onStop();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }
}
