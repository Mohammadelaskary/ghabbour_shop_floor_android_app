package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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


public class ProductionSignoffFragment extends Fragment implements  BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, OnBasketRemoved, BarcodeReadListener {
//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentProductionSignoffBinding binding;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

    private MachinesignoffViewModel viewModel;
    List<Basketcodelst> basketList = new ArrayList<>();
    //String passedtext;
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
        binding = FragmentProductionSignoffBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    private int currentSignOffQty;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        machinesignoffViewModel = ViewModelProviders.of(this, providerFactory).get(MachinesignoffViewModel.class);
        viewModel = new ViewModelProvider(this).get(MachinesignoffViewModel.class);
        bottomSheetBehavior = BottomSheetBehavior.from(binding.basketsBottomSheet.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setDraggable(false);
        progressDialog= loadingProgressDialog(getContext());
        binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
        binding.saveBtn.setIconResource(R.drawable.ic__save);
        handleEditTextFocus(binding.machinecodeEdt,binding.basketsBottomSheet.basketcodeEdt);
        binding.signOffQty.setEnabled(userInfo.getAllowPartialSignOffManufacturing());
        observeStatus();

        binding.machinecodeNewedttxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                    if (machineCode.isEmpty())
                        binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code));
                    else
                        viewModel.getmachinecodedata(USER_ID, DEVICE_SERIAL_NO, machineCode);
                    return true;
                }
                return false;
            }
        });

