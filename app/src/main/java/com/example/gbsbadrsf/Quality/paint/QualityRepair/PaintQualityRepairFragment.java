package com.example.gbsbadrsf.Quality.paint.QualityRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.sortPainting;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.EXISTING_BASKET_CODE;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
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

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PaintQualityRepairFragment extends Fragment implements BarcodeReadListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    public static final String QTY_READY_TO_MOVE = "qty_ready_to_move";
    FragmentWeldingQualityRepairBinding binding;
    List<PaintingDefect> defectsPaintingList = new ArrayList<>();
    PaintQualityRepairQtyDefectsQtyAdapter adapter;
    public static PaintQualityRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";
//    @Inject
//    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeldingQualityRepairBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpProgressDialog();
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        handleEditTextFocus(binding.basketCode);
        initViewModel();
//        if (viewModel.getBasketData()!=null){
//            basketData = viewModel.getBasketData();
//            fillData(basketData.getParentDescription(),basketData.getOperationEnName(),basketData.getJobOrderName(),basketData.getJobOrderQty(),basketData.getOperationEnName());
//        }
        if (viewModel.getBasketCode()!=null){
            binding.basketCode.getEditText().setText(viewModel.getBasketCode());
            getBasketData(viewModel.getBasketCode());
        }
        setupRecyclerView();
//        if (viewModel.getDefectsPaintingList()!=null&&viewModel.getBasketData()!=null){
//            adapter.setDefectsPaintingList(defectsPaintingList);
//            adapter.setBasketData(basketData);
//            qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
//            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
//            adapter.notifyDataSetChanged();
//            String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//            binding.defectedData.qty.setText(defectedQty);
//        }
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsWelding();
        initViews();
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
                    getBasketData(basketCode);
//                    getBasketDefectsPainting(basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsWelding() {
        viewModel.getDefectsPaintingListStatus().observe(getViewLifecycleOwner(), status -> {
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
//    private void getBasketDefectsPainting(String basketCode) {
//        viewModel.getDefectsPaintingViewModel(userId,deviceSerialNo,basketCode);
//        viewModel.getDefectsPaintingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsPainting -> {
//            if (apiResponseDefectsPainting!=null) {
//                ResponseStatus responseStatus = apiResponseDefectsPainting.getResponseStatus();
//                String statusMessage = responseStatus.getStatusMessage();
//                if (statusMessage.equals(SUCCESS)) {
//                    if (apiResponseDefectsPainting.getDefectsPainting() != null) {
//                        defectsPaintingList.clear();
//                        List<DefectsPainting> defectsPaintingListLocal = apiResponseDefectsPainting.getDefectsPainting();
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
            if (defectsPainting.getDefectGroupId() != id) {
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
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this, provider).get(PaintQualityRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintQualityRepairViewModel.class);

    }

    LastMovePaintingBasket basketData;
    String parentDesc,parentCode = "",operationName;
    private int qtyReadyToMove;
    private void getBasketData(String basketCode) {
        viewModel.getBasketData(userId,deviceSerialNo,basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMovePaintingBasket -> {
            if (apiResponseLastMovePaintingBasket!=null) {
                ResponseStatus responseStatus = apiResponseLastMovePaintingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    basketData = apiResponseLastMovePaintingBasket.getLastMovePaintingBaskets().get(0);
                    defectsPaintingList = apiResponseLastMovePaintingBasket.getPaintingDefects();
                    adapter.setDefectsPaintingList(defectsPaintingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
                    qtyReadyToMove = apiResponseLastMovePaintingBasket.getMinQtyApproved();
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
                    binding.basketCode.setError(null);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    fillData(parentDesc,operationName, basketData.getJobOrderName(), basketData.getJobOrderQty(), basketData.getOperationEnName());
                    adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
                    adapter.notifyDataSetChanged();
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

        });
    }

    private void fillData(String parentDesc, String operationName, String jobOrderName, Integer jobOrderQty, String operationEnName) {
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(jobOrderQty.toString());
    }


    private void setupRecyclerView() {
        adapter = new PaintQualityRepairQtyDefectsQtyAdapter();
        adapter.setOnDefectsPerQtyItemClicked(new PaintQualityRepairQtyDefectsQtyAdapter.OnDefectItemClicked() {
            @Override
            public void onDefectItemClicked(ArrayList<PaintingDefect> defectList) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("selectedDefectsPainting", defectList);
                bundle.putParcelable(BASKET_DATA, basketData);
                bundle.putInt(QTY_READY_TO_MOVE,qtyReadyToMove);
                Navigation.findNavController(requireView()).navigate(R.id.fragment_paint_quality_repair_to_fragment_paint_quality_defect_repair, bundle);
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
//            getBasketDefectsPainting(scannedText);
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
        if (basketData!=null) {
            basketData.setBasketCode(basketCode);
        }
//        if (!defectsPaintingList.isEmpty()){
//            viewModel.setDefectsPaintingList(defectsPaintingList);
//        }
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(scannedText);
//            getBasketDefectsPainting(scannedText);
        });
    }
}