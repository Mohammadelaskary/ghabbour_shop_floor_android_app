package com.example.gbsbadrsf.ProductionRepair.PaintProductionRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.sortPainting;
import static com.example.gbsbadrsf.MyMethods.MyMethods.sortWelding;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.welding.WeldingQualityOperationFragment.EXISTING_BASKET_CODE;
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
import com.example.gbsbadrsf.ProductionRepair.PaintProductionRepair.ViewModel.PaintProductionRepairViewModel;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintProductionRepairBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class PaintProductionRepairFragment extends Fragment implements BarcodeReadListener, BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {

    FragmentPaintProductionRepairBinding binding;
    List<PaintingDefect> defectsPaintingList = new ArrayList<>();
    PaintProductionRepairChildsQtyDefectsQtyAdapter adapter;
    PaintProductionRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";

//    @Inject
//    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec setUpBarCodeReaderInterMec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintProductionRepairBinding.inflate(inflater, container, false);

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
        setUpBarCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        handleEditTextFocus(binding.basketCode);
        addTextWatcher();
        observeGettingBasketData();

        observeGettingDefectsPainting();
        initViews();
        setupRecyclerView();
        if (viewModel.getBasketCode()!=null){
            binding.basketCode.getEditText().setText(viewModel.getBasketCode());
            getBasketData(userId,deviceSerialNo,viewModel.getBasketCode());
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
//                    getBasketDefectsPainting(userId, deviceSerialNo, basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsPainting() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();

//    private void getBasketDefectsPainting(int userId, String deviceSerialNo, String basketCode) {
//        viewModel.getDefectsPaintingViewModel(userId, deviceSerialNo, basketCode);
//        viewModel.getDefectsPaintingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsWelding -> {
//            if (apiResponseDefectsWelding!=null) {
//                ResponseStatus responseStatus = apiResponseDefectsWelding.getResponseStatus();
//                String statusMessage = responseStatus.getStatusMessage();
//                if (statusMessage.equals(SUCCESS)) {
//                    if (apiResponseDefectsWelding.getDefectsPainting() != null) {
//                        defectsPaintingList.clear();
//                        List<DefectsPainting> defectsPaintingListLocal = apiResponseDefectsWelding.getDefectsPainting();
//                        defectsPaintingList.addAll(defectsPaintingListLocal);
//                        adapter.setDefectsPaintingList(defectsPaintingList);
//                        qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
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

    public List<QtyDefectsQtyDefected> groupDefectsById(List<PaintingDefect> defectsPaintingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1;
        sortPainting(defectsPaintingListLocal);
        for (PaintingDefect defectsPainting : defectsPaintingListLocal) {
            if (defectsPainting.getDefectGroupId() != id&&!defectsPainting.getRejectedQty()) {
                int currentId = defectsPainting.getDefectGroupId();
                int defectedQty = defectsPainting.getQtyDefected();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId, defectedQty, getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (PaintingDefect defectsPainting : defectsPaintingList) {
            if (defectsPainting.getDefectGroupId() == currentId)
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
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)){
                warningDialog(getContext(),getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this, provider).get(PaintProductionRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintProductionRepairViewModel.class);

    }

    LastMovePaintingBasket basketData;
    String parentDesc, parentCode = "", operationName,jobOrderName;
    int jobOrderQty;
    private void getBasketData(int userId, String deviceSerialNo, String basketCode) {
        binding.basketCode.setError(null);
        viewModel.getBasketDataViewModel(userId, deviceSerialNo, basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMovePaintingBasket -> {
            if (apiResponseLastMovePaintingBasket!=null) {
                ResponseStatus responseStatus = apiResponseLastMovePaintingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    basketData = apiResponseLastMovePaintingBasket.getLastMovePaintingBaskets().get(0);
                    adapter.setBasketData(basketData);
                    defectsPaintingList = apiResponseLastMovePaintingBasket.getPaintingDefects();
                    adapter.setDefectsPaintingList(defectsPaintingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
                    String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                    adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
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
                    parentDesc = basketData.getParentDescription();
                    parentCode = basketData.getParentCode();
                    operationName = basketData.getOperationEnName();
                    jobOrderName  = basketData.getJobOrderName();
                    jobOrderQty = basketData.getJobOrderQty();
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
            adapter.notifyDataSetChanged();
            fillData(parentDesc, jobOrderName, operationName,jobOrderQty);
        });
    }

    private void fillData(String parentDesc, String jobOrderName, String operationName,int jobOrderQty) {
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
    }


    private void setupRecyclerView() {
        adapter = new PaintProductionRepairChildsQtyDefectsQtyAdapter();
        binding.defectsDetailsList.setAdapter(adapter);
    }

    private void initViews() {

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(userId, deviceSerialNo, scannedText);
//            getBasketDefectsPainting(userId, deviceSerialNo, scannedText);
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
        barCodeReader.onPause();
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
            String scannedText = setUpBarCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(userId, deviceSerialNo, scannedText);
//            getBasketDefectsPainting(userId, deviceSerialNo, scannedText);
        });
    }
}