//        fragmentProductionSignoffBinding.machinecodeNewedttxt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                machinesignoffViewModel.getmachinecodedata("1", "S123", fragmentProductionSignoffBinding.machinecodeNewedttxt.getText().toString());
//
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });

        binding.signOffQty.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String signOffQtyText = binding.signOffQty.getEditText().getText().toString().trim();
                if (!hasFocus) {
                    if (!signOffQtyText.isEmpty()){
                        if (containsOnlyDigits(signOffQtyText)){
                            if (Integer.parseInt(signOffQtyText)>machineLoading.getRemainingQty()&&Integer.parseInt(signOffQtyText)<=0){
                                binding.signOffQty.setError(getString(R.string.please_enter_a_valid_sign_off_qty));
                                binding.signOffQty.getEditText().requestFocus();
                            }
                        } else {
                            binding.signOffQty.setError(getString(R.string.please_enter_a_valid_sign_off_qty));
                            binding.signOffQty.getEditText().requestFocus();
                        }
                    } else {
                        binding.signOffQty.setError(getString(R.string.please_enter_sign_off_qty));
                        binding.signOffQty.getEditText().requestFocus();
                    }
                }

            }
        });



        getdata();
        addTextWatcher();
        initViews();
        subscribeRequest();
        observeBasketStatus();
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
                    binding.basketsBottomSheet.basketcodeEdt.setError(statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void handleButtonGroup() {
        if (isBulk) {
            binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
            binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
        } else {
            binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
            binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
        }
        binding.basketsBottomSheet.bulk.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = true;
                setBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning),getString(R.string.are_you_sure_to_change_type),true);
            }
        });
        binding.basketsBottomSheet.diff.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = false;
                setUnBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning),getString(R.string.are_you_sure_to_change_type),false);
            }
        });
    }
    private void warningDialogWithChoice(Context context, String s, String s1, boolean bulk) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,s,s1);
        dialog.setOnOkClicked(() -> {
            basketList.clear();
            basketCodes.clear();
            adapter.notifyDataSetChanged();
            handleTableTitle();
            isBulk = bulk;
            if (bulk) {
                setBulkViews();
                binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
            } else {
                setUnBulkViews();
                binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
            }
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(()->{
            if (!bulk) {
                binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
            } else {
                binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
            }
        });
        dialog.show();
    }
    private void warningDialogWithChoiceForChangeSignOffQty(Context context, String s, String s1) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,s,s1);
        dialog.setOnOkClicked(() -> {
            basketList.clear();
            basketCodes.clear();
            adapter.notifyDataSetChanged();
            handleTableTitle();
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(dialog::dismiss);
        dialog.show();
    }
    BottomSheetBehavior bottomSheetBehavior ;
    boolean isExpanded = false;
    private void setupBasketsBottomSheet() {
        setupBasketsRecyclerview();
        fillData();
        clearInputLayoutError(binding.basketsBottomSheet.basketcodeEdt);
        clearInputLayoutError(binding.basketsBottomSheet.basketQty);
        handleListeners();
        handleButtonGroup();
        handleTableTitle();
        clearInputLayoutError(binding.basketsBottomSheet.basketcodeEdt);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    isExpanded = true;
                    binding.disableColor.setVisibility(View.VISIBLE);
                }else{
                    isExpanded=false;
                    binding.disableColor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }
    List<String> basketCodes = new ArrayList<>();
    private void handleBasketEditTextActionGo(String basketCode) {
        String basketQty  = binding.basketsBottomSheet.basketQty.getEditText().getText().toString().trim();
        if (!basketQty.isEmpty()){
            if (containsOnlyDigits(basketQty)){
                if (!isBulk) {
                    if (Integer.parseInt(basketQty) <= Integer.parseInt(getRemaining()) && Integer.parseInt(basketQty) > 0) {
                        if (!basketCode.isEmpty()) {
                            if (basketList.isEmpty()) {
                                viewModel.checkBasketEmpty(basketCode,"0",machineLoading.getChildId(),machineLoading.getJobOrderId(),machineLoading.getOperationId(),machineLoading.getProductionSequenceNo());
                                progressDialog.show();
                            } else {
                                if (!basketCodes.contains(basketCode))  {
                                    viewModel.checkBasketEmpty(basketCode,"0",machineLoading.getChildId(),machineLoading.getJobOrderId(),machineLoading.getOperationId(),machineLoading.getProductionSequenceNo());
                                    progressDialog.show();
                                } else {
                                    binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                                }

                            }
                        } else {
                            binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                        }
                    } else {
                        binding.basketsBottomSheet.basketQty.setError(getString(R.string.basket_qty_must_be_equal_or_less_than_remaining_qty_and_more_than_0));
                        binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
                    }
                }
                else  {
                    if (!basketCode.isEmpty()) {
                        Basketcodelst basketcodelst = new Basketcodelst(basketCode, Integer.parseInt(basketQty));
                        if (basketList.isEmpty()) {
                            viewModel.checkBasketEmpty(basketCode,"0",machineLoading.getChildId(),machineLoading.getJobOrderId(),machineLoading.getOperationId(),machineLoading.getProductionSequenceNo());
                            progressDialog.show();
                        } else {
                            if (!basketCodes.contains(basketCode)) {
                                viewModel.checkBasketEmpty(basketCode,"0",machineLoading.getChildId(),machineLoading.getJobOrderId(),machineLoading.getOperationId(),machineLoading.getProductionSequenceNo());
                                progressDialog.show();
                            } else {
                                binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                            }

                        }
                    } else {
                        binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                    }
                }
            } else {
                binding.basketsBottomSheet.basketQty.setError(getString(R.string.basket_qty_must_contain_only_digits));
                binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
            }
        } else {
            binding.basketsBottomSheet.basketQty.setError(getString(R.string.please_enter_basket_qty_first_and_scan_basket_again));
        }
    }

    private void addBasketIfBulk() {
        String basketCode = binding.basketsBottomSheet.basketcodeEdt.getEditText().getText().toString().trim();
        String basketQty  = binding.basketsBottomSheet.basketQty.getEditText().getText().toString().trim();
        Basketcodelst basketcodelst = new Basketcodelst(basketCode,Integer.parseInt(basketQty));
        basketCodes.add(basketCode);
        basketList.add(basketcodelst);
        handleTableTitle();
        adapter.setBulk(isBulk);
        adapter.notifyDataSetChanged();
        binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
    }

    private void addBasketIfNotBulk(){
        String basketCode = binding.basketsBottomSheet.basketcodeEdt.getEditText().getText().toString().trim();
        String basketQty  = binding.basketsBottomSheet.basketQty.getEditText().getText().toString().trim();
        Basketcodelst basketcodelst = new Basketcodelst(basketCode,Integer.parseInt(basketQty));
        basketCodes.add(basketCode);
        basketList.add(basketcodelst);
        handleTableTitle();
        adapter.setBulk(isBulk);
        adapter.notifyDataSetChanged();
        updateViews();
        binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
    }
    private void handleTableTitle() {
        if (basketList.isEmpty())
            binding.basketsBottomSheet.tableTitle.setVisibility(View.GONE);
        else
            binding.basketsBottomSheet.tableTitle.setVisibility(View.VISIBLE);
    }
    private void handleListeners() {
        binding.basketsBottomSheet.basketcodeEdt.getEditText().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                handleBasketEditTextActionGo(binding.basketsBottomSheet.basketcodeEdt.getEditText().getText().toString().trim());
                return true;
            }
            return false;
        });
        binding.basketsBottomSheet.saveBtn.setOnClickListener(__->{
            if (!basketList.isEmpty()){
                if (!isBulk) {
                    if (calculateTotalAddedQty(basketList) == currentSignOffQty) {
//                        onInputSelected.sendlist(basketList, isBulk);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        handleBasketsButtonColor();
//                        cancel();
//                        barCodeReader.onPause();

                    } else {
                        warningDialog(getContext(), getString(R.string.please_add_all_loading_qty_to_baskets));
                    }
                } else {
//                    onInputSelected.sendlist(basketList, true);
                    Log.d(TAG, "handleListeners: not empty");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    handleBasketsButtonColor();
//                    cancel();
//                    barCodeReader.onPause();
                }
            } else {
                warningDialog(getContext(),getString(R.string.please_add_at_least_1_basket));
            }
        });
        binding.basketsBottomSheet.cancel.setOnClickListener(__->{
            if (!basketList.isEmpty()) {
                CustomChoiceDialog choiceDialog = new CustomChoiceDialog(getContext(), getString(R.string.cancel_now_will_remove_added_baskets), getString(R.string.are_you_sure_to_cancel));
                choiceDialog.setOnOkClicked(() -> {
                    basketList.clear();
                    basketCodes.clear();
//                    onInputSelected.sendlist(basketList, isBulk);
                    choiceDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    handleBasketsButtonColor();
                });
                //                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                choiceDialog.setOnCancelClicked(choiceDialog::dismiss);
                choiceDialog.show();
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                handleBasketsButtonColor();
            }
        });
    }
    private void fillData() {
        binding.basketsBottomSheet.childdesc.setText(childDesc);
        if (!isBulk)
            updateViews();
    }
    private ProductionSignoffAdapter adapter;
    private void setupBasketsRecyclerview() {
        adapter = new ProductionSignoffAdapter(basketList,this,isBulk);
        binding.basketsBottomSheet.basketcodeRv.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.basketsBottomSheet.basketcodeRv.setLayoutManager(manager);
    }
    private String getRemaining() {
        int remaining = currentSignOffQty- calculateTotalAddedQty(basketList);
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


    private void addTextWatcher() {
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
        binding.signOffQty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!basketList.isEmpty()){
                    warningDialogWithChoiceForChangeSignOffQty(getContext(), getString(R.string.change_sign_off_qty_will_remove_added_sign_off_baskets),getString(R.string.are_you_sure_to_change_sign_off_qty));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else if (status.equals(Status.SUCCESS))
                progressDialog.dismiss();
            else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    String childDesc;
    private MachineLoading machineLoading;
    public void getdata() {
        viewModel.getApiResponseMachineLoadingData().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if(response.getData()!=null) {
                    machineLoading = response.getData();
                    int loadingQty = response.getData().getLoadingQty();
                    if (loadingQty!=0) {
                        binding.dataLayout.setVisibility(View.VISIBLE);
                        childDesc = response.getData().getChildDescription();
                        setupBasketsBottomSheet();
                        binding.childesc.setText(response.getData().getChildDescription());
                        binding.jobordernum.setText(response.getData().getJobOrderName());
                        binding.operation.setText(response.getData().getOperationEnName());
                        binding.loadingQty.setText(response.getData().getLoadingQty().toString());
                        binding.signedOffQty.setText(response.getData().getSignedOffQty().toString());
                        binding.signOffQty.getEditText().setText(response.getData().getRemainingQty().toString());
//                        binding.loadingQty.setText(String.valueOf(loadingQty));
//                        binding.signoffqty.setText(response.getData().get);
                        binding.Joborderqtn.setText(String.valueOf(response.getData().getJobOrderQty()));
                        basketCodes.clear();
                        basketList.clear();
                        adapter.notifyDataSetChanged();
                        handleBasketsButtonColor();
                    } else {
                        binding.dataLayout.setVisibility(View.GONE);
                        warningDialog(getContext(),getString(R.string.error_in_loading_qty));
                    }
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.childesc.setText("");
                    binding.jobordernum.setText("");
                    binding.operation.setText("");
                    binding.Joborderqtn.setText("");
                    binding.machinecodeEdt.setError(statusMessage);
                }
            } else {
                binding.dataLayout.setVisibility(View.GONE);
                binding.childesc.setText("");
                binding.jobordernum.setText("");
                binding.operation.setText("");
                binding.Joborderqtn.setText("");
            }


        });
    }
    private boolean isBulk = true;
    private void initViews() {
        binding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();
                currentSignOffQty = Integer.parseInt(binding.signOffQty.getEditText().getText().toString().trim());
                if (!machineCode.isEmpty()&&bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    binding.basketsBottomSheet.signoffqty.setText(String.valueOf(currentSignOffQty));
                    handleBottomSheetUi();
                    adapter.notifyDataSetChanged();
                    handleTableTitle();
                } else {
                    binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code_and_press_enter));
                }

            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                if (machineCode.isEmpty())
                    binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code));
                if (basketList.isEmpty())
                    warningDialog(getContext(),getString(R.string.please_add_at_least_1_basket));
                if (!machineCode.isEmpty()&& !basketList.isEmpty()) {
                    MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
                    machineSignoffBody.setMachineCode(binding.machinecodeNewedttxt.getText().toString());
                    machineSignoffBody.setUserID(USER_ID );
                    machineSignoffBody.setDeviceSerialNo(DEVICE_SERIAL_NO);
                    if (!isBulk)
                        machineSignoffBody.setSignOutQty(calculateTotalAddedQty(basketList));
                    else
                        machineSignoffBody.setSignOutQty(currentSignOffQty);
                    machineSignoffBody.setBasketLst(basketList);
                    machineSignoffBody.setIsBulkQty(isBulk);
                    machineSignoffBody.setAppLang(LocaleHelper.getLanguage(getContext()));
                    if (currentSignOffQty== machineLoading.getLoadingQty())
                        viewModel.getmachinesignoff(machineSignoffBody);
                    else
                        viewModel.MachineSignOff_Partial(machineSignoffBody);
                }

            }
        });


    }
    private void handleBasketsButtonColor(){
        if (basketList.isEmpty()){
            binding.signoffitemsBtn.setText(getString(R.string.add_baskets));
            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.appbarcolor));
            binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
        } else {
            binding.signoffitemsBtn.setText(R.string.edit_baskets);
            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.done));
            binding.signoffitemsBtn.setIconResource(R.drawable.ic_edit);
        }
    }
    private void handleBottomSheetUi() {
        if (isBulk)
            setBulkViews();
        else
            setUnBulkViews();
    }
    private void setBulkViews() {
        binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
        binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
        binding.basketsBottomSheet.basketQty.getEditText().setText(String.valueOf(currentSignOffQty));
        binding.basketsBottomSheet.basketQty.getEditText().setEnabled(false);
        binding.basketsBottomSheet.basketQty.getEditText().setClickable(false);
        binding.basketsBottomSheet.totalAddedQtn.setText(String.valueOf(currentSignOffQty));
        binding.basketsBottomSheet.basketQtyTxt.setVisibility(View.GONE);
        binding.basketsBottomSheet.totalqtnTxt.setText(getString(R.string.total_qty));
        binding.basketsBottomSheet.basketcodeEdt.getEditText().requestFocus();
    }
    private void setUnBulkViews() {
        binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
        binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
        binding.basketsBottomSheet.basketQty.getEditText().setEnabled(true);
        binding.basketsBottomSheet.basketQty.getEditText().setClickable(true);
        binding.basketsBottomSheet.basketQtyTxt.setVisibility(View.VISIBLE);
        binding.basketsBottomSheet.totalqtnTxt.setText(getString(R.string.total_added_qty));
        binding.basketsBottomSheet.basketcodeEdt.getEditText().requestFocus();
        updateViews();
    }
    private void updateViews() {
        binding.basketsBottomSheet.basketQty.getEditText().setText(getRemaining());
        binding.basketsBottomSheet.totalAddedQtn.setText(String.valueOf(calculateTotalAddedQty(basketList)));
    }
    private void subscribeRequest() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), responseStatus -> {
            if (responseStatus != null) {
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                    back(ProductionSignoffFragment.this);
                } else
                    warningDialog(getContext(),statusMessage);
            }
        });

    }


