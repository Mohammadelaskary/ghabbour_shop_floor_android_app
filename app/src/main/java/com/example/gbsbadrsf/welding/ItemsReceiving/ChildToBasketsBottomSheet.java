package com.example.gbsbadrsf.welding.ItemsReceiving;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.context;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.JOB_ORDER;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.databinding.ChildToBasketBottomSheetLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class ChildToBasketsBottomSheet extends Fragment implements BasketCodesAdapter.OnBasketCodeRemoved, BarcodeReadListener, BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener, View.OnClickListener {
    public static final String PPR = "ppr";
    public static final String ISSUED_CHILD = "issued_child";
    private LstIssuedChildParameter issuedChild;
    private List<String> basketsCodes =  new ArrayList<>();
    private BasketCodesAdapter adapter;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private Ppr ppr;
    private JobOrder jobOrder;
    private Activity activity;
    private ChildToBasketViewModel viewModel;
    private ChildToBasketBottomSheetLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChildToBasketBottomSheetLayoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReceivedData();
        setUpBasketCodesRecyclerView();
        attachListenersToButtons();
        fillData();
        observeStatus();
        observeAddingChildToBasket();
        clearInputLayoutError(binding.basketCode);
        handleEditTextFocus(binding.basketCode);
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:progressDialog.show();break;
                case SUCCESS:progressDialog.hide();break;
                case ERROR:progressDialog.hide(); warningDialog(getContext(),getString(R.string.network_issue));break;
            }
        });
    }

    private void getReceivedData() {
        if (getArguments()!=null){
            ppr = getArguments().getParcelable(PPR);
            jobOrder = getArguments().getParcelable(JOB_ORDER);
            issuedChild = getArguments().getParcelable(ISSUED_CHILD);
        }
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        viewModel = new ViewModelProvider(this).get(ChildToBasketViewModel.class);
        progressDialog = loadingProgressDialog(getContext());

    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }

    private void fillData() {
        binding.childDesc.setText(issuedChild.getChilDITEMDESC());
        binding.qty.setText(issuedChild.getQuantitYISSUED());
//        adapter.setBasketCodes(issuedChild.getBasketsCodes());
        if (issuedChild.getIsIssued()) {
            binding.save.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);
        } else {
            binding.save.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.VISIBLE);
        }

    }

    private void attachListenersToButtons() {
        binding.basketCode.getEditText().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                String basketCode = binding.basketCode.getEditText().getText().toString().trim();
                if (!basketsCodes.contains(basketCode)) {
                    basketsCodes.add(basketCode);
                    adapter.notifyDataSetChanged();
                } else
                    binding.basketCode.setError(context.getString(R.string.basket_added_previously));
//                    LstIssuedChildParameter issuedChild = issuedChildList.get(selectedPosition);
////                    issuedChild.setBasketCode(basketCode);
////                    issuedChildList.remove(selectedPosition);
////                    issuedChildList.add(selectedPosition,issuedChild);
////                    adapter.notifyItemChanged(selectedPosition);
//                    if (Integer.parseInt(issuedChild.getQuantitYISSUED())>0)
//                        viewModel.TransferIssuedChildToBasket(USER_ID,DEVICE_SERIAL_NO, jobOrder.getEntitiyId(), basketCode, ppr.getPprparentId(), issuedChild.getChilDITEMID());
//                    else
//                        warningDialog(getContext(),getString(R.string.there_is_no_issued_qty_for_this_child));
                return true;
            }
            return false;
        });
        binding.save.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);
    }

    private void setUpBasketCodesRecyclerView() {
        adapter = new BasketCodesAdapter(basketsCodes,this);
        binding.basketList.setAdapter(adapter);
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            Log.d(TAG, "onBarcodeEvent: "+basketsCodes.size());
            if (!basketsCodes.contains(scannedText)) {
                binding.basketCode.getEditText().setText(scannedText);
                basketsCodes.add(scannedText);
                adapter.notifyDataSetChanged();
            } else {
                binding.basketCode.getEditText().setText("");
                binding.basketCode.setError(context.getString(R.string.basket_added_previously));
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

    private void observeAddingChildToBasket() {
        viewModel.transferIssuedChildToBasketResponse.observe(getViewLifecycleOwner(),apiResponseTransferIssuedChildToBasket -> {
            if (apiResponseTransferIssuedChildToBasket!=null){
                String statusMessage = apiResponseTransferIssuedChildToBasket.getResponseStatus().getStatusMessage();
                if (apiResponseTransferIssuedChildToBasket.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(this);
//                    binding.basketCode.getEditText().setText("");
//                    binding.basketCode.setVisibility(View.GONE);
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            if (!basketsCodes.contains(scannedText)) {
                binding.basketCode.getEditText().setText(scannedText);
                basketsCodes.add(scannedText);
                adapter.notifyDataSetChanged();
            } else {
                binding.basketCode.getEditText().setText("");
                binding.basketCode.setError(context.getString(R.string.basket_added_previously));
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save:
                if (!basketsCodes.isEmpty()){
                    PutChildsToBasketData data = new PutChildsToBasketData(
                            USER_ID,
                            DEVICE_SERIAL_NO,
                            jobOrder.getEntitiyId(),
                            ppr.getPprparentId(),
                            issuedChild.getChilDITEMID(),
                            basketsCodes,
                            LocaleHelper.getLanguage(getContext())
                    );
                    viewModel.TransferIssuedChildToBasket(data);
                } else {
                    warningDialog(getContext(),getContext().getString(R.string.please_add_at_least_1_basket));
                }
                break;
            case R.id.cancel:
                basketsCodes.clear();
                back(this);
                break;
        }
    }

    @Override
    public void onBasketCodeRemoved(int position) {
        basketsCodes.remove(position);
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }
}
