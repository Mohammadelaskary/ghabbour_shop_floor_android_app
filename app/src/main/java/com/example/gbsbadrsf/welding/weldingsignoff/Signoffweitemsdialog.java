package com.example.gbsbadrsf.welding.weldingsignoff;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.OnBasketRemoved;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.ProductionSignoffAdapter;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.SignoffcustomdialogBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

public class Signoffweitemsdialog extends Dialog implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, OnBasketRemoved {
    private String parentDesc;
    private String loadingQty;
    private OnInputSelected onInputSelected;
    private boolean isBulk;
    Activity activity;
    List<String> basketCodes = new ArrayList<>();
    public Signoffweitemsdialog(@NonNull Context context,String parentDesc,String loadingQty,OnInputSelected onInputSelected,boolean isBulk,List<Basketcodelst> basketList,Activity activity) {
        super(context);
        this.activity = activity;
        this.parentDesc = parentDesc;
        this.loadingQty = loadingQty;
        this.onInputSelected = onInputSelected;
        this.isBulk = isBulk;
        this.basketList = basketList;
        basketCodes.clear();
        for (Basketcodelst basketcodelst1:basketList){
            basketCodes.add(basketcodelst1.getBasketcode());
        }
    }

    @Override
    public void onBasketRemoved(int position) {
        basketList.remove(position);
        basketCodes.remove(position);
        adapter.notifyDataSetChanged();
        if (!isBulk)
            updateViews();
    }


