package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.OnBasketRemoved;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.AddBasketsBottomSheetLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class AddBasketsBottomSheet  extends BottomSheetDialogFragment implements OnBasketRemoved, BarcodeReadListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    private List<Basket> basketList;
    private List<String> basketCodes = new ArrayList<>();
    private boolean isBulk;
    private MachineHoldViewModel viewModel;
    private MachineData machineData;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private OnAddBasketsBottomSheetButtonsClicked onAddBasketsBottomSheetButtonsClicked;

    public void setMachineData(MachineData machineData) {
        this.machineData = machineData;
    }

    public void setBasketList(List<Basket> basketList) {
        this.basketList = basketList;
        basketCodes.clear();
        for (Basket basket : basketList) {
            basketCodes.add(basket.getBasketCode());
        }
    }

    public boolean isBulk() {
        return isBulk;
    }

    public void setBulk(boolean bulk) {
        isBulk = bulk;
    }

    private int remainingQty;

    public void setRemainingQty(int remainingQty) {
        this.remainingQty = remainingQty;
    }

    public AddBasketsBottomSheet(MachineHoldViewModel viewModel, OnAddBasketsBottomSheetButtonsClicked onAddBasketsBottomSheetButtonsClicked) {
        this.viewModel = viewModel;
        this.onAddBasketsBottomSheetButtonsClicked = onAddBasketsBottomSheetButtonsClicked;
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);

    }

    private AddBasketsBottomSheetLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddBasketsBottomSheetLayoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillData();
        handleBottomSheetUi();
        setupBasketsRecyclerview();
        observeBasketStatus();
        clearInputLayoutError(binding.basketcodeEdt);
        clearInputLayoutError(binding.basketQty);
        handleListeners();
        handleButtonGroup();
        handleTableTitle();
//        binding.basketQty.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String basketQty = binding.basketQty.getEditText().getText().toString().trim();
//                Log.d(TAG, "onFocusChange: "+remainingQty);
//                if (!hasFocus){
//                    if (!basketQty.isEmpty()){
//                        if (containsOnlyDigits(basketQty)){
//                            if (Integer.parseInt(basketQty)<=remainingQty&&Integer.parseInt(basketQty)>0){
//                                if (isBulk) {
//                                    binding.basketQty.getEditText().setEnabled(false);
//                                    binding.totalAddedQtn.setText(basketQty);
//                                } else
//                                    binding.basketQty.getEditText().setEnabled(true);
//                            } else {
//                                binding.basketQty.setError(getString(R.string.basket_qty_must_be_less_than_remaining_qty_and_more_than_0));
//                            }
//                        } else {
//                            binding.basketQty.setError(getString(R.string.please_enter_a_valid_qty));
//                        }
//                    } else {
//                        binding.basketQty.setError(getString(R.string.please_enter_qty));
//                    }
//                }
//            }
//        });
    }

