package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.sortWelding;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.paint.QualityRepair.PaintQualityRepairFragment.QTY_READY_TO_MOVE;
import static com.example.gbsbadrsf.Quality.welding.WeldingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingQualityRepairBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WeldingQualityRepairFragment extends Fragment implements BarcodeReadListener,BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentWeldingQualityRepairBinding binding;
    List<WeldingDefect> defectsWeldingList = new ArrayList<>();
    WeldingQualityRepairQtyDefectsQtyAdapter adapter;
    public static WeldingQualityRepairViewModel viewModel;

    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeldingQualityRepairBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpProgressDialog();
        barCodeReader = new SetUpBarCodeReader(this, this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        initViewModel();
        if (viewModel.getBasketCode()!=null){
            binding.basketCode.getEditText().setText(viewModel.getBasketCode());
            getBasketData(viewModel.getBasketCode());
        }
        handleEditTextFocus(binding.basketCode);
//        if (viewModel.getBasketData()!=null){
//            basketData = viewModel.getBasketData();
//            fillData(basketData.getParentDescription(),basketData.getOperationEnName(),basketData.getJobOrderName(),basketData.getJobOrderQty());
//        }

//        if (viewModel.getDefectsWeldingList()!=null&&viewModel.getBasketData()!=null){
//            adapter.setDefectsWeldingList(defectsWeldingList);
//            qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
//
//            String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//            if (basketData.getBasketType().equals("Defected")){
//                adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
//                int basketQty = basketData.getSignOffQty();
//                String basketDefectedQty = basketQty + "/" + calculateDefectedQty(qtyDefectsQtyDefectedList);
//                binding.basketQtyLayout.qty.setText(basketDefectedQty);
//            } else {
//                qtyDefectsQtyDefectedList = new ArrayList<>();
//                adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
////                        int basketQty = basketData.getSignOffQty();
////                        String basketDefectedQty = basketQty + "/" + calculateDefectedQty(qtyDefectsQtyDefectedList);
//                binding.basketQtyLayout.qty.setText(basketData.getSignOffQty());
//            }
//        }
        setupRecyclerView();
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsWelding();
        initViews();
    }

    private void addTextWatcher() {
        Objects.requireNonNull(binding.basketCode.getEditText()).addTextChangedListener(new TextWatcher() {
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
        binding.basketCode.getEditText().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                basketCode = binding.basketCode.getEditText().getText().toString().trim();
                getBasketData(basketCode);
//                    getBasketDefectsWelding(basketCode);
                return true;
            }
            return false;
        });
    }

    private void observeGettingDefectsWelding() {
        viewModel.getDefectsWeldingListStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    int userId = USER_ID;
    String deviceSerialNo=DEVICE_SERIAL_NO,basketCode;
//    private void getBasketDefectsWelding(String basketCode) {
//        binding.basketCode.setError(null);
//        viewModel.getDefectsWeldingViewModel(userId,deviceSerialNo,basketCode);
//        viewModel.getDefectsWeldingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsWelding -> {
//            if (apiResponseDefectsWelding!=null) {
//                ResponseStatus responseStatus = apiResponseDefectsWelding.getResponseStatus();
//                String statusMessage = responseStatus.getStatusMessage();
//                if (statusMessage.equals(SUCCESS)) {
//                    if (apiResponseDefectsWelding.getDefectsWelding() != null) {
//                        defectsWeldingList.clear();
//                        List<WeldingDefect> defectsWeldingListLocal = apiResponseDefectsWelding.getDefectsWelding();
//                        defectsWeldingList.addAll(defectsWeldingListLocal);
//                        adapter.setDefectsWeldingList(defectsWeldingList);
//                        qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
//                        String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//                        binding.defectedData.qty.setText(defectedQty);
//
//                    }
//                } else {
//                    binding.defectedData.qty.setText("");
//                    qtyDefectsQtyDefectedList.clear();
//                }
//            } else {
//                warningDialog(getContext(),"Error in getting data!");
//                binding.defectedData.qty.setText("");
//                qtyDefectsQtyDefectedList.clear();
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
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this, provider).get(WeldingQualityRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(WeldingQualityRepairViewModel.class);

    }

    LastMoveWeldingBasket basketData;
    String parentDesc,parentCode = "",operationName,jobOrderName;
    int jobOrderQty,qtyReadyToMove;
    private void getBasketData(String basketCode) {
        viewModel.getBasketData(userId,deviceSerialNo,basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMoveWeldingBasket -> {
            if (apiResponseLastMoveWeldingBasket!=null) {
                ResponseStatus responseStatus = apiResponseLastMoveWeldingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    basketData = apiResponseLastMoveWeldingBasket.getLastMoveWeldingBaskets().get(0);
                    defectsWeldingList.clear();
                    List<WeldingDefect> defectsWeldingListLocal = apiResponseLastMoveWeldingBasket.getWeldingDefects();
                    defectsWeldingList.addAll(defectsWeldingListLocal);
                    qtyReadyToMove = apiResponseLastMoveWeldingBasket.getMinQtyApproved();
                    adapter.setDefectsWeldingList(defectsWeldingList);
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
                    parentDesc = basketData.getParentDescription();
                    parentCode = basketData.getParentCode();
                    operationName = basketData.getOperationEnName();
                    jobOrderName = basketData.getJobOrderName();
                    jobOrderQty  = basketData.getJobOrderQty();
                    binding.dataLayout.setVisibility(View.VISIBLE);
                } else {
                    parentDesc = "";
                    parentCode = "";
                    operationName = "";
                    binding.basketCode.setError(statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                }
            } else {
                parentDesc = "";
                parentCode = "";
                operationName = "";
                binding.basketCode.setError(getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
            }
            fillData(parentDesc,operationName,jobOrderName,jobOrderQty);
            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
        });
    }

    private void fillData(String parentDesc, String operationName,String jobOrderName,int jobOrderQty) {
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
    }


    private void setupRecyclerView() {
        adapter = new WeldingQualityRepairQtyDefectsQtyAdapter();
        adapter.setOnDefectItemClicked(new WeldingQualityRepairQtyDefectsQtyAdapter.OnDefectItemClicked() {
            @Override
            public void onDefectItemClicked(ArrayList<WeldingDefect> defectList) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(BASKET_DATA,basketData);
                bundle.putParcelableArrayList("selectedDefectsWelding",defectList);
                bundle.putInt(QTY_READY_TO_MOVE,qtyReadyToMove);
                Navigation.findNavController(requireView()).navigate(R.id.fragment_welding_quality_repair_to_fragment_welding_quality_defect_repair, bundle);
            }
        });
        binding.defectsDetailsList.setAdapter(adapter);
    }


    private void initViews() {
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(scannedText);
            Log.d(TAG, "onBarcodeEvent: hi from first fragment");
//            getBasketDefectsWelding(scannedText);
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
//        if (basketData!=null) {
//            basketData.setBasketCode(basketCode);
//            viewModel.setBasketData(basketData);
//        }
        basketCode = binding.basketCode.getEditText().getText().toString().trim();
        viewModel.setBasketCode(basketCode);
//        if (!defectsWeldingList.isEmpty()){
//            viewModel.setDefectsWeldingList(defectsWeldingList);
//        }
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(scannedText);
            Log.d(TAG, "onBarcodeEvent: hi from first fragment");
//            getBasketDefectsWelding(scannedText);
        });
    }
}