    public interface OnInputSelected{
        // void sendInput(String input);
        void sendlist(List<Basketcodelst> input,boolean isBulk);
    }
    private ProductionSignoffAdapter adapter;
    List<Basketcodelst> basketList;
    SignoffcustomdialogBinding binding;
    SetUpBarCodeReader barCodeReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignoffcustomdialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (isBulk)
            setBulkViews();
        else
            setUnBulkViews();
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReader.onResume();
        setUpRecyclerView();
        fillData();
        clearInputLayoutError(binding.basketcodeEdt);
        clearInputLayoutError(binding.basketQty);
        handleListeners();
        handleButtonGroup();
        clearInputLayoutError(binding.basketcodeEdt);
        handleEditTextFocus(binding.basketcodeEdt);
    }

    private void handleButtonGroup() {
        if (isBulk) {
            binding.bulkGroup.check(R.id.bulk);
            binding.bulkGroup.uncheck(R.id.diff);
        } else {
            binding.bulkGroup.check(R.id.diff);
            binding.bulkGroup.uncheck(R.id.bulk);
        }
        binding.bulk.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = true;
                setBulkViews();
            } else {
                warningDialogWithChoice(getContext(), "Change baskets type will make you add baskets from the beginning.","Are you sure to change type?",true);
            }
        });
        binding.diff.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = false;
                setUnBulkViews();
            } else {
                warningDialogWithChoice(getContext(), "Change baskets type will make you add baskets from the beginning.","Are you sure to change type?",false);
            }
        });
    }

    private void setUnBulkViews() {
        binding.bulkGroup.check(R.id.diff);
        binding.bulkGroup.uncheck(R.id.bulk);
        binding.basketQty.getEditText().setEnabled(true);
        binding.basketQty.getEditText().setClickable(true);
        binding.basketQtyTxt.setVisibility(View.VISIBLE);
        binding.totalqtnTxt.setText("Total Added Qty");
        updateViews();
    }

    private void warningDialogWithChoice(Context context, String s, String s1,boolean bulk) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,s,s1);
        dialog.setOnOkClicked(() -> {
            basketList.clear();
            isBulk = bulk;
            if (bulk) {
                setBulkViews();
                binding.bulkGroup.check(R.id.bulk);
                binding.bulkGroup.uncheck(R.id.diff);
            } else {
                setUnBulkViews();
                binding.bulkGroup.check(R.id.diff);
                binding.bulkGroup.uncheck(R.id.bulk);
            }
            dialog.dismiss();
            basketList.clear();
            basketCodes.clear();
        });
        dialog.setOnCancelClicked(()->{
            if (!bulk) {
                binding.bulkGroup.check(R.id.bulk);
                binding.bulkGroup.uncheck(R.id.diff);
            } else {
                binding.bulkGroup.check(R.id.diff);
                binding.bulkGroup.uncheck(R.id.bulk);
            }
        });
        dialog.show();
    }

    private void setBulkViews() {
        binding.bulkGroup.check(R.id.bulk);
        binding.bulkGroup.uncheck(R.id.diff);
        binding.basketQty.getEditText().setText(loadingQty);
        binding.basketQty.getEditText().setEnabled(false);
        binding.basketQty.getEditText().setClickable(false);
        binding.totalAddedQtn.setText(loadingQty);
        binding.basketQtyTxt.setVisibility(View.GONE);
        binding.totalqtnTxt.setText("Total Qty");
    }

    private void handleListeners() {
        binding.basketcodeEdt.getEditText().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                handleBasketEditTextActionGo();
                return true;
            }
            return false;
        });
        binding.saveBtn.setOnClickListener(__->{
            if (!basketList.isEmpty()){
                if (isBulk) {
                    if (calculateTotalAddedQty(basketList) == Integer.parseInt(loadingQty)) {
                        onInputSelected.sendlist(basketList, isBulk);
                        dismiss();
                        basketList.clear();
                        basketCodes.clear();
                    } else {
                        warningDialog(getContext(), "Please add all loading qty to baskets!");
                    }
                } else {
                    onInputSelected.sendlist(basketList, isBulk);
                    dismiss();
                    basketList.clear();
                    basketCodes.clear();
                }
            } else {
                warningDialog(getContext(),"Please add at least 1 basket!");
            }
        });
    }

    private void handleBasketEditTextActionGo() {
        String basketQty  = binding.basketQty.getEditText().getText().toString().trim();
        String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
        if (!basketQty.isEmpty()){
            if (containsOnlyDigits(basketQty)){
                if (!isBulk) {
                    if (Integer.parseInt(basketQty) <= Integer.parseInt(getRemaining()) && Integer.parseInt(basketQty) > 0) {
                        if (!basketCode.isEmpty()) {
                            Basketcodelst basketcodelst = new Basketcodelst(basketCode, Integer.parseInt(basketQty));
                            if (basketList.isEmpty()) {
                                basketList.add(basketcodelst);
                                basketCodes.add(basketCode);
                                adapter.setBulk(isBulk);
                                adapter.notifyDataSetChanged();
                                updateViews();
                                binding.basketcodeEdt.getEditText().setText("");
                            } else {

                                if (!basketCodes.contains(basketCode))  {
                                    basketList.add(basketcodelst);
                                    basketCodes.add(basketCode);
                                    adapter.setBulk(isBulk);
                                    adapter.notifyDataSetChanged();
                                    updateViews();
                                    binding.basketcodeEdt.getEditText().setText("");
                                } else {
                                    binding.basketcodeEdt.setError("Basket added previously!");
                                }

                            }
                        } else {
                            binding.basketcodeEdt.setError("Please enter or scan a valid basket code!");
                        }
                    } else {
                        binding.basketQty.setError("Basket quantity must be equal or less than remaining quantity and more than 0!");
                        binding.basketcodeEdt.getEditText().setText("");
                    }
                } else {
                    if (!basketCode.isEmpty()) {
                        Basketcodelst basketcodelst = new Basketcodelst(basketCode, Integer.parseInt(basketQty));
                        if (basketList.isEmpty()) {
                            basketList.add(basketcodelst);
                            basketCodes.add(basketCode);
                            adapter.setBulk(isBulk);
                            adapter.notifyDataSetChanged();
                            binding.basketcodeEdt.getEditText().setText("");
                        } else {
                            if (!basketCodes.contains(basketCode)) {
                                basketList.add(basketcodelst);
                                basketCodes.add(basketCode);
                                adapter.setBulk(isBulk);
                                adapter.notifyDataSetChanged();
                                binding.basketcodeEdt.getEditText().setText("");

                            } else {
                                binding.basketcodeEdt.setError("Basket added previously!");
                            }

                        }
                    } else {
                        binding.basketcodeEdt.setError("Please enter or scan a valid basket code!");
                    }
                }
            } else {
                binding.basketQty.setError("Basket quantity must contain only digits!");
                binding.basketcodeEdt.getEditText().setText("");
            }
        } else {
            binding.basketQty.setError("Please enter basket quantity first and scan basket again!");
        }
    }

    private void fillData() {
        binding.childdesc.setText(parentDesc);
        binding.signoffqty.setText(loadingQty);
        if (!isBulk)
            updateViews();
    }

    private void updateViews() {
        binding.basketQty.getEditText().setText(getRemaining());
        binding.totalAddedQtn.setText(String.valueOf(calculateTotalAddedQty(basketList)));
    }

    private String getRemaining() {
        int remaining = Integer.parseInt(loadingQty) - calculateTotalAddedQty(basketList);
        return String.valueOf(remaining);
    }

    private int calculateTotalAddedQty(List<Basketcodelst>list) {
        int total = 0;
        if (!list.isEmpty()) {
            for (Basketcodelst basketcodelst : list) {
                total += basketcodelst.getQty();
            }
        }
        return total;
    }



    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        activity.runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketcodeEdt.getEditText().setText(scannedText);
            handleBasketEditTextActionGo();
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);

    }

    private void setUpRecyclerView() {
        adapter = new ProductionSignoffAdapter(basketList,this,isBulk);
        binding.basketcodeRv.setAdapter(adapter);
    }

}