//    @Override
//    public void sendInput(String input) {
//        //fragmentProductionSignoffBinding.totalqtn.setText(input);
//        passedtext=input;
//    }

    //that for send list

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               String scannedText = barCodeReader.scannedData(barcodeReadEvent);
               if (!isExpanded) {
                   binding.machinecodeNewedttxt.setText(scannedText);
                   viewModel.getmachinecodedata(USER_ID, DEVICE_SERIAL_NO, scannedText);
                   MyMethods.hideKeyboard(getActivity());
               } else {
                   binding.basketsBottomSheet.basketcodeEdt.getEditText().setText(scannedText);
                   handleBasketEditTextActionGo(scannedText);
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
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }

    @Override
    public void onPause () {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onBasketRemoved(int position) {
        basketList.remove(position);
        basketCodes.remove(position);
        adapter.notifyDataSetChanged();
        handleTableTitle();
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                if (!isExpanded) {
                    binding.machinecodeNewedttxt.setText(scannedText);
                    viewModel.getmachinecodedata(USER_ID, DEVICE_SERIAL_NO, scannedText);
                    MyMethods.hideKeyboard(getActivity());
                } else {
                    binding.basketsBottomSheet.basketcodeEdt.getEditText().setText(scannedText);
                    handleBasketEditTextActionGo(scannedText);
                }
            }
        });
    }
}





