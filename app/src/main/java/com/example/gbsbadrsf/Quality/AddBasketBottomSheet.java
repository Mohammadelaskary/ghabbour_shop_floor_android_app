package com.example.gbsbadrsf.Quality;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.BasketCodeBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

public class AddBasketBottomSheet extends BottomSheetDialog implements BarcodeReadListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {
    private Activity activity;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private OnBasketCodeScanned onBasketCodeScanned;
    public AddBasketBottomSheet(@NonNull Context context,Activity activity,OnBasketCodeScanned onBasketCodeScanned) {
        super(context);
        this.activity=activity;
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        this.onBasketCodeScanned = onBasketCodeScanned;
    }
    private BasketCodeBottomSheetBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BasketCodeBottomSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.defectedRejectedBasketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.defectedRejectedBasketCode.getEditText().getText().toString().trim();
                    onBasketCodeScanned.onBasketCodeScanned(basketCode);
                    barCodeReader.onPause();
                    barCodeReaderInterMec.onPause();
                    binding.defectedRejectedBasketCode.getEditText().setText("");
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        handleEditTextFocus(binding.defectedRejectedBasketCode);

    }
    private String basketCode;

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;

    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        activity.runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.defectedRejectedBasketCode.getEditText().setText(scannedText);
            onBasketCodeScanned.onBasketCodeScanned(scannedText);
            barCodeReader.onPause();
            barCodeReaderInterMec.onPause();
            dismiss();
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
            binding.defectedRejectedBasketCode.getEditText().setText(scannedText);
            onBasketCodeScanned.onBasketCodeScanned(scannedText);
            barCodeReader.onPause();
            barCodeReaderInterMec.onPause();
            dismiss();
        });
    }
    public interface OnBasketCodeScanned{
        void onBasketCodeScanned(String basketCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.defectedRejectedBasketCode.getEditText().setText(basketCode);
        barCodeReader.onResume();
    }
}