//
    private void handleButtonGroup() {
        if (isBulk) {
            binding.bulkGroup.check(R.id.bulk);
            binding.bulkGroup.uncheck(R.id.diff);
        } else {
            binding.bulkGroup.check(R.id.diff);
            binding.bulkGroup.uncheck(R.id.bulk);
        }
        binding.bulk.setOnClickListener(v -> {
            if (basketList.isEmpty()) {
                isBulk = true;
                setBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning), getString(R.string.are_you_sure_to_change_type), true);
            }
        });
        binding.diff.setOnClickListener(v -> {
            Log.d("basketList", basketList.isEmpty() + "");
            if (basketList.isEmpty()) {
                isBulk = false;
                setUnBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getContext().getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning), getContext().getString(R.string.are_you_sure_to_change_type), false);
            }
        });
    }

    private void warningDialogWithChoice(Context context, String s, String s1, boolean bulk) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context, s, s1);
        dialog.setOnOkClicked(() -> {
            basketList.clear();
            basketCodes.clear();
            adapter.notifyDataSetChanged();
            handleTableTitle();
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
            binding.loadingQty.setText(getRemaining()+" / "+machineData.getMachineQty());
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(() -> {
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


    private void handleBasketEditTextActionGo(String basketCode) {
        String basketQty  = binding.basketQty.getEditText().getText().toString().trim();
        if (!basketQty.isEmpty()){
            if (containsOnlyDigits(basketQty)){
                if (!isBulk) {
                    if (Integer.parseInt(basketQty) <= Integer.parseInt(getRemaining()) && Integer.parseInt(basketQty) > 0) {
                        if (!basketCode.isEmpty()) {
                            if (basketList.isEmpty()) {
                                viewModel.checkBasketEmpty(basketCode,"0",String.valueOf(machineData.getChildId()),String.valueOf(machineData.getJobOrderId()),String.valueOf(machineData.getOperationId()));
                            } else {
                                if (!basketCodes.contains(basketCode))  {
                                    viewModel.checkBasketEmpty(basketCode,"0",String.valueOf(machineData.getChildId()),String.valueOf(machineData.getJobOrderId()),String.valueOf(machineData.getOperationId()));
                                } else {
                                    binding.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                                }

                            }
                        } else {
                            binding.basketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                        }
                    } else {
                        binding.basketQty.setError(getString(R.string.basket_qty_must_be_equal_or_less_than_remaining_qty_and_more_than_0));
                        binding.basketcodeEdt.getEditText().setText("");
                    }
                }
                else  {
                    if (!basketCode.isEmpty()) {
                        if (basketList.isEmpty()) {
                            viewModel.checkBasketEmpty(basketCode,"0",String.valueOf(machineData.getChildId()),String.valueOf(machineData.getJobOrderId()),String.valueOf(machineData.getOperationId()));
                        } else {
                            if (!basketCodes.contains(basketCode)) {
                                viewModel.checkBasketEmpty(basketCode,"0",String.valueOf(machineData.getChildId()),String.valueOf(machineData.getJobOrderId()),String.valueOf(machineData.getOperationId()));
                            } else {
                                binding.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                            }

                        }
                    } else {
                        binding.basketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                    }
                }
            } else {
                binding.basketQty.setError(getString(R.string.basket_qty_must_contain_only_digits));
                binding.basketcodeEdt.getEditText().setText("");
            }
        } else {
            binding.basketQty.setError(getString(R.string.please_enter_basket_qty_first_and_scan_basket_again));
            binding.basketcodeEdt.getEditText().setText("");
        }
    }
    private void observeBasketStatus() {
        viewModel.getCheckBasketEmpty().observe(getViewLifecycleOwner(), responseStatus -> {
            if (responseStatus != null){
                String statusMessage= responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    if (isBulk)
                        addBasketIfBulk();
                    else
                        addBasketIfNotBulk();
                } else {
                    binding.basketcodeEdt.setError(statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }
    private void addBasketIfBulk() {
        String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
        String basketQty  = binding.basketQty.getEditText().getText().toString().trim();
        Basket basket = new Basket(basketCode,Integer.parseInt(basketQty));
        basketCodes.add(basketCode);
        basketList.add(basket);
        handleTableTitle();
        adapter.setBulk(isBulk);
        adapter.notifyDataSetChanged();
        binding.basketcodeEdt.getEditText().setText("");
        binding.basketQty.getEditText().setEnabled(false);
        binding.totalAddedQtn.setText(basketQty);
        binding.loadingQty.setText(getRemaining()+" / "+machineData.getMachineQty());
    }

    private void addBasketIfNotBulk(){
        String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
        String basketQty  = binding.basketQty.getEditText().getText().toString().trim();
        Basket basket = new Basket(basketCode,Integer.parseInt(basketQty));
        basketCodes.add(basketCode);
        basketList.add(basket);
        handleTableTitle();
        adapter.setBulk(isBulk);
        adapter.notifyDataSetChanged();
        updateViews();
        binding.basketcodeEdt.getEditText().setText("");
    }
    private void handleTableTitle() {
        if (basketList.isEmpty())
            binding.tableTitle.setVisibility(View.GONE);
        else
            binding.tableTitle.setVisibility(View.VISIBLE);
    }
    private void handleListeners() {
        binding.basketcodeEdt.getEditText().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                handleBasketEditTextActionGo(binding.basketcodeEdt.getEditText().getText().toString().trim());
                return true;
            }
            return false;
        });
        binding.saveBtn.setOnClickListener(__->{
            if (!basketList.isEmpty()){
                if (!isBulk) {
//                    if (calculateTotalAddedQty(basketList) <= Integer.parseInt(getRemaining())) {
//                        onInputSelected.sendlist(basketList, isBulk);

                        onAddBasketsBottomSheetButtonsClicked.onAddBasketsBottomSheetButtonsClicked(basketList,isBulk,calculateTotalAddedQty(basketList));
                    //                        cancel();
//                        barCodeReader.onPause();

//                    } else {
//                        warningDialog(getContext(), getContext().getString(R.string.basket_qty_must_be_equal_or_less_than_remaining_qty_and_more_than_0));
//                    }
                } else {
//                    onInputSelected.sendlist(basketList, true);
                    onAddBasketsBottomSheetButtonsClicked.onAddBasketsBottomSheetButtonsClicked(basketList,isBulk,basketList.get(0).getQty());
                    Log.d(TAG, "handleListeners: not empty");
                    //                    cancel();
//                    barCodeReader.onPause();
                }
                dismiss();
            } else {
                warningDialog(getContext(),getContext().getString(R.string.please_add_at_least_1_basket));
            }
        });
        binding.cancel.setOnClickListener(__->{
            if (!basketList.isEmpty()) {
                CustomChoiceDialog choiceDialog = new CustomChoiceDialog(getContext(), getContext().getString(R.string.cancel_now_will_remove_added_baskets), getContext().getString(R.string.are_you_sure_to_cancel));
                choiceDialog.setOnOkClicked(() -> {
                    basketList.clear();
                    basketCodes.clear();
//                    onInputSelected.sendlist(basketList, isBulk);
                    onAddBasketsBottomSheetButtonsClicked.onAddBasketsBottomSheetButtonsClicked(basketList,isBulk,0);
                    choiceDialog.dismiss();
                    dismiss();

                });
                //                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                choiceDialog.setOnCancelClicked(choiceDialog::dismiss);
                choiceDialog.show();
            } else {
                dismiss();
            }
        });
    }
    private void fillData() {
        binding.childdesc.setText(machineData.getChildDescription());
        binding.loadingQty.setText(remainingQty+" / "+String.valueOf(machineData.getMachineQty()));
        if (!isBulk)
            updateViews();

    }
    private BasketsAdapter adapter;
    private void setupBasketsRecyclerview() {
        adapter = new BasketsAdapter(basketList,this,isBulk);
        binding.basketcodeRv.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.basketcodeRv.setLayoutManager(manager);
    }
    private String getRemaining() {
        int remaining;
        if (!basketList.isEmpty()) {
            if (!isBulk)
                remaining = remainingQty - calculateTotalAddedQty(basketList);
            else
                remaining = remainingQty - basketList.get(0).getQty();
        } else {
            remaining = remainingQty;
        }
        return String.valueOf(remaining);
    }

    private int calculateTotalAddedQty(List<Basket>list) {
        int total = 0;
        if (!list.isEmpty()) {
            for (Basket basketcodelst : list) {
                total += basketcodelst.getQty();
            }
        }
        return total;
    }



    private void handleBottomSheetUi() {
        if (isBulk)
            setBulkViews();
        else
            setUnBulkViews();
    }
    private void setBulkViews() {
        binding.bulkGroup.check(R.id.bulk);
        binding.bulkGroup.uncheck(R.id.diff);
//        binding.basketQty.getEditText().setText(String.valueOf(remainingQty));
//        binding.basketQty.getEditText().setEnabled(false);
//        binding.basketQty.getEditText().setClickable(false);
//        binding.totalAddedQtn.setText(String.valueOf(remainingQty));
        binding.basketQtyTxt.setVisibility(View.GONE);
        binding.totalqtnTxt.setText(getContext().getString(R.string.total_qty));
        if (basketList.isEmpty())
            binding.basketQty.getEditText().setEnabled(true);
        else {
            binding.basketQty.getEditText().setEnabled(false);
            binding.basketQty.getEditText().setText(String.valueOf(basketList.get(0).getQty()));
            binding.totalAddedQtn.setText(String.valueOf(basketList.get(0).getQty()));
        }
        binding.basketQty.getEditText().requestFocus();
    }
    private void setUnBulkViews() {
        binding.bulkGroup.check(R.id.diff);
        binding.bulkGroup.uncheck(R.id.bulk);
        binding.basketQty.getEditText().setEnabled(true);
        binding.basketQty.getEditText().setClickable(true);
        binding.basketQtyTxt.setVisibility(View.VISIBLE);
        binding.totalqtnTxt.setText(getContext().getString(R.string.total_added_qty));
        binding.basketQty.getEditText().requestFocus();
        updateViews();
    }
    private void updateViews() {
        binding.basketQty.getEditText().setText(getRemaining());
        binding.totalAddedQtn.setText(String.valueOf(calculateTotalAddedQty(basketList)));
        binding.loadingQty.setText(getRemaining()+" / "+machineData.getMachineQty());
    }

    @Override
    public void onBasketRemoved(int position) {
        basketList.remove(position);
        basketCodes.remove(position);
        adapter.notifyDataSetChanged();
        handleTableTitle();
        if (isBulk) {
            if (basketList.isEmpty()) {
                binding.basketQty.getEditText().setEnabled(true);
                binding.basketQty.getEditText().setText("");
                binding.totalAddedQtn.setText("");
            } else {
                binding.basketQty.getEditText().setEnabled(false);
                binding.basketQty.getEditText().setText(String.valueOf(basketList.get(0).getQty()));
                binding.totalAddedQtn.setText(String.valueOf(basketList.get(0).getQty()));
            }
        }
        binding.loadingQty.setText(getRemaining()+" / "+machineData.getMachineQty());
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barcodeReadEvent.getBarcodeData().trim();
            binding.basketcodeEdt.getEditText().setText(scannedText);
            handleBasketEditTextActionGo(scannedText);
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
            String scannedText = barcodeReadEvent.getBarcodeData().trim();
            binding.basketcodeEdt.getEditText().setText(scannedText);
            handleBasketEditTextActionGo(scannedText);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        isBulk = true;
    }

    interface OnAddBasketsBottomSheetButtonsClicked{
        void onAddBasketsBottomSheetButtonsClicked(List<Basket> baskets,boolean isBulk,int qty);
    }
}
