package com.example.gbsbadrsf.ProductionRepair.WeldingQuality;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.sortWelding;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.ProductionRepair.WeldingQuality.ViewModel.WeldingProductionRepairViewModel;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.WeldingProductionRepairFragmentBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class WeldingProductionRepairFragment extends Fragment implements BarcodeReadListener, BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {

    WeldingProductionRepairFragmentBinding binding;
    List<WeldingDefect> defectsWeldingList = new ArrayList<>();
    WeldingProductionRepairChildsQtyDefectsQtyAdapter adapter;
    WeldingProductionRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";

//    @Inject
//    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WeldingProductionRepairFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    int userId = USER_ID;
    String deviceSerialNo = DEVICE_SERIAL_NO,basketCode;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpProgressDialog();
        initViewModel();
        barCodeReader = new SetUpBarCodeReader(this, this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        handleEditTextFocus(binding.basketCode);
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsManufacturing();
        initViews();
        setupRecyclerView();
        if (viewModel.getBasketCode()!=null){
            binding.basketCode.getEditText().setText(viewModel.getBasketCode());
            getBasketData(userId, deviceSerialNo, viewModel.getBasketCode());
        }
    }

    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    getBasketData(userId, deviceSerialNo, basketCode);
//                    getBasketDefectsWelding(userId, deviceSerialNo, basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsManufacturing() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();

//    private void getBasketDefectsWelding(int userId, String deviceSerialNo, String basketCode) {
//        viewModel.getDefectsWeldingViewModel(userId, deviceSerialNo, basketCode);
//        viewModel.getDefectsWeldingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsWelding -> {
//            if (apiResponseDefectsWelding != null) {
//                ResponseStatus responseStatus = apiResponseDefectsWelding.getResponseStatus();
//                String statusMessage = responseStatus.getStatusMessage();
//                if (statusMessage.equals(SUCCESS)) {
//                    if (apiResponseDefectsWelding.getDefectsWelding() != null) {
//                        defectsWeldingList.clear();
//                        List<WeldingDefect> defectsManufacturingListLocal = apiResponseDefectsWelding.getDefectsWelding();
//                        defectsWeldingList.addAll(defectsManufacturingListLocal);
//                        adapter.setDefectsManufacturingList(defectsWeldingList);
//                        qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
//                        String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//                        binding.defectedData.qty.setText(defectedQty);
//                    }
//                } else {
//                    binding.defectedData.qty.setText("");
//                    qtyDefectsQtyDefectedList.clear();
//                }
//            } else {
//                binding.defectedData.qty.setText("");
//                qtyDefectsQtyDefectedList.clear();
////                Toast.makeText(getContext(), "Error in getting data!", Toast.LENGTH_SHORT).show();
//                warningDialog(getContext(),"Error in getting data!");
//            }
//            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
//            adapter.notifyDataSetChanged();
//        });
//    }

    public List<QtyDefectsQtyDefected> groupDefectsById(List<WeldingDefect> defectsWeldingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1;
        sortWelding(defectsWeldingListLocal);
        for (WeldingDefect defectsWelding : defectsWeldingListLocal) {
            if (defectsWelding.getDefectGroupId() != id) {
                int currentId = defectsWelding.getDefectGroupId();
                int defectedQty = defectsWelding.getQtyDefected();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId, defectedQty, getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (WeldingDefect defectsWelding : defectsWeldingList) {
            if (defectsWelding.getDefectGroupId() == currentId)
                defectNo++;
        }
        return defectNo;
    }


    private String calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList) {
            sum += qtyDefectsQtyDefected.getDefectedQty();
        }
        return String.valueOf(sum);
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeGettingBasketData() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this, provider).get(WeldingProductionRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(WeldingProductionRepairViewModel.class);

    }

    LastMoveWeldingBasket basketData;
    String parentDesc, parentCode = "", operationName,jobOrderName;
    int jobOrderQty;
    private void getBasketData(int userId, String deviceSerialNo, String basketCode) {
        binding.basketCode.setError(null);
        viewModel.getBasketDataViewModel(userId, deviceSerialNo, basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMoveWeldingBasket -> {
            if (apiResponseLastMoveWeldingBasket!=null) {

                ResponseStatus responseStatus = apiResponseLastMoveWeldingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    basketData = apiResponseLastMoveWeldingBasket.getLastMoveWeldingBaskets().get(0);
                    adapter.setBasketData(basketData);
                    defectsWeldingList.clear();
                    List<WeldingDefect> defectsManufacturingListLocal = apiResponseLastMoveWeldingBasket.getWeldingDefects();
                    defectsWeldingList.addAll(defectsManufacturingListLocal);
                    adapter.setDefectsManufacturingList(defectsWeldingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
                    String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                    if (basketData.getBasketType().equals("Defected")){
//                        adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
                        int basketQty = basketData.getSignOffQty();
                        String basketDefectedQty = basketQty + "/" + calculateDefectedQty(qtyDefectsQtyDefectedList);
                        binding.basketQtyLayout.qty.setText(basketDefectedQty);
                    } else {
                        qtyDefectsQtyDefectedList = new ArrayList<>();
//                        int basketQty = basketData.getSignOffQty();
//                        String basketDefectedQty = basketQty + "/" + calculateDefectedQty(qtyDefectsQtyDefectedList);
                        binding.basketQtyLayout.qty.setText(String.valueOf(basketData.getSignOffQty()));
                    }
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    parentDesc = basketData.getParentDescription();
                    parentCode = basketData.getParentCode();
                    operationName = basketData.getOperationEnName();
                    jobOrderName  =basketData.getJobOrderName();
                    jobOrderQty   = basketData.getJobOrderQty();
                    binding.basketCode.setError(null);
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    parentDesc = "";
                    parentCode = "";
                    operationName = "";
                    binding.basketCode.setError(statusMessage);
                }
            } else {
                binding.dataLayout.setVisibility(View.GONE);
                parentDesc = "";
                parentCode = "";
                operationName = "";
                binding.basketCode.setError(getString(R.string.error_in_getting_data));
            }
            fillData(parentDesc, jobOrderName, operationName,jobOrderQty);
            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
        });
    }

    private void fillData(String parentDesc, String jobOrderName, String operationName,int jobOrderQty) {
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
    }


    private void setupRecyclerView() {
        adapter = new WeldingProductionRepairChildsQtyDefectsQtyAdapter();
        binding.defectsDetailsList.setAdapter(adapter);
    }

    private void initViews() {

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(userId, deviceSerialNo, scannedText.trim());
//            getBasketDefectsWelding(userId, deviceSerialNo, basketCode);
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
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        basketCode = binding.basketCode.getEditText().getText().toString().trim();
        viewModel.setBasketCode(basketCode);
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(userId, deviceSerialNo, scannedText.trim());
//            getBasketDefectsWelding(userId, deviceSerialNo, basketCode);
        });
    }
}
